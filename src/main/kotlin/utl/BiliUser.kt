package org.echoosx.mirai.plugin.utl

import org.echoosx.mirai.plugin.config.BiliConfig.cookie
import org.echoosx.mirai.plugin.VFollowCheck
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup

fun getMidByNick(nick:String):Long?{
    try {
        val response: Connection.Response = Jsoup.connect("https://api.bilibili.com/x/web-interface/search/type")
            .header("cookie",cookie)
            .data("search_type","bili_user")
            .data("keyword",nick)
            .data("page","1")
            .data("page_size","50")
            .data("order_sort","0")
            .data("user_type","0")
            .ignoreContentType(true)
            .execute()

        val body = response.body()
        val result = JSONObject(body).getJSONObject("data").getJSONArray("result")
        for(res in result){
            val uname = (res as JSONObject).getString("uname")
            if(uname == nick) {
                return res.getLong("mid")
            }
        }
        return null

    }catch (e:Throwable){
        VFollowCheck.logger.error(e)
        return null
    }
}