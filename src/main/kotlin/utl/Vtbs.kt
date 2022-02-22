package org.echoosx.mirai.plugin.utl

import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup

fun getVtbs():MutableSet<Long>{
    val res: Connection.Response = Jsoup.connect("https://vtbs.musedash.moe/v1/vtbs")
        .ignoreContentType(true)
        .execute()

    val body = res.body()
    val midList:MutableSet<Long> = mutableSetOf()
    for(vtb in JSONArray(body)){
        midList.add((vtb as JSONObject).getLong("mid"))
    }
    return midList
}