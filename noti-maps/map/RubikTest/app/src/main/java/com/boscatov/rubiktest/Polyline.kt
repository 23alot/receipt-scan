package com.boscatov.rubiktest
import com.google.gson.annotations.SerializedName

data class Polyline (
        @SerializedName("points")
        val points: String
)