package org.echoosx.mirai.plugin.utl

import org.echoosx.mirai.plugin.VFollowCheck
import org.json.JSONObject

/**
 * 获取用户关注Vtuber的主函数
 * @param unameOrUid 用户输入，uid或昵称
 * @return 格式化消息
 */
fun getFollowVup(unameOrUid:String):String{
    val msg = buildString {
        val (nick,uid) = getUserUid(unameOrUid)
        val followVupList:MutableSet<String> = mutableSetOf()
        val followList = getOfficialFollows(uid)

        if (followList != null) {
            for(follow in followList){
                val mid = (follow as JSONObject).getLong("mid")
                if(mid in VFollowCheck.vtb_list){
                    val uname = follow.getString("uname")
                    followVupList.add(uname)
                }
            }
            var total = mergeCfj(uid,followVupList)
            if(followVupList.size == 0){
                append("用户【${nick}】未关注虚拟主播")
                return@buildString
            }
            total = if(followVupList.size < total && total > 50){
                total - 50 + followVupList.size
            }else
                followVupList.size

            appendLine("用户【${nick}】关注的虚拟主播共${total}位：")
            for(uname in followVupList){
                append("${uname}、")
            }
            deleteAt((length-1))
            if(followList.size < total) append("……")
        }else throw FollowInvisibleException(unameOrUid)
    }
    return msg
}