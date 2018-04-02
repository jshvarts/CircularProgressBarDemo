package com.jshvarts.circularprogressbardemo

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

private const val STROKE_THICKNESS_FRACTION = 0.075f
private const val TEXT_SIZE_FRACTION = 0.25f
private const val MAX_PROGRESS = 100f
private const val ANIM_DURATION_MS = 800L

class CircularProgressBar @JvmOverloads constructor(context: Context,
                                                   attrs: AttributeSet? = null,
                                                   defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var backgroundColor: Int? = null

    private var foregroundColor: Int? = null

    private var textColor: Int? = null

    private var strokeThickness: Float = 0f

    private var progressTextSize: Float = 0f

    private val circleBounds = RectF()

    private val progressTextBounds = Rect()

    private var progress = 0f

    // pre-allocate and reuse Paint in onDraw()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val progressValueAnimator = ValueAnimator.ofFloat(0f, progress).apply {
        duration = ANIM_DURATION_MS
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            progress = animatedValue as Float
            invalidate()
        }
    }

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                    R.styleable.CircularProgressBar, 0, 0)

            backgroundColor = typedArray.getColor(
                    R.styleable.CircularProgressBar_barBackgroundColor,
                    Color.GRAY)

            foregroundColor = typedArray.getColor(
                    R.styleable.CircularProgressBar_barForegroundColor,
                    Color.BLACK)

            textColor = typedArray.getColor(
                    R.styleable.CircularProgressBar_android_textColor,
                    Color.BLACK)

            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackground(canvas)
        drawForeground(canvas)
        drawProgressText(canvas)
    }

    /**
     * Triggered when you set width and height explicitly or when the view gets resized inside another container
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val diameter = Math.min(w, h)
        strokeThickness = diameter * STROKE_THICKNESS_FRACTION

        val centerX = w / 2
        val centerY = h / 2
        val squareSide = diameter - strokeThickness
        val halfOfStrokeWidth = squareSide / 2

        circleBounds.apply {
            left = centerX - halfOfStrokeWidth
            top = centerY - halfOfStrokeWidth
            right = centerX + halfOfStrokeWidth
            bottom = centerY + halfOfStrokeWidth
        }

        progressTextSize = diameter * TEXT_SIZE_FRACTION
    }

    fun updateProgress(progress: Float) {
        if (progressValueAnimator.isRunning) {
            progressValueAnimator.cancel()
        }
        progressValueAnimator.setFloatValues(this.progress, progress)
        progressValueAnimator.start()
    }

    private fun drawBackground(canvas: Canvas) {
        paint.apply {
            strokeWidth = strokeThickness
            color = backgroundColor!!
            style = Paint.Style.STROKE
        }
        canvas.drawOval(circleBounds, paint)
    }

    private fun drawForeground(canvas: Canvas) {
        paint.apply {
            color = foregroundColor!!
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
        val sweepAngle = progress / MAX_PROGRESS * 360
        canvas.drawArc(circleBounds, 0f, sweepAngle, false, paint)
    }

    private fun drawProgressText(canvas: Canvas) {
        val text = "${progress.toInt()}%"
        paint.apply {
            this.textSize = progressTextSize
            style = Paint.Style.FILL
            color = textColor!!
            textAlign = Paint.Align.CENTER
            getTextBounds(text, 0, text.length, progressTextBounds)
        }
        canvas.drawText(text, circleBounds.centerX(), circleBounds.centerY() + progressTextBounds.height() / 2, paint)
    }
}