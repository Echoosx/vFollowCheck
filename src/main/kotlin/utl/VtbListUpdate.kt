package org.echoosx.mirai.plugin.utl

import org.echoosx.mirai.plugin.VFollowCheck
import org.echoosx.mirai.plugin.config.SupplyVtbs
import org.echoosx.mirai.plugin.data.VtbUpdate
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

internal class VtbListUpdate: Job {
    private val logger get() = VFollowCheck.logger

    @Throws(JobExecutionException::class)
    override fun execute(jobExecutionContext: JobExecutionContext?) {
        // 当前时间
        val date = Date()
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dateStr = dateFormat.format(date)

        // 工作内容
        VFollowCheck.vtb_list = getVtbs()
        VFollowCheck.vtb_list.addAll(SupplyVtbs.supply)
        val new = VFollowCheck.vtb_list.size
        logger.info("Vtuber列表更新，现有Vtuber数：${new}，新增：${new - VtbUpdate.size} 执行时间：$dateStr")
        VtbUpdate.size = new
        VtbUpdate.latest = dateStr
    }
}