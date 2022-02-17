package org.echoosx.mirai.plugin.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.At
import org.echoosx.mirai.plugin.VFollowCheck
import org.echoosx.mirai.plugin.utl.*

object FollowCheck:SimpleCommand(
    VFollowCheck,
    "ccf","查成分",
    description = "查询B站关注虚拟主播"
) {
    @OptIn(ConsoleExperimentalApi::class, ExperimentalCommandDescriptors::class)
    override val prefixOptional: Boolean = true

    @Handler
    @Suppress("unused")
    suspend fun CommandSender.handle(unameOrUid:String){
        try{
            val msg = getFollowVupName(unameOrUid)
            if(getGroupOrNull() == null)
                sendMessage(msg)
            else
                sendMessage(At(user!!) +"\n" + msg)
        }catch (e: NoUserException) {
            sendMessage(e.message)
        }
        catch (e: FollowInvisibleException) {
            sendMessage(e.message)
        } catch (e: Throwable){
            sendMessage("查询失败")
            VFollowCheck.logger.error(e)
        }
    }
}