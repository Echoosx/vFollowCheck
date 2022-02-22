package org.echoosx.mirai.plugin.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Card(
    @SerialName("uname")
    val uname:String,
    @SerialName("level")
    val level:Int,
    @SerialName("wear")
    val wear:Boolean
)