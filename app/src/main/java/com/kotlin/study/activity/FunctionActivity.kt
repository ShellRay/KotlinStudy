package com.kotlin.study.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.study.R
import com.kotlin.study.bean.UserBean
import com.kotlin.study.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_function.*

class FunctionActivity : AppCompatActivity() {

    //kotlin取消了activity.this的用法
    val instance = lazy { this }

    val catalogs = listOf<String>("kotlin的函数\n",
            "kotlin的函数lambda\n"
                    + "在Kotlin中对Java中的一些的接口的回调做了一些优化，可以使用一个lambda函数来代替。可以简化写一些不必要的嵌套回调方法。但是需要注意:在lambda表达式，只支持单抽象方法模型，也就是说设计的接口里面只有一个抽象的方法，才符合lambda表达式的规则，多个回调方法不支持\n" +
                    "--------------------- \n",
            "kotlin的函数let\n"
                    + "let函数适用的场景\n" +
                    "\n" +
                    "场景一: 最常用的场景就是使用let函数处理需要针对一个可null的对象统一做判空处理。\n" +
                    "\n" +
                    "场景二: 然后就是需要去明确一个变量所处特定的作用域范围内可以使用\n" +
                    "\n" +
                    "6、let函数使用前后的对比\n" +
                    "\n" +
                    "没有使用let函数的代码是这样的，看起来不够优雅\n" +
                    "\n" +
                    "mVideoPlayer?.setVideoView(activity.course_video_view)\n" +
                    "    mVideoPlayer?.setControllerView(activity.course_video_controller_view)\n" +
                    "    mVideoPlayer?.setCurtainView(activity.course_video_curtain_view)\n" +
                    "使用let函数后的代码是这样的\n" +
                    "\n" +
                    "mVideoPlayer?.let {\n" +
                    "       it.setVideoView(activity.course_video_view)\n" +
                    "       it.setControllerView(activity.course_video_controller_view)\n" +
                    "       it.setCurtainView(activity.course_video_curtain_view)\n" +
                    "}\n" +
                    "--------------------- \n",
            "kotlin的函数with\n" +
                    "with函数和前面的几个函数使用方式略有不同，因为它不是以扩展的形式存在的。它是将某对象作为函数的参数，在函数块内可以通过 this 指代该对象。返回值为函数块的最后一行或指定return表达式"
                    + "\n适用于调用同一个类的多个方法时，可以省去类名重复，直接调用类的方法即可，经常用于Android中RecyclerView中onBinderViewHolder中，数据model的属性映射到UI上"
                    + "没有使用kotlin中的实现\n" +
                    "\n" +
                    "@Override\n" +
                    "public void onBindViewHolder(ViewHolder holder, int position) {\n" +
                    "\n" +
                    "   ArticleSnippet item = getItem(position);\n" +
                    "        if (item == null) {\n" +
                    "            return;\n" +
                    "        }\n" +
                    "        holder.tvNewsTitle.setText(StringUtils.trimToEmpty(item.titleEn));\n" +
                    "        holder.tvNewsSummary.setText(StringUtils.trimToEmpty(item.summary));\n" +
                    "        String gradeInfo = \"难度：\" + item.gradeInfo;\n" +
                    "        String wordCount = \"单词数：\" + item.length;\n" +
                    "        String reviewNum = \"读后感：\" + item.numReviews;\n" +
                    "        String extraInfo = gradeInfo + \" | \" + wordCount + \" | \" + reviewNum;\n" +
                    "        holder.tvExtraInfo.setText(extraInfo);\n" +
                    "        ...\n" +
                    "}\n" +
                    "kotlin的实现\n" +
                    "\n" +
                    "override fun onBindViewHolder(holder: ViewHolder, position: Int){\n" +
                    "   val item = getItem(position)?: return\n" +
                    "\n" +
                    "   with(item){\n" +
                    "\n" +
                    "      holder.tvNewsTitle.text = StringUtils.trimToEmpty(titleEn)\n" +
                    "       holder.tvNewsSummary.text = StringUtils.trimToEmpty(summary)\n" +
                    "       holder.tvExtraInf.text = 难度：$ gradeInfo | 单词数：$ length | 读后感: $ numReviews\n" +
                    "       ...   \n" +
                    "\n" +
                    "   }\n" +
                    "\n" +
                    "}\n",


            "kotlin的函数run\n"
                    + "run函数实际上可以说是let和with两个函数的结合体，run函数只接收一个lambda函数为参数，以闭包形式返回，返回值为最后一行的值或者指定的return的表达式\n"
                    + "适用于let,with函数任何场景。因为run函数是let,with两个函数结合体，准确来说它弥补了let函数在函数体内必须使用it参数替代对象，在run函数中可以像with函数一样可以省略，直接访问实例的公有属性和方法，另一方面它弥补了with函数传入对象判空问题，在run函数中可以像let函数一样做判空处理\n" +
                    " 还是借助上个例子kotlin代码\n" +
                    "\n" +
                    "override fun onBindViewHolder(holder: ViewHolder, position: Int){\n" +
                    "   val item = getItem(position)?: return\n" +
                    "\n" +
                    "   with(item){\n" +
                    "\n" +
                    "      holder.tvNewsTitle.text = StringUtils.trimToEmpty(titleEn)\n" +
                    "       holder.tvNewsSummary.text = StringUtils.trimToEmpty(summary)\n" +
                    "       holder.tvExtraInf = 难度：$ gradeInfo | 单词数：$ length | 读后感: $ numReviews\n" +
                    "       ...   \n" +
                    "\n" +
                    "   }\n" +
                    "\n" +
                    "}\n" +
                    "使用run函数后的优化\n" +
                    "\n" +
                    "override fun onBindViewHolder(holder: ViewHolder, position: Int){\n" +
                    "\n" +
                    "  getItem(position)?.run{\n" +
                    "      holder.tvNewsTitle.text = StringUtils.trimToEmpty(titleEn)\n" +
                    "       holder.tvNewsSummary.text = StringUtils.trimToEmpty(summary)\n" +
                    "       holder.tvExtraInf = 难度：$ gradeInfo | 单词数：$ length | 读后感: $ numReviews\n" +
                    "       ...   \n" +
                    "\n" +
                    "   }\n" +
                    "\n" +
                    "}\n"
                    + "--------------------- \n",

            "kotlin的函数apply\n"
                    + "从结构上来看apply函数和run函数很像，唯一不同点就是它们各自返回的值不一样，run函数是以闭包形式返回最后一行代码的值，而apply函数的返回的是传入对象的本身。\n"
                    + "整体作用功能和run函数很像，唯一不同点就是它返回的值是对象本身，而run函数是一个闭包形式返回，返回的是最后一行的值。正是基于这一点差异它的适用场景稍微与run函数有点不一样。apply一般用于一个对象实例初始化的时候，需要对对象中的属性进行赋值。或者动态inflate出一个XML的View的时候需要给View绑定数据也会用到，这种情景非常常见。特别是在我们开发中会有一些数据model向View model转化实例化的过程中需要用到\n" +
                    " 没有使用apply函数的代码是这样的，看起来不够优雅\n" +
                    "\n" +
                    "mSheetDialogView = View.inflate(activity, R.layout.biz_exam_plan_layout_sheet_inner, null)\n" +
                    "        mSheetDialogView.course_comment_tv_label.paint.isFakeBoldText = true\n" +
                    "        mSheetDialogView.course_comment_tv_score.paint.isFakeBoldText = true\n" +
                    "        mSheetDialogView.course_comment_tv_cancel.paint.isFakeBoldText = true\n" +
                    "        mSheetDialogView.course_comment_tv_confirm.paint.isFakeBoldText = true\n" +
                    "        mSheetDialogView.course_comment_seek_bar.max = 10\n" +
                    "        mSheetDialogView.course_comment_seek_bar.progress = 0\n" +
                    "使用apply函数后的代码是这样的\n" +
                    "\n" +
                    "mSheetDialogView = View.inflate(activity, R.layout.biz_exam_plan_layout_sheet_inner, null).apply{\n" +
                    "   course_comment_tv_label.paint.isFakeBoldText = true\n" +
                    "   course_comment_tv_score.paint.isFakeBoldText = true\n" +
                    "   course_comment_tv_cancel.paint.isFakeBoldText = true\n" +
                    "   course_comment_tv_confirm.paint.isFakeBoldText = true\n" +
                    "   course_comment_seek_bar.max = 10\n" +
                    "   course_comment_seek_bar.progress = 0\n" +
                    "\n" +
                    "}\n"
                    + "--------------------- \n",

            "kotlin的函数also\n"
                    + "also函数的结构实际上和let很像唯一的区别就是返回值的不一样，let是以闭包的形式返回，返回函数体内最后一行的值，如果最后一行为空就返回一个Unit类型的默认值。而also函数返回的则是传入对象的本身\n"
                    + "适用于let函数的任何场景，also函数和let很像，只是唯一的不同点就是let函数最后的返回值是最后一行的返回值而also函数的返回值是返回当前的这个对象。一般可用于多个扩展函数链式调用",
            "ALL STAR WITH BIG BANG"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function)
        rcyLambda.layoutManager = LinearLayoutManager(this)
        rcyLambda.adapter = MainAdapter(catalogs, this, ClickCallback(this))

    }

    inner class ClickCallback(private val kotlinCatalog: FunctionActivity) : MainAdapter.OnClickCallback {
        override fun onClick(view: View, position: Int) {
            toast(catalogs[position].substring(0, 10))
            when (position) {
                1 -> functionLambda()
                2 -> functionLet()
                3 -> functionWith()
                4 -> functionRun()
                5 -> functionApply()
                6 -> functionAlso()
            }
        }
    }


    class MainAdapter(private val items: List<String>, instances: FunctionActivity, clickCallback: OnClickCallback?) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

        private var instance = instances
        private var mOnClickCallback = clickCallback

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(TextView(parent.context))
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = items[position]
            holder.textView.textSize = 17f
            holder.textView.setTextColor(ContextCompat.getColor(instance, R.color.colorPrimaryDark))
            holder.textView.setOnClickListener {
                mOnClickCallback!!.onClick(holder.textView, position)
            }
        }

        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

        interface OnClickCallback {
            fun onClick(view: View, position: Int)
        }
    }

    fun toast(str: String) {
        ToastUtils.showCustomToast(this, str)
    }

    fun functionLambda() {
        lambdaClick.setOnClickListener {
            toast("lambda")
        }
    }

    fun functionLet() {
        val result = "resultLet".let {
            toast(it)//it = resultLet
            1000
        }
        println(result.toString())
    }

    fun functionWith() {
        val user = UserBean("王五", 10086, "男", "柯棣华")
        val functionResult = with(user) {
            toast("my name is $userName, I am $userSex, my phone number is $userId")
            1000
        }
        println("functionResult: $functionResult")
    }

    fun functionRun() {
        val user = UserBean("王五", 10086, "男", "柯棣华")
        val functionResult = user.run {
            toast("my name is $userName, I am $userSex, my phone number is $userId")
            1000
        }
        println("functionResult: $functionResult")
    }

    fun functionApply() {
        val user = UserBean("王五", 10086, "男", "柯棣华")
        val functionResult = user.apply {
            toast("my name is $userName, I am $userSex, my phone number is $userId")
            1000
        }
        println("functionResult: $functionResult")
    }

    fun functionAlso() {
        val user = "jdj"
        val functionResult = user.also {
            toast("my name is $user")
            1000
        }
        println("functionResult: $functionResult")
    }
}