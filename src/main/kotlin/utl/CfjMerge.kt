package org.echoosx.mirai.plugin.utl

import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup

fun mergeCfj(unameOrUid:String, originList:MutableSet<String>):Int{

    val res: Connection.Response = Jsoup.connect("https://api.asoulfan.com/cfj/")
        .data("name",unameOrUid)
        .ignoreContentType(true)
        .execute()

    val body = res.body()
    val code = JSONObject(body).getInt("code")
    if(code == 22115) throw FollowInvisibleException(unameOrUid)
    val cfjList = JSONObject(body).getJSONObject("data").getJSONArray("list")
    val total = JSONObject(body).getJSONObject("data").getInt("total")

    for(follow in cfjList) {
        val uname = (follow as JSONObject).getString("uname")
        originList.add(uname)
    }
    return total
}