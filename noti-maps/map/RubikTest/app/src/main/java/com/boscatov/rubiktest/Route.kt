package com.boscatov.rubiktest
import com.google.gson.annotations.SerializedName

data class Route (
        @SerializedName("legs")
        val legs: ArrayList<Leg>
)