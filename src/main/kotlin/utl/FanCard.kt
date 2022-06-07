package org.echoosx.mirai.plugin.utl

import org.echoosx.mirai.plugin.VFollowCheck.vtb_list
import org.echoosx.mirai.plugin.config.BiliConfig
import org.echoosx.mirai.plugin.data.Card
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup

/**
 * 获取B站账号虚拟主播粉丝牌的主函数
 * @param unameOrUid 用户输入，uid或昵称
 * @return 格式化消息
 */
fun getFanCard(unameOrUid:String):String{
    val (nick,uid) = getUserUid(unameOrUid)

    val res: Connection.Response = Jsoup.connect("https://api.live.bilibili.com/xlive/web-ucenter/user/MedalWall")
        .header("cookie", BiliConfig.cookie)
        .data("target_id",uid.toString())
        .ignoreContentType(true)
        .execute()

    val body = res.body()
    val cardNodes = JSONObject(body).getJSONObject("data").getJSONArray("list")

    val cardVupList:MutableList<Card> = arrayListOf()
    for(card in cardNodes){
        val mid = (card as JSONObject).getJSONObject("medal_info").getLong("target_id")
        if(mid in vtb_list){
            val uname = card.getString("target_name")
            val level = card.getJSONObject("medal_info").getInt("level")
            val wear = card.getJSONObject("medal_info").getInt("wearing_status")==1
            cardVupList.add(Card(uname,level,wear))
        }
    }

    val msg = buildString {
        if(cardVupList.size == 0) {
            append("用户【${nick}】没有虚拟主播粉丝牌")
            return@buildString
        }
        appendLine("B站用户【${nick}】的虚拟主播粉丝牌：")
        for(card in cardVupList){
            if(card.wear)
                appendLine("【${card.uname}】${card.level}级 (佩戴中)")
            else
                appendLine("【${card.uname}】${card.level}级")
        }
        deleteAt((length-1))
    }

    return msg
}