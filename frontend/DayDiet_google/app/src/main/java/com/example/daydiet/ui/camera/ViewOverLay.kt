package com.example.daydiet.ui.camera

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class ViewOverLay constructor(context: Context?, attributeSet: AttributeSet?) :
    View(context, attributeSet) {

    private var mText: String? = null
    private var mPoint: PointF? = null

    private val textpaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context!!, android.R.color.holo_blue_light)
        strokeWidth = 10f
        textSize = 150f
        isFakeBoldText = true
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mText?.let {
            canvas?.drawText(it, mPoint!!.x, mPoint!!.y, textpaint)
        }

    }

    fun drawText(str: String, ptr: PointF) {
        mText = str
        mPoint = ptr;
        invalidate()
    }
}