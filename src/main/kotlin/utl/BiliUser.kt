package org.echoosx.mirai.plugin.utl

import org.echoosx.mirai.plugin.BiliConfig.cookie
import org.echoosx.mirai.plugin.VFollowCheck
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun getMidByNick(nick:String):Long?{
    try {
        val document: Document = Jsoup.connect("https://search.bilibili.com/upuser")
            .header("cookie",cookie)
            .data("keyword",nick)
            .data("order","0")
            .data("order_sort","0")
            .data("user_type","0")
            .data("page","1")
            .get()

        val box = document.select("li.user-item")
        var space:String? = null
        box.forEach {
            val user = it.select("div.info-wrap>div.headline>a")
            val uname  = user.attr("title")
            if(uname == nick) {
                space = "https:" + user.attr("href")
                return@forEach
            }
        }

        if(space == null)
            return null
        val regex = """space\.bilibili\.com/([0-9]+)\?""".toRegex()
        val mid = regex.find(space!!)?.groups?.get(1)?.value?.toLong()

        return mid
    }catch (e:Throwable){
        VFollowCheck.logger.error(e)
        return null
    }
}