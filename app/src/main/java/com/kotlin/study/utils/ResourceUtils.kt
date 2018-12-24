package com.kotlin.study.utils

import android.os.Environment
import android.util.Log
import com.kotlin.study.net.NetManager
import com.kotlin.study.net.SignHelper

import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by GG on 2018/12/18.
 */
class  ResourceUtils {

    class EffectFile(val ziptype: String, val version: String, val gift_id: String, val fileurl: String)

    private var filelist: MutableList<EffectFile>? = null

    fun intialize() {

        filelist = ArrayList()

        object : Thread() {
            override fun run() {
                work()
            }
        }.start()
    }


    companion object {
        private var instances : ResourceUtils? = null
        fun getInstance(): com.kotlin.study.utils.ResourceUtils {
            if (instances == null) {
                instances = ResourceUtils()
            }
            return instances as ResourceUtils
        }
    }


    private fun work() {

        // 获取大礼物特效列表
        var present: String? = null

        while (present == null) {
            present = execute("gift")
        }

        /*  //获取主播等级特效列表
        String anlevel = null;

        while (anlevel == null)
        {
            anlevel = execute("anlevel");
        }

        //获取主播勋章特效列表
        String anchorMedal = null;

        while (anchorMedal == null)
        {
            anchorMedal = execute("amedal");
        }
*/

        try {
            filelist!!.add(EffectFile("0", "0", "1", "https://github.com/yyued/SVGA-Samples/blob/master/halloween.svga?raw=true"))

            //            fill(filelist, present);
            //            fill(filelist, anlevel);
            //            fill(filelist, anchorMedal);
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val datasize = filelist!!.size
        var position = 0

        while (position < datasize) {
            val sub = filelist!![position]
            val localpath = getLocalPath(sub.version, sub.gift_id)
            val localtemp = getLocalTemp(sub.version, sub.gift_id)

            if (File(localpath).exists()) {
                position++
            } else if (download(sub.fileurl, localtemp)) {
                Log.e("shell", "网络下载资源成功")
                File(localtemp).renameTo(File(localpath))

                /* if (EffectZip.needDecompress(localpath))
                {
                    EffectZip.decompressFile(localpath);
                }*/
                position++
            }
        }

    }

    @Throws(Exception::class)
    private fun fill(list: MutableList<EffectFile>, text: String) {
        var json = JSONObject(text)
        json = json.getJSONObject("content")
        val array = json.getJSONArray("list")

        val length = array.length()

        for (i in 0 until length) {
            val item = array.getJSONObject(i)
            val ziptype = item.getString("type")

            val zips = item.getJSONArray("zips")
            val datasize = zips.length()

            for (j in 0 until datasize) {
                val zip = zips.getJSONObject(j)
                val keys = zip.keys()

                var version = ""
                var gift_id = ""
                var fileurl = ""

                while (keys.hasNext()) {
                    val key = keys.next()

                    if (key == "ver") {
                        version = zip.getString(key)
                    } else {
                        fileurl = zip.getString(key)
                        gift_id = key
                    }
                }

                list.add(EffectFile(ziptype, version, gift_id, fileurl))
            }
        }

    }

    private fun getReqUrl(fields: String): String {
        val params = HashMap<String, String>()
        val time = System.currentTimeMillis()
        params["r"] = time.toString()
        params["fields"] = fields
        SignHelper.addSign(params)

        val builder = StringBuffer()
        //        builder.append(ApiConstant.URL_GIFT_ENTER);
        builder.append("?")

        val iterator = params.entries.iterator()

        while (iterator.hasNext()) {
            val en = iterator.next()
            builder.append(en.key)
            builder.append("=")
            builder.append(en.value)

            if (iterator.hasNext()) {
                builder.append("&")
            }
        }

        return builder.toString()
    }

    @Throws(Exception::class)
    private fun readText(connection: HttpURLConnection): String {
        val responseCode = connection.responseCode
        val builder = StringBuilder()

        if (responseCode == HttpURLConnection.HTTP_OK) {
            val `is` = connection.inputStream

            val br = BufferedReader(InputStreamReader(`is`))
            var line: String? = null

            //kotlin表达方式
            while (true) {
                line = br.readLine()?: break
                builder.append(line)
            }

            br.close()
        }

        return builder.toString()
    }

    private fun execute(fields: String): String? {

        var connection: HttpURLConnection? = null
        var result: String? = null

        try {
            val url = "https://github.com/yyued/SVGA-Samples/blob/master/halloween.svga?raw=true"//getReqUrl(fields);

            connection = URL(url).openConnection() as HttpURLConnection
            connection.connectTimeout = 10 * 1000
            connection.readTimeout = 10 * 1000
            connection.requestMethod = "GET"

            val config = NetManager.getInstance().getHttpConfig()
            val header = if (config != null) config!!.getHeader() else null

            if (header != null) {
                val set = header!!.entries
                for (entry in set) {
                    connection.setRequestProperty(entry.key, entry.value)
                }
            }

            result = readText(connection)
            connection.disconnect()
        } catch (e: Exception) {
            result = null
            if (connection != null) {
                try {
                    //主动关闭inputStream
                    //这里不需要进行判空操作
                    connection.getInputStream().close();
                } catch (e:IOException) {
                    e.printStackTrace();
                }
                connection.disconnect();
            }

        }

        if (connection != null) {

            connection.disconnect()
        }

        return result
    }

    fun isAvaliable(id: String): Boolean {
        val filepath = getFilePath(id)
        return File(filepath).exists()
    }

    fun getFilePath(id: String): String {
        var find: EffectFile? = null
        val size = filelist!!.size

        for (i in 0 until size) {
            val file = filelist!![i]

            if (file.gift_id == id) {
                find = file
                break
            }
        }

        return if (find != null) getLocalPath(find.version, find.gift_id) else ""
    }

    /**
     * 下载网络文件
     *
     * @param url      网络文件地址
     * @param savepath 本地存储位置
     * @return
     */
    private fun download(url: String, savepath: String): Boolean {
        var connect: HttpURLConnection? = null
        var fos: FileOutputStream? = null
        var sis: InputStream? = null
        var success = true

        val file = File(savepath)
        val dir = file.parentFile

        if (!dir.exists()) {
            dir.mkdirs()
        }

        var responseCode = 0

        try {

            connect = URL(url).openConnection() as HttpURLConnection
            connect.readTimeout = 10 * 1000
            connect.connectTimeout = 15 * 1000
            connect.requestMethod = "GET"
            connect.doInput = true
            connect.setRequestProperty("Accept-Encoding", "identity")

            if (file.length() > 0) {
                connect.setRequestProperty("Range", "bytes=" + file.length() + "-")
            }

            connect.connect()
            responseCode = connect.responseCode

            if (responseCode == 200 || responseCode == 206) {
                sis = connect.inputStream
                fos = FileOutputStream(savepath, true)

                val buffer = ByteArray(20480)
                var recvsize: Long = 0
                var readsize = 0

                while (({readsize = sis!!.read(buffer);readsize}()) > 0) {
                    fos.write(buffer, 0, readsize)
                    fos.flush()
                    recvsize = recvsize + readsize
                }

                sis!!.close()
                fos.close()
            } else {
                success = false
            }
        } catch (e: Exception) {
            success = false
        }


        try {
            if (sis != null) {
                sis.close()
            }
            if (fos != null) {
                fos.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (connect != null) {
            connect.disconnect()
        }

        if (responseCode == 416) {
            file.delete()
        }

        return success

    }

    /**
     * 获取本地存储路径
     *
     * @param version 大礼物版本号
     * @param gift_id 大礼物id
     * @return
     */
    private fun getLocalPath(version: String, gift_id: String): String {
        return Environment.getExternalStorageDirectory().toString() + "/kotlin/zip/" + version + "/" + gift_id + ".zip"
    }

    /**
     * 获取下载临时路径
     *
     * @param version 大礼物版本号
     * @param gift_id 大礼物id
     * @return
     */
    private fun getLocalTemp(version: String, gift_id: String): String {
        return Environment.getExternalStorageDirectory().toString() + "/kotlin/zip/" + version + "/" + gift_id + ".tmp"
    }

}