package org.echoosx.mirai.plugin.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object SupplyVtbs:AutoSavePluginConfig("Supply") {
    @ValueDescription("补充UID")
    val supply:Set<Long> by value()
}