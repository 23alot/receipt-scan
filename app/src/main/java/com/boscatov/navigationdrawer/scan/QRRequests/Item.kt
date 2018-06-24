package com.boscatov.navigationdrawer.scan.QRRequests

import com.google.gson.annotations.SerializedName

data class Item(
        @SerializedName("barcode")
        val barcode: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("ndsCalculated10")
        val ndsCalculated10: Int,
        @SerializedName("ndsCalculated18")
        val ndsCalculated18: Int,
        @SerializedName("price")
        val price: Int,
        @SerializedName("quantity")
        val quantity: Int,
        @SerializedName("sum")
        val sum: Int
)