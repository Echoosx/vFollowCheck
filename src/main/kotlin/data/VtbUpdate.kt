package org.echoosx.mirai.plugin.data

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object VtbUpdate:AutoSavePluginData("vtb_update") {
    @ValueDescription("更新时间")
    var latest:String by value()

    @ValueDescription("数据量")
    var size:Int by value()
}