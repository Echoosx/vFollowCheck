package org.echoosx.mirai.plugin.utl

import org.echoosx.mirai.plugin.config.BiliConfig.cookie
import org.echoosx.mirai.plugin.VFollowCheck
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup


/**
 * 由用户输入获取B站账号uid
 * @param uidOrNick 用户输入，可能是uid或昵称
 * @return uid：Long类型
 */
fun getUserUid(uidOrNick:String):Pair<String,Long>{
    val uidRegex = Regex("""^((?i)uid)?[:：]?([0-9]+)$""")
    val uid = uidRegex.matchEntire(uidOrNick)?.groupValues?.get(2)
    return if(uid.isNullOrBlank()) {
        getUidByNick(uidOrNick) ?: throw NoUserException(uidOrNick)
    }else{
        Pair("UID:${uid}",uid.toLong())
    }
}

/**
 * 使用B站api通过昵称获取账号uid
 * @param nick 昵称
 * @return uid：Long或null
 */
fun getUidByNick(nick:String):Pair<String,Long>?{
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

        val candidateList = mutableListOf<Pair<String,Long>>()
        val body = response.body()
        JSONObject(body).getJSONObject("data").getInt("numResults").apply { if(this==0) return null }
        val result = JSONObject(body).getJSONObject("data").getJSONArray("result")
        for(res in result){
            val uname = (res as JSONObject).getString("uname")
            if(uname.equals(nick,ignoreCase = true)) {
                candidateList.add(Pair(uname,res.getLong("mid")))
            }
        }
        if(candidateList.size <= 0){
            return null
        }else if(candidateList.size == 1){
            return candidateList.first()
        }else{
            for(candidate in candidateList){
                if(candidate.first == nick){
                    return candidate
                }
            }
        }
        return null
    }catch (e:Throwable){
        VFollowCheck.logger.error(e)
        return null
    }
}