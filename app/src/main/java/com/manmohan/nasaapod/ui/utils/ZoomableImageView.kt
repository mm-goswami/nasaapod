package com.manmohan.nasaapod.ui.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView


class ZoomableImageView : ImageView {

    constructor(context: Context) : this(context, null){
        mScaleGestureDetector = ScaleGestureDetector(context, ScaleListener(this, 1.0f))
        setOnTouchListener {v, event -> onTouchEvent(event)}
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        mScaleGestureDetector = ScaleGestureDetector(context, ScaleListener(this, 1.0f))
        setOnTouchListener {v, event -> onTouchEvent(event)}
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        mScaleGestureDetector = ScaleGestureDetector(context, ScaleListener(this, 1.0f))
        setOnTouchListener {v, event -> onTouchEvent(event)}
    }


    private var mScaleGestureDetector: ScaleGestureDetector? = null

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        mScaleGestureDetector?.onTouchEvent(motionEvent)
        return true
    }

    private inner class ScaleListener(private val imageView: ImageView, var scaleFactor: Float) : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = Math.max(
                0.1f,
                Math.min(scaleFactor, 10.0f)
            )
            imageView.setScaleX(scaleFactor)
            imageView.setScaleY(scaleFactor)
            return true
        }
    }
}