package org.echoosx.mirai.plugin.utl

import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup

/**
 * 将成分姬的查询结果合并进主查询（因为成分姬能查询到250+之后的关注）
 * @param uid 账号uid
 * @param originList 主查询结果串
 * @return 成分姬查询结果数
 */
fun mergeCfj(uid:Long, originList:MutableSet<String>):Int{
    try {
        val res: Connection.Response = Jsoup.connect("https://api.asoulfan.com/cfj/")
            .data("name", uid.toString())
            .ignoreContentType(true)
            .execute()

        val body = res.body()
        JSONObject(body).getInt("code").apply { if (this == 22115) return 0 }
        val cfjList = JSONObject(body).getJSONObject("data").getJSONArray("list")
        val total = JSONObject(body).getJSONObject("data").getInt("total")
        for(follow in cfjList) {
            val uname = (follow as JSONObject).getString("uname")
            originList.add(uname)
        }
        return total
    }catch (e:Throwable){
        logger.error("Error:$e")
        return 0
    }
}