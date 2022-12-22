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
import kotlin.math.sqrt
import kotlin.random.Random


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

    private var drawColor = ResourcesCompat.getColor(resources, COLOR.GREEN.value, null)

    private var path = Path()
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f
    private var currentX = 0f
    private var currentY = 0f
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    //Variable for tools state
    private var drawActive = 0
    private var drawMove = 0

    //Variable for rectangle and circle draw
    private var startX = 0f
    private var endX = 0f
    private var startY = 0f
    private var endY = 0f
    private var radius = 0f

    //Spray points array
    private var amountPoints = 2
    private var sprayPoints: Array<Float> = emptyArray()

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
        amountPoints = state.points.value
        when (state.tools) {
            TOOLS.DASH -> {
                drawActive = 0
                paint.pathEffect = DashPathEffect(
                    floatArrayOf(
                        state.size.value.toFloat() * 2,
                        state.size.value.toFloat() * 2,
                        state.size.value.toFloat() * 2,
                        state.size.value.toFloat() * 2
                    ), 0f
                )
            }
            TOOLS.CIRCLE -> {
                drawActive = 1
            }
            TOOLS.RECTANGLE -> {
                drawActive = 2
            }
            TOOLS.SPRAY -> {
                sprayPoints = emptyArray()
                sprayPoints = Array(amountPoints + 1, { 0f })
                drawActive = 3
            }
            TOOLS.LINE -> {
                drawActive = 4
            }
            TOOLS.TEXT -> {
                drawActive = 5
            }
            else -> {
                paint.pathEffect = null
                drawActive = 0

            }
        }
    }

    fun clear() {
        extraCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (drawActive) {
            0 -> {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> touchStart()
                    MotionEvent.ACTION_MOVE -> touchMove()
                    MotionEvent.ACTION_UP -> touchUp()
                }
                drawMove = 0
            }
            1 -> {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> touchStartFigure()
                    MotionEvent.ACTION_UP -> touchUpCircle()
                }
                drawMove = 1
            }
            2 -> {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> touchStartFigure()
                    MotionEvent.ACTION_UP -> touchUpRectangle()
                }
                drawMove = 2
            }
            3 -> {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> touchPaintPointArray()
                }
                drawMove = 3
            }
            4 -> {
                when (event.action) {
                    //TODO create line function
                }
                drawMove = 4
            }
            5 -> {
                when (event.action) {
                    //TODO create write text function
                }
                drawMove = 5
            }
        }

        return true
    }

    private fun touchPaintPointArray() {
        startX = motionTouchEventX
        startY = motionTouchEventY
        for (i in 1..amountPoints step 2) {
            sprayPoints[i + 1] = abs(
                startX + Random.nextInt(-amountPoints * 2, amountPoints * 2)
            )
            sprayPoints[i] = abs(
                startY + Random.nextInt(-amountPoints * 2, amountPoints * 2)
            )
        }
        invalidate()
    }

    private fun touchUpCircle() {
        val dx = abs(motionTouchEventX - startX)
        val dy = abs(motionTouchEventY - startY)
        radius = sqrt(dx * dx + dy * dy)
        invalidate()
    }

    private fun touchStartFigure() {
        startX = motionTouchEventX
        startY = motionTouchEventY
    }

    private fun touchUpRectangle() {
        endX = motionTouchEventX
        endY = motionTouchEventY
        invalidate()
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

        //Circle Draw
        if (drawMove == 1) {
            extraCanvas.drawCircle(startX, startY, radius, paint)
            drawMove = 0
        }

        //Rectangle Draw
        if (drawMove == 2) {
            extraCanvas.drawRect(startX, startY, endX, endY, paint)
            drawMove = 0
        }

        //Spray Points
        if (drawMove == 3) {
            extraCanvas.drawPoints(sprayPoints.toFloatArray(), paint)
            drawMove = 0
        }

        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
        canvas.drawPath(drawing, this.paint)
        canvas.drawPath(curPath, this.paint)


        //For a future project///////////////////////////////////
//        paint.textSize = 100f
//        paint.textSize = 100f
//        canvas.drawARGB(80, 100, 200, 200)
//        canvas.drawCircle(100f, 200f, 50f, paint)
//        canvas.drawRect(200f, 150f, 400f, 200f, paint)
//        canvas.drawArc(300f, 250f, 600f, 500f, 30f, 300f, true, paint)
//        canvas.drawText("text", 300f, 700f, paint)

        //ThreeAngleAndCurveYellow
//        path.moveTo(100f, 750f)
//        path.lineTo(350f, 850f)
//        path.lineTo(450f, 750f)
//        canvas.drawPath(path, paint)
//        path.quadTo(100f, 1100f, 500f, 1300f)
//        canvas.drawPath(path, paint)

        //CircleAndTextGreen
//       if (drawMove == 1){
////           paint.style = Paint.Style.FILL
//           path.addCircle(startX, startY, 400f, Path.Direction.CW)
////           canvas.drawTextOnPath(text, path, 0f, 60f, paint)
//           paint.style = Paint.Style.STROKE
//           extraCanvas.drawPath(path, paint)
//           drawMove = 0
//       }

    }

}