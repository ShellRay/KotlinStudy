package com.kotlin.study.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.study.BaseActivity
import com.kotlin.study.R
import kotlinx.android.synthetic.main.activity_fundamental_type.*

class FundamentalTypeActivity:BaseActivity(){

    //kotlin取消了activity.this的用法
    val instance = lazy { this }

    val catalogs = listOf<String>("kotlin的基本类型\n",
            "一、数字类型\n" +
                    "val doubles : Double = 123.5e10 //Double,64\n" +
                    "val floats : Float = 1.0f //Float,32\n" +
                    "val longs : Long = 1L //Long,64\n" +
                    "val ints : Int = 1 //Int,32\n" +
                    "val shorts : Short = 1 //Short,16\n" +
                    "val bytes : Byte = 1 //Byte,8\n" +"Kotlin支持十进制、十六进制以及二进制，目前还不支持八进制"+
                    "val tenType : Int = 10 //支持十进制，默认是十进制\n" +
                    "val sixteenType : Byte = 0x22 //支持十六进制,0x开头标示十六进制\n" +
                    "val twoType : Int = 0b00000010 //支持二进制,0b开头表示二进制\n" +
                    "与java不同之处：\n" +
                    "1、数字类型支持字面值中的下划线，这样让长数字常量更易读，例如：\n" +
                    "val ints1 = 1_001_000\n" +
                    "val longs2 = 1234_5678_9012_3456L\n" +
                    "val hexBytes = 0xFF_EC_DE_5E\n" +
                    "val bytew = 0b11010010_01101001_10010100_10010010\n" +
                    "\n" +
                    "2、Kotlin数据类型转化与java不同\n" +
                    "val a: Int? = 1\n" +
                    "//    val b: Long? = a//这样隐式转换有问题，不能编译\n" +
                    "//    val c: Long? = (Long)a//像java一样强转也不行，不能编译\n" +
                    "\n" +
                    "Kotlin提供了更加强大的转化功能\n" +
                    "每个数字类型都支持一下的转化：\n" +
                    "toByte(): Byte\n" +
                    "toShort(): Short\n" +
                    "toInt(): Int\n" +
                    "toLong(): Long\n" +
                    "toFloat(): Float\n" +
                    "toDouble(): Double\n" +
                    "toChar(): Char\n" +
                    "val d: Long? = a?.toLong()\n" +
                    "//算术运算会有重载做适当转换\n" +
                    "val l = 1L + 3 // Long + Int => Long\n" +
                    "\n" +
                    "当然接下来的其他类型大部分也都支持以上转化，例如：字符等\n" +
                    "3、位运算\n" +
                    "//    这是完整的位运算列表（只用于 Int 和 Long）：\n" +
                    "//    shl(bits) – 有符号左移 (Java 的 <<)\n" +
                    "//    shr(bits) – 有符号右移 (Java 的 >>)\n" +
                    "//    ushr(bits) – 无符号右移 (Java 的 >>>)\n" +
                    "//    and(bits) – 位与\n" +
                    "//    or(bits) – 位或\n" +
                    "//    xor(bits) – 位异或\n" +
                    "//    inv() – 位非\n" +
                    "val x = (1 shl 2) and 0x000FF000\n" ,
            "二、字符类型（Char）\n" +
                    "字符类型,单引号括起来且只能一个字符。\n" +
                    "特殊字符可以用反斜杠转义。 支持这几个转义序列：\\t、 \\b、\\n、\\r、'、\"、\\ 和 \$\n" +
                    "    val char1 : Char = 'a'\n" +
                    "    val char2 : Char = '1'\n" +
                    "    val cInt : Int = char2.toInt()//字符类型转Int\n" +
                    "    //字符类型转Int方法\n" +
                    "    fun CharToInt(c: Char): Int {\n" +
                    "        if (c !in '0'..'9')\n" +
                    "            throw IllegalArgumentException(\"Out of range\")\n" +
                    "        return c.toInt() - '0'.toInt() // 显式转换为数字\n" +
                    "    }\n" ,
            "三、布尔类型（）\n" +
                    "与java差不多，唯一区别就是，Kotlin不只是true或false,还支持null，例如：\n" +
                    "    val bool1 : Boolean = true\n" +
                    "    val bool2 : Boolean? = null//加上？就可以对布尔类型赋值null\n" +
                    "    fun test4() : String{\n" +
                    "        return bool2.toString()//就算调用null，输出打印，也不会报错，这就是Kotlin空安全强大之处\n" +
                    "    }\n" +
                    "\n" ,
            "四、集合、数组类型\n" +
                    "与大多数语言不同，Kotlin 区分可变集合和不可变集合（lists、sets、maps 等）。精确控制什么时候集合可编辑有助于消除 bug 和设计良好的 API。\n" +
                    "Kotlin 的 List<out T> 类型是一个提供只读操作如 size、get等的接口。和 Java 类似，它继承自 Collection<T> 进而继承自 Iterable<T>。\n" +
                    "改变 list 的方法是由 MutableList<T> 加入的。\n" +
                    "这一模式同样适用于 Set<out T>/MutableSet<T> 及 Map<K, out V>/MutableMap<K, V>。\n" +
                    "    val numbers: MutableList<Int> = mutableListOf(1, 2, 3)\n" +
                    "    val OnlyReadList: List<Int> = numbers\n" +
                    "    fun test5() : String{\n" +
                    "//        readOnlyView.clear()    // -> 不能编译\n" +
                    "//        return numbers.toString()       // 输出 \"[1, 2, 3]\"\n" +
                    "        numbers.add(4)\n" +
                    "        return OnlyReadList.toString()   // 输出 \"[1, 2, 3, 4]\"\n" +
                    "    }\n" +
                    "1、Arrays\n" +
                    "Kotlin 标准库提供了arrayOf()创建数组， **ArrayOf创建特定类型数组\n" +
                    "    val array = arrayOf(1,2,3)\n" +
                    "    //和Java不一样，Kotlin 的数组是容器类\n" +
                    "    // 提供了 ByteArray, CharArray, ShortArray, IntArray, LongArray, BooleanArray, FloatArray, and DoubleArray。\n" +
                    "    val intArray = intArrayOf(1,2)\n" +
                    "\n" +
                    "public inline constructor(size: Int, init: (Int) -> T)\n" +
                    "创建一个具有指定[size]的新数组，其中每个元素通过调用指定的值来计算。函数的作用是:返回给定索引的数组元素。例如：\n" +
                    "    val array1 = Array(10, { k -> k * k })//这里k代表元素的索引位置\n" +
                    "//  return array1.get(2).toString()//输出4，因为索引是2，输出：k*k = 2*2 = 4\n" +
                    "    val array2 = Array(10,{k -> \"位置:\"+(k+1) })\n" +
                    "//  return array2.get(5).toString() //输出\"位置:6\"，因为索引是5，输出：\"位置:\"+(k+1) = \"位置:\"+(6+1) = 位置:6\n" +
                    "    val emptyArray = emptyArray<Long>()\n" +
                    "\n" +
                    "当然你也创建对象数组\n" +
                    "   class Student(val name : String?){\n" +
                    "    }\n" +
                    "   //当然你也创建对象数组\n" +
                    "   val studentArray = Array<Student>(10,{k -> Student(\"学生编号：\"+k)})\n" +
                    "// return studentArray.get(6).name.toString()\n" +
                    "   studentArray[6] = Student(\"张三\")\n" +
                    "   return studentArray.get(6).name.toString()\n" +
                    "\n" +
                    "2、Lists\n" +
                    "List是有序容器，Kotlin 标准库通过listOf()创建list\n" +
                    "你可以这样申明\n" +
                    "        val list1 = listOf<Int>()\n" +
                    "\n" +
                    "当然也可以这样，list1与list2意思一样\n" +
                    "        val list2 : List<Int> = listOf(1,2,3,4,5,6)\n" +
                    "\n" +
                    "当然你也可以付初始默认值，也可以不指定类型\n" +
                    "        val list3 = listOf(1,2,\"3\")\n" +
                    "\n" +
                    "如果你想对list2进行添加或者修改时，就不行了，因为List<T>只能读，这时候你需要声明个可以操作的属性\n" +
                    "        val list4 : MutableList<Any> = list3.toMutableList()//这里将List转换出可以编辑的\n" +
                    "        list4.add(\"4\")\n" +
                    "//        list4.set(0,10)\n" +
                    "//        return list4.toString()//输出：[1, 2, 3, 4]\n" +
                    "//        return list3.toString()//输出：[1, 2, 3]\n" +
                    "\n" +
                    "对list4操作不会影响list3，list4相当于复制出来的\n" +
                    "//        return (list4.first() == 1).toString()//当然也有list4.last()\n" +
                    "\n" +
                    "当然也可以按一定条件筛选\n" +
                    "//        return list2.filter { (it % 2) == 0 }.toString()\n" +
                    "        val list5 : MutableList<Int> = mutableListOf(1,2,3,4)\n" +
                    "        val list6 : List<Int> = list5\n" +
                    "        list5.add(5)\n" +
                    "//        return list5.toString()//输出：[1, 2, 3, 4, 5]\n" +
                    "        return list6.toString()//输出：[1, 2, 3, 4, 5]\n" +
                    "\n" +
                    "        val emptyList: List<String> = emptyList<String>()\n" +
                    "        val nonNulls: List<String> = listOfNotNull<String>(null, \"a\", \"b\", \"c\")\n" +
                    "\n" +
                    "3、Maps\n" +
                    "Map是<key, value>容器， Kotlin提供mapOf创建map\n" +
                    "    fun test8() : String{\n" +
                    "        val map1 = mapOf(\"a\" to 1, \"b\" to 2, \"c\" to 3)\n" +
                    "//        return map1.toString()\n" +
                    "        val map2 = hashMapOf(\"a\" to 1, \"b\" to 2, \"c\" to 3)\n" +
                    "//        return map2.toString()\n" +
                    "        val map3 : MutableMap<String, String> = mutableMapOf(\"a\" to \"a\", \"b\" to \"b\", \"c\" to \"c\")\n" +
                    "//        return map3.toString()\n" +
                    "        val map4 : LinkedHashMap<String, String> = linkedMapOf(\"a\" to \"a\", \"b\" to \"b\", \"c\" to \"c\")\n" +
                    "//        return map4.toString()\n" +
                    "        val map5 : SortedMap<String, String> = sortedMapOf(\"a\" to \"a\", \"b\" to \"b\", \"c\" to \"c\")\n" +
                    "        return map5.toString()\n" +
                    "        return \"\"\n" +
                    "    }\n" +
                    "\n" +
                    "4、Sets\n" +
                    "Set是没有重复项的容器， Kotlin提供setOf创建Set\n" +
                    "    fun test9() : String{\n" +
                    "        val set1: Set<Int> = setOf(1,2,3,3,4) //1,2,3,4\n" +
                    "//        return set1.toString()\n" +
                    "        val set2: HashSet<String> = hashSetOf(\"1\",\"2\",\"3\",\"3\")//3,2,1\n" +
                    "//        return set2.toString()\n" +
                    "        val set3: SortedSet<Int> = sortedSetOf(11, 0, 9, 11, 9, 8)//0,8,9,11\n" +
                    "        return set3.toString()\n" +
                    "        return \"\"\n" +
                    "    }\n" +
                    "\n" ,
            "五、字符串类型\n" +
                    "支持这几个转义序列：\\t、 \\b、\\n、\\r、'、\"、\\ 和 \$\n" +
                    "    val string1 : String = \"1\"\n" +
                    "    val string2 = \"Hello, world!\\n\"\n" +
                    "\n" +
                    "字符串模板表达式\n" +
                    "字符串可以包含模板表达式 ，即一些小段代码，会求值并把结果合并到字符串中。 模板表达式以美元符（\$）开头，由一个简单的名字构成:\n" +
                    "        val string3 = a = $"+"string1\"\n" +
                    "//        return string3\n" +
                    "        val string4 = $"+ "string3.length is $"+"{string3.length}\n" +
                    "        return string4 //输出a = 1.length is 5\n" +
                    "\n" )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fundamental_type)
        rcyCatalog.layoutManager = LinearLayoutManager(this)
        rcyCatalog.adapter = MainAdapter(catalogs,this,ClickCallback(this))

    }

    inner class ClickCallback(private val kotlinCatalog: FundamentalTypeActivity) : MainAdapter.OnClickCallback {
        override fun onClick(view: View, position: Int) {
            var intent = Intent()
            when(position){
                0 -> intent.setClass(kotlinCatalog, FundamentalTypeActivity::class.java)

            }
            startActivity(intent)
        }
    }


    class MainAdapter(val items: List<String>, val instances: FundamentalTypeActivity, clickCallback: OnClickCallback?) : RecyclerView.Adapter<MainAdapter.ViewHolder>()
    {

        private var instance = instances
        private var mOnClickCallback = clickCallback

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(TextView(parent.context))
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = items[position]
            holder.textView.textSize = 17f
            holder.textView.setTextColor(ContextCompat.getColor(instance,R.color.colorPrimaryDark))
            holder.textView.setOnClickListener{
                mOnClickCallback!!.onClick(holder.textView,position)
            }
        }

        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

        interface OnClickCallback {
            fun onClick(view: View, position: Int)
        }
    }

}