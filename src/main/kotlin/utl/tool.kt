package org.echoosx.mirai.plugin.utl

import okhttp3.OkHttpClient
import okhttp3.Request
import org.echoosx.mirai.plugin.PluginMain
import java.io.IOException

class FollowInvisibleException(unameOrUid:String):Exception(){
    override val message: String = "用户【${unameOrUid}】隐藏了关注"
}

fun httpGET(url: String):String{
    var tempString = ""
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .get()
        .build()
    //同步处理
    val call = client.newCall(request)
    try {
        val response = call.execute()
        tempString = response.body?.string().toString()
    } catch (e: IOException) {
        PluginMain.logger.error(e)
    }
    return tempString
}