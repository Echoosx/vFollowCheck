package org.echoosx.mirai.plugin.utl

import org.echoosx.mirai.plugin.config.BiliConfig
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup

/**
 * 官方api查询B站账号关注列表（只能查询到最新的250个关注）
 * @param uid B站账号uid
 * @return
 */
fun getOfficialFollows(uid:Long):List<Any>?{
    var followList:List<Any> = arrayListOf()
    for(page in 1..5){
        val res: Connection.Response = Jsoup.connect("https://api.bilibili.com/x/relation/followings")
            .header("cookie", BiliConfig.cookie)
            .data("vmid",uid.toString())
            .data("pn",page.toString())
            .data("ps","50")
            .data("jsonp","jsonp")
            .ignoreContentType(true)
            .execute()

        val body = res.body()

        val code = JSONObject(body).getInt("code")
        if(code == 22115)
            return null

        followList = followList.plus(JSONObject(body).getJSONObject("data").getJSONArray("list"))
    }
    return followList
}