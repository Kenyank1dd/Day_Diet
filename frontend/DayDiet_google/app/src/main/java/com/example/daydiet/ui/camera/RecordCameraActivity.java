package com.example.daydiet.ui.camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.YuvImage;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Size;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.daydiet.R;
import com.example.daydiet.service.Base64Util;
import com.example.daydiet.service.FileUtil;
import com.example.daydiet.yolov5.Box;
import com.example.daydiet.yolov5.YOLOv5;
import com.example.daydiet.yolov5.YoloV5Ncnn;
import com.google.mlkit.common.sdkinternal.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import wseemann.media.FFmpegMediaMetadataRetriever;

public class RecordCameraActivity extends AppCompatActivity{

    public static CameraX.LensFacing CAMERA_ID = CameraX.LensFacing.BACK;

    private static final int REQUEST_CAMERA = 1;
    private static String[] PERMISSIONS_CAMERA = { Manifest.permission.CAMERA };

    private Toolbar toolbar;
    private TextureView viewFinder;
    private Button btn_cancel;
    private Button btn_finish;

    ExecutorService detectService = Executors.newSingleThreadExecutor();

    FFmpegMediaMetadataRetriever mmr;

    private PreviewConfig previewConfig;
    private Preview preview;
    private ImageCapture mImageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordcamera_layout);

        //实现状态栏图标和文字颜色为暗色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_CAMERA,
                    REQUEST_CAMERA
            );
            finish();
        }

        initViewID();
        initViewListener();
    }

    protected void initViewListener() {
        // 预览
        viewFinder.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                updateTransform();
            }
        });

        viewFinder.post(new Runnable() {
            @Override
            public void run() {
                startCamera();
            }
        });

        // 取消拍摄
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 拍摄照片
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final File file = new File(getExternalMediaDirs()[0], System.currentTimeMillis() + ".jpg");
                mImageCapture.takePicture(
                    file,
                    ContextCompat.getMainExecutor(getApplicationContext()),
                    new ImageCapture.OnImageSavedListener() {
                        @Override
                        public void onImageSaved(@NonNull File file) {
                            Log.d("++++666B", "##### onImageSaved: " + file.getPath());
                            Intent data = new Intent();
                            data.putExtra("filepath", file.getPath());
                            setResult(1, data);
                            //销毁当前Activity
                            finish();
                        }
                        @Override
                        public void onError(@NonNull ImageCapture.ImageCaptureError imageCaptureError, @NonNull String message, @Nullable Throwable cause) {
                            Log.d("++++666C", "##### onError: \n" + message + "\n" + imageCaptureError.toString() + "\n" + cause.toString());
                        }
                    }
//                    metadata
                );
            }
        });
    }


    protected void initViewID() {
        toolbar = findViewById(R.id.tool_bar);
        viewFinder = findViewById(R.id.view_finder);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_finish = findViewById(R.id.btn_finish);
    }

    private void updateTransform() {
        Matrix matrix = new Matrix();
        // Compute the center of the view finder
        float centerX = viewFinder.getWidth() / 2f;
        float centerY = viewFinder.getHeight() / 2f;

        float[] rotations = {0, 90, 180, 270};
        // Correct preview output to account for display rotation
        float rotationDegrees = rotations[viewFinder.getDisplay().getRotation()];

        matrix.postRotate(-rotationDegrees, centerX, centerY);

        // Finally, apply transformations to our TextureView
        viewFinder.setTransform(matrix);
    }

    @SuppressLint("RestrictedApi")
    private void startCamera() {
        CameraX.unbindAll();
        // 预览
        previewConfig = new PreviewConfig.Builder()
                .setLensFacing(CAMERA_ID)
//                .setTargetAspectRatio(Rational.NEGATIVE_INFINITY)  // 宽高比
//                .setTargetResolution(new Size(480, 640))  // 分辨率
                .setTargetResolution(new Size(viewFinder.getWidth(), viewFinder.getHeight()))
                .build();
        preview = new Preview(previewConfig);
        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(Preview.PreviewOutput output) {
                ViewGroup parent = (ViewGroup) viewFinder.getParent();
                parent.removeView(viewFinder);
                parent.addView(viewFinder, 0);

                viewFinder.setSurfaceTexture(output.getSurfaceTexture());
                updateTransform();
            }
        });

        ImageCaptureConfig captureConfig = new ImageCaptureConfig.Builder()
        .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
//        .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
        .setTargetResolution(new Size(960, 1280))
//          .setTargetResolution(new Size(viewFinder.getWidth(), viewFinder.getHeight()))
        .build();

        mImageCapture = new ImageCapture(captureConfig);

        CameraX.bindToLifecycle((LifecycleOwner) this, mImageCapture, preview);

//        CameraX.bindToLifecycle((LifecycleOwner) this, mImageCapture, preview);
    }

    @Override
    protected void onDestroy() {
        if (detectService != null) {
            detectService.shutdown();
            detectService = null;
        }
        if (mmr != null) {
            mmr.release();
        }
        CameraX.unbindAll();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission!", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }
}
