package com.example.canvas.domain

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import com.example.canvas.R
import com.example.canvas.data.model.CanvasViewState
import com.example.canvas.data.settings.COLOR
import com.example.canvas.data.settings.TOOLS
import kotlin.math.abs

class DrawView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val STROKE_WIDTH = 12f
    }

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private var drawColor = ResourcesCompat.getColor(resources, COLOR.BLACK.value, null)

    private var path = Path()
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    private var currentX = 0f
    private var currentY = 0f
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    // Path representing
    private val drawing = Path() // the drawing
    private val curPath = Path() // what's currently being drawn

    private var onClick: () -> Unit = {}

    // Painting Settings
    private val paint = Paint().apply {
        color = drawColor
        isAntiAlias = true // Smooths out edges of what is drawn without affecting shape.
        isDither =
            true // Dithering affects how colors with higher-precision than the device are down-sampled.
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }

    fun render(state: CanvasViewState) {
        drawColor = ResourcesCompat.getColor(resources, state.color.value, null)
        paint.color = drawColor
        paint.strokeWidth = state.size.value.toFloat()
        if (state.tools == TOOLS.DASH) {
            paint.pathEffect = DashPathEffect(
                floatArrayOf(
                    state.size.value.toFloat() * 2,
                    state.size.value.toFloat() * 2,
                    state.size.value.toFloat() * 2,
                    state.size.value.toFloat() * 2
                ), 0f
            )
        } else {
            paint.pathEffect = null
        }
    }

    fun clear() {
        extraCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        invalidate()
    }

    fun setOnClickField(onClickField: () -> Unit) {
        onClick = onClickField
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    private fun restartCurrentXY() {
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchStart() {
        onClick()
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        restartCurrentXY()
    }

    private fun touchMove() {
        val dx = abs(motionTouchEventX - currentX)
        val dy = abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            restartCurrentXY()
            extraCanvas.drawPath(path, paint)
            extraCanvas.save()
        }
        invalidate()
    }

    private fun touchUp() {
        drawing.addPath(curPath)
        curPath.reset()
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //For a future project///////////////////////////////////
        val pathEx = Path()
        val paintEx = Paint()
        paintEx.setColor(Color.BLUE)
        val text = context.getString(R.string.circle_text_draw)

        paintEx.textSize = 100f
        canvas.drawARGB(80, 100, 200, 200)
        canvas.drawCircle(100f, 200f, 50f, paintEx)
        canvas.drawRect(200f, 150f, 400f, 200f, paintEx)
        canvas.drawArc(300f, 250f, 600f, 500f, 30f, 300f, true, paintEx)
        canvas.drawText("text", 300f, 700f, paintEx)

        //ThreeAngleAndCurveYellow
        paintEx.setColor(Color.YELLOW)
        pathEx.moveTo(100f, 750f)
        pathEx.lineTo(350f, 850f)
        pathEx.lineTo(450f, 750f)
        canvas.drawPath(pathEx, paintEx)
        pathEx.quadTo(100f, 1100f, 500f, 1300f)
        canvas.drawPath(pathEx, paintEx)
        pathEx.reset()

        //CircleAndTextGreen
        paintEx.style =Paint.Style.FILL
        pathEx.addCircle(500f,1400f, 400f, Path.Direction.CW)
        paintEx.setColor(Color.GREEN)
        canvas.drawTextOnPath(text, pathEx, 0f, 60f, paintEx)
        paintEx.style = Paint.Style.STROKE
        canvas.drawPath(pathEx, paintEx)




        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
        canvas.drawPath(drawing, this.paint)
        canvas.drawPath(curPath, this.paint)
    }

}