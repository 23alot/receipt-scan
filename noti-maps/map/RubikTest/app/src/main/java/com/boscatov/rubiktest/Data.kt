package com.boscatov.rubiktest
import com.google.gson.annotations.SerializedName

data class Data(
        @SerializedName("routes")
        val routes: ArrayList<Route>
)