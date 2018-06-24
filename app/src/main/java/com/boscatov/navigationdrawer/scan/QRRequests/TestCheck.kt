package com.boscatov.navigationdrawer.scan.QRRequests

import com.google.gson.annotations.SerializedName

data class TestCheck(
        @SerializedName("is_receipt_legal")
        val isLegal: Boolean)