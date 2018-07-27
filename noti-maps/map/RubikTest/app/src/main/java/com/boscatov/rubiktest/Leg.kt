package com.boscatov.rubiktest
import com.google.gson.annotations.SerializedName

data class Leg (
        @SerializedName("steps")
        val steps: ArrayList<Step>
)