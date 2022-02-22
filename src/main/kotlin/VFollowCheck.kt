package org.echoosx.mirai.plugin

import kotlinx.coroutines.launch
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.utils.info
import org.echoosx.mirai.plugin.command.FanCardCheck
import org.echoosx.mirai.plugin.config.BiliConfig.updateDuring
import org.echoosx.mirai.plugin.command.FollowCheck
import org.echoosx.mirai.plugin.config.BiliConfig
import org.echoosx.mirai.plugin.config.SupplyVtbs
import org.echoosx.mirai.plugin.config.SupplyVtbs.supply
import org.echoosx.mirai.plugin.data.VtbUpdate
import org.echoosx.mirai.plugin.data.VtbUpdate.latest
import org.echoosx.mirai.plugin.utl.getVtbs
import java.text.SimpleDateFormat
import java.util.*

object VFollowCheck : KotlinPlugin(
    JvmPluginDescription(
        id = "org.echoosx.mirai.plugin.VFollowCheck",
        name = "VFollowCheck",
        version = "1.1.0"
    ) {
        author("Echoosx")
    }
) {
    var vtb_list:MutableSet<Long> = mutableSetOf()
    override fun onEnable() {
        FollowCheck.register()
        FanCardCheck.register()
        BiliConfig.reload()
        VtbUpdate.reload()
        SupplyVtbs.reload()

        logger.info { "vFollowCheck loaded" }
        GlobalEventChannel.subscribeAlways<BotOnlineEvent> {
            val updateTimer = object :TimerTask(){
                override fun run() {
                    this@VFollowCheck.launch {
                        vtb_list = getVtbs()
                        latest = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                        vtb_list.addAll(supply)
                        val new = vtb_list.size
                        logger.info("Vtuber列表更新，现有数据${new}条，新增${new - VtbUpdate.size}条")
                        VtbUpdate.size = new
                    }
                }
            }
            Timer().schedule(updateTimer, Date(), updateDuring.toLong() * 60 * 60 * 1000)
        }
    }
}
