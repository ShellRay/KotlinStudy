package com.kotlin.study.activity

import android.os.Bundle
import android.preference.*
import com.kotlin.study.R
import android.widget.Toast


/**
 * @author ShellRay
 * Created  on 2019/3/25.
 * @descriptionPreferenceActivity是一个抽象类，继承于ListActivity，以列表形式视图来展现界面,
 * 加载的整个View也是基于ListActivity中那个ListView的，其最主要的优势在于添加Preference后可让其
 * 状态持久化储存（通过SharedPreferences，一般存储在/data/data//shared_prefs文件夹下的默认名为
 * “app package name”+”_preferences.xml”的文件里），比如说用户勾选CheckBox后退出应用，下一
 * 次进入到这一界面时候，对应的是CheckBox依然是被勾选状态
 */
class SettingActivity : PreferenceActivity() {

    private val CHECKBOXPRERENCE_KEY = "key_checkbox_preference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        addPreferencesFromResource(R.xml.setting_preference)
//        createPreference()//同xml设置一样

        val preference = findPreference(CHECKBOXPRERENCE_KEY)
        preference.setOnPreferenceChangeListener({ preference, newValue ->
            //如果值改变了我们可以通过监听这个事件来获取新值
            Toast.makeText(applicationContext, String.format("Preference的值为%s", newValue), Toast.LENGTH_LONG).show()
            true
        })
        //设置Preference的点击事件监听
        preference.setOnPreferenceClickListener({
            //当接收到Click事件之后触发
            Toast.makeText(applicationContext, "Preference Clicked", Toast.LENGTH_LONG).show()
            true
        })

        removePreferenceByKey() //删除某个节点
    }

    private fun createPreference() {
        val preferenceScreen = this.preferenceManager.createPreferenceScreen(this)//先构建PreferenceScreen对象得到一个布局容器
        this.preferenceScreen = preferenceScreen//设置容器
        val checkBoxPreference = CheckBoxPreference(this)//构建一个子Preference，待添加到容器中
        checkBoxPreference.key = CHECKBOXPRERENCE_KEY//设置key
        checkBoxPreference.title = "The Title Of CheckBoxPreference"//设置title
        checkBoxPreference.summary = "Some summay for CheckBoxPreference"
        preferenceScreen.addPreference(checkBoxPreference)//添加到容器中
    }

    private fun removePreferenceByKey() {
        val preferenceGroup : PreferenceGroup? = findPreference("key_category") as PreferenceGroup?;//先找到PreferenceGroup
        val preference=findPreference("key_edtprefs");//再找到要删除的Preference
        val preferences: PreferenceGroup? = findPreference("key_prerence") as PreferenceGroup?
        preferences!!.removePreference(findPreference("key_switch"));//执行删除key为key_checkbox_child的Preference
//        preferenceScreen.removePreference(findPreference("key_category"))//删除掉key_category及对应节点下的所有节点
        //ERROR//getPreferenceScreen().removePreference(findPreference("key_edtprefs"));//无效，因为getPreferenceScreen获得的是当前的顶级容器，而key_edtprefs不是它的直接字节点
        ////((PreferenceGroup)findPreference("key_category")).removeAll();//仅删除掉key_category下对应Preference节点下的所有子节点
    }
}