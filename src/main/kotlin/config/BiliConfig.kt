package org.echoosx.mirai.plugin.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object BiliConfig:AutoSavePluginConfig("Setting") {
    @ValueDescription("Cookie")
    val cookie: String by value()

    @ValueDescription("Vtb列表更新间隔（Cron表达式）")
    val updateCron: String by value("0 0 0 * * ?")
}