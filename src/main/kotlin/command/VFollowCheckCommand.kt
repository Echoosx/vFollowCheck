package org.echoosx.mirai.plugin.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.At
import org.echoosx.mirai.plugin.VFollowCheck
import org.echoosx.mirai.plugin.utl.FollowInvisibleException
import org.echoosx.mirai.plugin.utl.httpGET
import org.json.JSONObject

object VFollowCheckCommand:SimpleCommand(
    VFollowCheck,
    "ccf","查成分",
    description = "查询B站关注的虚拟主播"
){
    @OptIn(ConsoleExperimentalApi::class, ExperimentalCommandDescriptors::class)
    override val prefixOptional: Boolean = true

    @Handler
    @Suppress("unused")
    suspend fun CommandSender.handle(unameOrUid:String){
        try{
            val msg = buildString {
                val url = "https://api.asoulfan.com/cfj/?name=${unameOrUid}"
                val response = httpGET(url)
                val code = JSONObject(response).getInt("code")
                if(code == 22115) throw FollowInvisibleException(unameOrUid)
                val total = JSONObject(response).getJSONObject("data").getInt("total")
                appendLine("B站用户【${unameOrUid}】关注的Vup共${total}位：")
                val vupList = JSONObject(response).getJSONObject("data").getJSONArray("list")
                for(vup in vupList){
                    append((vup as JSONObject).getString("uname"))
                    append("、")
                }
                deleteAt(length-1)
                if(vupList.length() < total) append("……")
            }
            if(getGroupOrNull() == null)
                sendMessage(msg)
            else
                sendMessage(At(user!!) +"\n" + msg)
        }catch (e: FollowInvisibleException){
            sendMessage(e.message)
        }catch (e:Exception){
            sendMessage("查询失败")
            VFollowCheck.logger.error(e)
        }
    }
}