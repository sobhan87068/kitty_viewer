package com.example.kittyviewer.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kitty(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
) : Parcelable
