package com.boscatov.navigationdrawer.scan.QRRequests

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

data class Check (
        @SerializedName("addressToCheckFiscalSign")
        val addressToCheckFiscalSign: String,
        @SerializedName("buyerAddress")
        val buyerAddress: String,
        @SerializedName("cashTotalSum")
        val cashTotalSum: Int,
        @SerializedName("dateTime")
        val dateTime: String,
        @SerializedName("ecashTotalSum")
        val ecashTotalSum: Int,
        @SerializedName("fiscalDocumentNumber")
        val fiscalDocumentNumber: Int,
        @SerializedName("fiscalDriveNumber")
        val fiscalDriveNumber: String,
        @SerializedName("kktRegId")
        val kktRegId: String,
        @SerializedName("nds18")
        val nds18: Int,
        @SerializedName("operationType")
        val operationType: Int,
        @SerializedName("operator")
        val operator: String,
        @SerializedName("receiptCode")
        val receiptCode: Int,
        @SerializedName("requestNumber")
        val requestNumber: Int,
        @SerializedName("retailPlaceAddress")
        val retailPlaceAddress: String,
        @SerializedName("senderAddress")
        val senderAddress: String,
        @SerializedName("shiftNumber")
        val shiftNumber: Int,
        @SerializedName("taxationType")
        val taxationType: Int,
        @SerializedName("totalSum")
        val totalSum: Int,
        @SerializedName("user")
        val user: String,
        @SerializedName("userInn")
        val userInn: String,
        @SerializedName("items")
        val items: ArrayList<Item>
) : Comparable<Check> {
        override fun compareTo(other: Check): Int {
            val date1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this.dateTime)
            val date2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(other.dateTime)
            return date2.compareTo(date1)
        }
}
