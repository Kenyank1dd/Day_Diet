package com.example.daydiet.ui.camera;

import android.Manifest;
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
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.camera.core.UseCase;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.daydiet.R;
import com.example.daydiet.yolov5.Box;
import com.example.daydiet.yolov5.YOLOv5;
import com.example.daydiet.yolov5.YoloV5Ncnn;
import com.heytap.databaseengine.HeytapHealthApi;
import com.heytap.databaseengine.apiv2.HResponse;
import com.heytap.databaseengine.apiv2.IHeytapHealthApi;
import com.heytap.databaseengine.apiv2.auth.AuthResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class CameraActivity extends AppCompatActivity{
    public static int YOLOV5S = 1;

    public static int USE_MODEL = YOLOV5S;
    public static boolean USE_GPU = true;

    public static CameraX.LensFacing CAMERA_ID = CameraX.LensFacing.BACK;

    private static final int REQUEST_CAMERA = 1;
    private static String[] PERMISSIONS_CAMERA = { Manifest.permission.CAMERA };

    private Toolbar toolbar;
    private ImageView resultImageView;
    private TextureView viewFinder;
    private TextView tvInfo;
    private Button btn_cancel;
    private Button btn_finish;

    private double threshold = 0.3, nms_threshold = 0.7;
    private YoloV5Ncnn yolov5ncnn = new YoloV5Ncnn();
    protected float videoSpeed = 1.0f;
    protected long videoCurFrameLoc = 0;
    public static int VIDEO_SPEED_MAX = 20 + 1;
    public static int VIDEO_SPEED_MIN = 1;

    private AtomicBoolean detectCamera = new AtomicBoolean(false);
    private AtomicBoolean detectPhoto = new AtomicBoolean(false);
    private AtomicBoolean detectVideo = new AtomicBoolean(false);

    private int width;
    private int height;

    private int[] count_objects = new int[21];

//    List<String> resultArray = new ArrayList<String>();
    HashSet<String> resultSet = new HashSet<String>();
//    String[] resultArray = {"food-result"};
//    List<String> resultArray = new ArrayList<>();
    ArrayList<String> resultArray = new ArrayList<String>();

    private String tvinformation = "已识别食材: ";

    protected Bitmap mutableBitmap;

    ExecutorService detectService = Executors.newSingleThreadExecutor();

    FFmpegMediaMetadataRetriever mmr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);

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

        for(int i = 0; i < 21; i++){
            count_objects[i] = 0;
        }

        initModel();
        initViewID();
        initViewListener();
    }

    protected void initViewListener() {
        threshold = 0.3f;
        nms_threshold = 0.7f;

        resultImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detectVideo.get() || detectPhoto.get()) {
                    detectPhoto.set(false);
                    detectVideo.set(false);
                    startCamera();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                if(resultSet.size() > 0){
                    // 把检测结果封装至意图对象中
                    for(String food: resultSet){
                        resultArray.add(food);
                    }
                    for(String food: resultArray){
                        Log.d("ddddinArray",food);
                    }
                    data.putStringArrayListExtra("food-result", resultArray);
                    //当前Activity销毁时，data这个意图就会传递给启动当前Activity的那个Activity
                    setResult(1, data);
                }
                else{
                    data.putExtra("food-result", "nothing detected");
                    setResult(0, data);
                }
                //销毁当前Activity
                finish();
            }
        });

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
    }

    protected void initViewID() {
        toolbar = findViewById(R.id.tool_bar);
        resultImageView = findViewById(R.id.imageView);
        viewFinder = findViewById(R.id.view_finder);
        tvInfo = findViewById(R.id.tv_info);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_finish = findViewById(R.id.btn_finish);
    }

    protected void initModel() {
        boolean ret_init = yolov5ncnn.Init(getAssets());
        if (!ret_init)
        {
            Log.e("MainActivity", "yolov5ncnn Init failed");
        }
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

    private void startCamera() {
        CameraX.unbindAll();
        // 预览
        PreviewConfig previewConfig = new PreviewConfig.Builder()
                .setLensFacing(CAMERA_ID)
//                .setTargetAspectRatio(Rational.NEGATIVE_INFINITY)  // 宽高比
                .setTargetResolution(new Size(480, 640))  // 分辨率
                .build();
        Preview preview = new Preview(previewConfig);
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
        DetectAnalyzer detectAnalyzer = new DetectAnalyzer();
        CameraX.bindToLifecycle((LifecycleOwner) this, preview, gainAnalyzer(detectAnalyzer));
    }


    private UseCase gainAnalyzer(DetectAnalyzer detectAnalyzer) {
        ImageAnalysisConfig.Builder analysisConfigBuilder = new ImageAnalysisConfig.Builder();
        analysisConfigBuilder.setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE);
        analysisConfigBuilder.setTargetResolution(new Size(480, 640));  // 输出预览图像尺寸
        ImageAnalysisConfig config = analysisConfigBuilder.build();
        ImageAnalysis analysis = new ImageAnalysis(config);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        analysis.setAnalyzer(detectService, detectAnalyzer);
        return analysis;
    }

    private Bitmap imageToBitmap(ImageProxy image) {
        byte[] nv21 = imagetToNV21(image);

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 100, out);
        byte[] imageBytes = out.toByteArray();
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    private byte[] imagetToNV21(ImageProxy image) {
        ImageProxy.PlaneProxy[] planes = image.getPlanes();
        ImageProxy.PlaneProxy y = planes[0];
        ImageProxy.PlaneProxy u = planes[1];
        ImageProxy.PlaneProxy v = planes[2];
        ByteBuffer yBuffer = y.getBuffer();
        ByteBuffer uBuffer = u.getBuffer();
        ByteBuffer vBuffer = v.getBuffer();
        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();
        byte[] nv21 = new byte[ySize + uSize + vSize];
        // U and V are swapped
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        return nv21;
    }

    private class DetectAnalyzer implements ImageAnalysis.Analyzer {
        @Override
        public void analyze(ImageProxy image, final int rotationDegrees) {
            detectOnModel(image, rotationDegrees);
        }
    }

    private void detectOnModel(ImageProxy image, final int rotationDegrees) {
        System.out.print(image.getHeight());
        System.out.print(image.getWidth());
        if (detectCamera.get() || detectPhoto.get() || detectVideo.get()) {
            return;
        }
        detectCamera.set(true);
        final Bitmap bitmapsrc = imageToBitmap(image);  // 格式转换
        if (detectService == null) {
            detectCamera.set(false);
            return;
        }
        detectService.execute(new Runnable() {
            @Override
            public void run() {
                Matrix matrix = new Matrix();
                matrix.postRotate(rotationDegrees);
                width = bitmapsrc.getWidth();
                height = bitmapsrc.getHeight();
                Bitmap bitmap = Bitmap.createBitmap(bitmapsrc, 0, 0, width, height, matrix, false);

                detectAndDraw(bitmap);
                // 更新位图和结果文字！
                showResultOnUI();
            }
        });
    }

    protected void showResultOnUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                detectCamera.set(false);
                resultImageView.setImageBitmap(mutableBitmap);
                tvInfo.setText(tvinformation);
            }
        });
    }

    protected Bitmap drawBoxRects(Bitmap mutableBitmap, YoloV5Ncnn.Obj[] objects) {
        if (objects == null)
        {
            return mutableBitmap;
        }

        // draw objects on bitmap
        Bitmap rgba = mutableBitmap.copy(Bitmap.Config.ARGB_8888, true);

        final int[] colors = new int[] {
                Color.rgb( 54,  67, 244),
                Color.rgb( 99,  30, 233),
                Color.rgb(176,  39, 156),
                Color.rgb(183,  58, 103),
                Color.rgb(181,  81,  63),
                Color.rgb(243, 150,  33),
                Color.rgb(244, 169,   3),
                Color.rgb(212, 188,   0),
                Color.rgb(136, 150,   0),
                Color.rgb( 80, 175,  76),
                Color.rgb( 74, 195, 139),
                Color.rgb( 57, 220, 205),
                Color.rgb( 59, 235, 255),
                Color.rgb(  7, 193, 255),
                Color.rgb(  0, 152, 255),
                Color.rgb( 34,  87, 255),
                Color.rgb( 72,  85, 121),
                Color.rgb(158, 158, 158),
                Color.rgb(139, 125,  96)
        };

        Canvas canvas = new Canvas(rgba);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        Paint textbgpaint = new Paint();
        textbgpaint.setColor(Color.WHITE);
        textbgpaint.setStyle(Paint.Style.FILL);

        Paint textpaint = new Paint();
        textpaint.setColor(Color.BLACK);
        textpaint.setTextSize(26);
        textpaint.setTextAlign(Paint.Align.LEFT);
        for (int i = 0; i < objects.length; i++)
        {
            paint.setColor(colors[i % 19]);
            canvas.drawRect(objects[i].x, objects[i].y, objects[i].x + objects[i].w, objects[i].y + objects[i].h, paint);
            // draw filled text inside image
            {
                String text = objects[i].label + " = " + String.format("%.1f", objects[i].prob * 100) + "%";

                float text_width = textpaint.measureText(text);
                float text_height = - textpaint.ascent() + textpaint.descent();

                float x = objects[i].x;
                float y = objects[i].y - text_height;
                if (y < 0)
                    y = 0;
                if (x + text_width > rgba.getWidth())
                    x = rgba.getWidth() - text_width;

                canvas.drawRect(x, y, x + text_width, y + text_height, textbgpaint);
                canvas.drawText(text, x, y - textpaint.ascent(), textpaint);

                if(objects[i].prob*100 > 0.8f){
//                resultArray.add(box.getLabel());
                    Log.d("====",String.valueOf(objects[i].label_id));
                    count_objects[(int) objects[i].label_id]++;
                    if(count_objects[(int) objects[i].label_id] >= 10){
                        if(resultSet.add(objects[i].label)){
                            tvinformation = tvinformation + objects[i].label + "、";
                        }
                    }
                }
            }
        }
        return rgba;
        /*
        if (results == null || results.length <= 0) {
            return mutableBitmap;
        }
        Canvas canvas = new Canvas(mutableBitmap);
        final Paint boxPaint = new Paint();
        boxPaint.setAlpha(200);
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setStrokeWidth(4 * mutableBitmap.getWidth() / 800.0f);
        boxPaint.setTextSize(30 * mutableBitmap.getWidth() / 800.0f);
        for (Box box : results) {
            if (USE_MODEL == MOBILENETV2_YOLOV3_NANO) {
                if (box.getScore() < 0.15f) {
                    // 模型比较小，置信度太低就不要了
                    continue;
                }
                // 有时候差太多了，手动改一下
                box.x0 = box.x0 < 0 ? box.x0 / 9 : box.x0;
                box.y0 = box.y0 < 0 ? box.y0 / 9 : box.y0;
            }
            boxPaint.setColor(box.getColor());
            boxPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(box.getLabel() + String.format(Locale.CHINESE, " %.3f", box.getScore()), box.x0 + 3, box.y0 + 30 * mutableBitmap.getWidth() / 1000.0f, boxPaint);
            boxPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(box.getRect(), boxPaint);
            if(box.getScore() > 0.5f){
//                resultArray.add(box.getLabel());
                resultSet.add(box.getLabel());
            }
        }
        return mutableBitmap;*/

    }

    protected Bitmap detectAndDraw(Bitmap image) {
        long millis1 = System.currentTimeMillis();


        YoloV5Ncnn.Obj[] objects = yolov5ncnn.Detect(image,USE_GPU);
        long millis2 = System.currentTimeMillis();
        long time=millis2-millis1;//经过的毫秒数
        System.out.println(" inference cost : "+time+"ms");
        mutableBitmap = drawBoxRects(image, objects);

        return mutableBitmap;
    }


    @Override
    protected void onDestroy() {
        detectCamera.set(false);
        detectVideo.set(false);
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
