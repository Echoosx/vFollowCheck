package org.echoosx.mirai.plugin

import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info
import org.echoosx.mirai.plugin.command.VFollowCheckCommand

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "org.echoosx.vFollowCheck",
        name = "vFollowCheck",
        version = "1.0.0"
    ) {
        author("Echoosx")
    }
) {
    override fun onEnable() {
        VFollowCheckCommand.register()
        logger.info { "vFollowCheck loaded" }
    }
}
