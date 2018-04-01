package com.jshvarts.circularprogressbardemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View

private const val STROKE_THICKNESS_FRACTION = 0.075f
private const val COLOR_DEFAULT_BACKGROUND = 0xffe1e7e9 // non-transparent
private const val COLOR_DEFAULT_FOREGROUND = 0xfff36c60

class CircularProgressBar(context: Context) : View(context) {

    private var strokeThickness: Float = 0f

    private val circleBounds = RectF()

    private val paint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG // ensure smooth edges
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Background
        paint.apply {
            strokeWidth = strokeThickness
            color = COLOR_DEFAULT_BACKGROUND.toInt()
        }
        canvas.drawOval(circleBounds, paint)

        // Foreground
        paint.color = COLOR_DEFAULT_FOREGROUND.toInt()
        canvas.drawArc(circleBounds, 0f, 120f, false, paint)


        // TODO remove after debugging
        paint.apply {
            strokeWidth = 1f
            color = Color.BLACK
        }
        canvas.drawRect(circleBounds, paint)
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

    }
}