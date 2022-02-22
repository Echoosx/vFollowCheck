package org.echoosx.mirai.plugin.utl

import org.echoosx.mirai.plugin.VFollowCheck
import org.json.JSONObject

fun getFollowVupName(unameOrUid:String):String{
    val msg = buildString {
        var mid:Long? = null
        mid = if(unameOrUid.matches(Regex("[0-9]+"))){
            unameOrUid.toLong()
        }else{
            getMidByNick(unameOrUid)
        }
        if(mid == null) throw NoUserException(unameOrUid)

        val followVupList:MutableSet<String> = mutableSetOf()
        val followList = getFollows(mid)
        if (followList != null) {
            for(follow in followList){
                val fmid = (follow as JSONObject).getLong("mid")
                if(fmid in VFollowCheck.vtb_list){
                    val uname = follow.getString("uname")
                    followVupList.add(uname)
                }
            }
            var total = mergeCfj(unameOrUid,followVupList)
            if(followVupList.size == 0){
                append("用户【${unameOrUid}】未关注虚拟主播")
                return@buildString
            }
            total = if(followVupList.size < total && total > 50){
                total - 50 + followVupList.size
            }else
                followVupList.size

            appendLine("用户【${unameOrUid}】关注的虚拟主播共${total}位：")
            for(uname in followVupList){
                append("${uname}、")
            }
            deleteAt((length-1))
            if(followList.size < total) append("……")
        }else throw FollowInvisibleException(unameOrUid)
    }
    return msg
}