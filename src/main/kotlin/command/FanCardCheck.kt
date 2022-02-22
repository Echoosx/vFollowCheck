package org.echoosx.mirai.plugin.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.At
import org.echoosx.mirai.plugin.VFollowCheck
import org.echoosx.mirai.plugin.utl.FollowInvisibleException
import org.echoosx.mirai.plugin.utl.NoUserException
import org.echoosx.mirai.plugin.utl.getFanCard

object FanCardCheck : SimpleCommand(
    VFollowCheck,
    "fsp","粉丝牌",
    description = "查询B站用户粉丝牌中的V"
) {
    @OptIn(ConsoleExperimentalApi::class, ExperimentalCommandDescriptors::class)
    override val prefixOptional: Boolean = true

    @Handler
    @Suppress("unused")
    suspend fun CommandSender.handle(unameOrUid:String){
        try{
            val msg = getFanCard(unameOrUid)
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