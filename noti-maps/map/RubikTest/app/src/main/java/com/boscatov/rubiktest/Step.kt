package com.boscatov.rubiktest
import com.google.gson.annotations.SerializedName

data class Step (
        @SerializedName("polyline")
        val polyline: Polyline
)