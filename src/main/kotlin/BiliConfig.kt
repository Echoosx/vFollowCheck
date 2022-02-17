package org.echoosx.mirai.plugin

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object BiliConfig:AutoSavePluginConfig("Setting") {
    @ValueDescription("Cookie")
    val cookie: String by value()

    @ValueDescription("更新间隔/h")
    val updateDuring: Int by value(24)
}