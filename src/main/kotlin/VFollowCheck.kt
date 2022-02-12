package org.echoosx.mirai.plugin

import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info
import org.echoosx.mirai.plugin.command.VFollowCheckCommand

object VFollowCheck : KotlinPlugin(
    JvmPluginDescription(
        id = "org.echoosx.mirai.plugin.vFollowCheck",
        name = "vFollowCheck",
        version = "1.0.1"
    ) {
        author("Echoosx")
    }
) {
    override fun onEnable() {
        VFollowCheckCommand.register()
        logger.info { "vFollowCheck loaded" }
    }
}
