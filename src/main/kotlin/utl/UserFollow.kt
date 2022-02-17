package org.echoosx.mirai.plugin.utl

import org.echoosx.mirai.plugin.BiliConfig
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup

fun getFollows(mid:Long):List<Any>?{

    var followList:List<Any> = arrayListOf()
    for(page in 1..5){
        val res: Connection.Response = Jsoup.connect("https://api.bilibili.com/x/relation/followings")
            .header("cookie", BiliConfig.cookie)
            .data("vmid",mid.toString())
            .data("pn",page.toString())
            .data("ps","50")
            .data("jsonp","jsonp")
            .ignoreContentType(true)
            .execute()

        val body = res.body()

        val code = JSONObject(body).getInt("code")
        if(code == 22115)
            return null
        val tempList = JSONObject(body).getJSONObject("data").getJSONArray("list")

        followList = followList.plus(tempList)
    }
    return followList
}