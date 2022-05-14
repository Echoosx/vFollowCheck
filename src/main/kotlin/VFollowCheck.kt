package org.echoosx.mirai.plugin

import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info
import org.echoosx.mirai.plugin.command.FanCardCheck
import org.echoosx.mirai.plugin.command.FollowCheck
import org.echoosx.mirai.plugin.config.BiliConfig
import org.echoosx.mirai.plugin.config.BiliConfig.updateCron
import org.echoosx.mirai.plugin.config.SupplyVtbs
import org.echoosx.mirai.plugin.data.VtbUpdate
import org.echoosx.mirai.plugin.utl.VtbListUpdate
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.TriggerBuilder
import org.quartz.impl.StdSchedulerFactory

object VFollowCheck : KotlinPlugin(
    JvmPluginDescription(
        id = "org.echoosx.mirai.plugin.VFollowCheck",
        name = "VFollowCheck",
        version = "1.2.0"
    ) {
        author("Echoosx")
    }
) {
    var vtb_list:MutableSet<Long> = mutableSetOf()
    override fun onEnable() {
        logger.info { "vFollowCheck loaded" }

        FollowCheck.register()
        FanCardCheck.register()
        BiliConfig.reload()
        VtbUpdate.reload()
        SupplyVtbs.reload()

        val scheduler = StdSchedulerFactory.getDefaultScheduler()

        val jobDetail = JobBuilder.newJob(VtbListUpdate::class.java)
            .build()

        val trigger = TriggerBuilder.newTrigger()
            .withSchedule(
                CronScheduleBuilder.cronSchedule(updateCron)
            )
            .startNow()
            .build()

        scheduler.scheduleJob(jobDetail,trigger)
        scheduler.start()
    }
}
