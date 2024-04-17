package com.example.kittyviewer.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteKitty(
    @SerialName("id") val id: String,
    @SerialName("url") val url: String?,
    @SerialName("width") val width: Int?,
    @SerialName("height") val height: Int?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RemoteKitty

        if (id != other.id) return false
        if (url != other.url) return false
        if (width != other.width) return false
        return height == other.height
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (width ?: 0)
        result = 31 * result + (height ?: 0)
        return result
    }
}