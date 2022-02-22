package org.echoosx.mirai.plugin.utl

class FollowInvisibleException(unameOrUid:String):Exception(){
    override val message: String = "用户【${unameOrUid}】隐藏了关注，成分一目了然"
}

class NoUserException(unameOrUid:String):Exception(){
    override val message: String = "未查询到用户【${unameOrUid}】"
}