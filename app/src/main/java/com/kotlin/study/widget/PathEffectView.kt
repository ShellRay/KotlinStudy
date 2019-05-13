package com.kotlin.study.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * @author ShellRay
 * Created  on 2019/5/13.
 * @description
 */
class PathEffectView : View {

    // 路径效果的相位
    var phase: Float = 0.toFloat()
    // 7种不同路径效果的数组
    var effects = arrayOfNulls<PathEffect>(7)
    // 颜色ID数组
    lateinit var colors: IntArray
    // 画笔
    private lateinit var paint: Paint
    // 声明路径 对象
    lateinit var path: Path

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    fun init() {
        paint = Paint()
        paint.setStyle(Paint.Style.STROKE)
        paint.setStrokeWidth(4f)
        // 创建、并初始化Path
        path = Path()
        path.moveTo(0f, 50f)
        for (i in 0..150) {
            // 生成15个点，随机生成它们的Y坐标，并将它们连成一条Path
            path.lineTo(i * 20f, Math.random().toFloat() * 60)
        }
        // 初始化7个颜色
        colors = intArrayOf(Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW)
    }

     override fun onDraw(canvas: Canvas) {
        // 将背景填充为白色
        canvas.drawColor(Color.WHITE)
        // -----下面开始初始化7种路径效果-------
        // 不使用路径效果
        effects[0] = null
        // 使用CornerPathEffect路径效果
        effects[1] = CornerPathEffect(10f)
        // 初始化DiscretePathEffect
        effects[2] = DiscretePathEffect(3.0f, 5.0f)
        // 初始化DashPathEffect
        effects[3] = DashPathEffect(floatArrayOf(20f, 10f, 5f, 10f),
                phase)
        // 初始化PathDashPathEffect
        val p = Path()
        p.addRect(0f, 0f, 8f, 8f, Path.Direction.CCW)
        effects[4] = PathDashPathEffect(p, 12f, phase,
                PathDashPathEffect.Style.ROTATE)
        // 初始化ComposePathEffect
        effects[5] = ComposePathEffect(effects[2], effects[4])
        // 初始化SumPathEffect
        effects[6] = SumPathEffect(effects[4], effects[3])
        // 将画布移动到8、8处开始绘制
        canvas.translate(8f, 8f)
        // 依次使用7种不同路径效果、7种不同颜色来绘制路径
        for (i in effects.indices) {
            paint.setPathEffect(effects[i])
            paint.setColor(colors[i])
            canvas.drawPath(path, paint)
            canvas.translate(0f, 60f)
        }
        // 改变phase值，形成动画效果
        phase += 1f
        // 重绘
        invalidate()
    }
}