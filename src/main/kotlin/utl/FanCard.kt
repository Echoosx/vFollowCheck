package org.echoosx.mirai.plugin.utl

import org.echoosx.mirai.plugin.VFollowCheck.vtb_list
import org.echoosx.mirai.plugin.config.BiliConfig
import org.echoosx.mirai.plugin.data.Card
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup


fun getFanCard(unameOrUid:String):String{
    var mid:Long? = null
    mid = if(unameOrUid.matches(Regex("[0-9]+"))){
        unameOrUid.toLong()
    }else{
        getMidByNick(unameOrUid)
    }
    if(mid == null) throw NoUserException(unameOrUid)

    val res: Connection.Response = Jsoup.connect("https://api.live.bilibili.com/xlive/web-ucenter/user/MedalWall")
        .header("cookie", BiliConfig.cookie)
        .data("target_id",mid.toString())
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
            append("用户【${unameOrUid}】没有虚拟主播粉丝牌")
            return@buildString
        }
        appendLine("B站用户【${unameOrUid}】的虚拟主播粉丝牌：")
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