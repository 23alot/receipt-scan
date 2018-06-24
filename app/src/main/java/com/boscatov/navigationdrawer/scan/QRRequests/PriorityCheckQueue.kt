package com.boscatov.navigationdrawer.scan.QRRequests

import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator

class PriorityCheckQueue: PriorityQueue<Check>() {
    override fun comparator(): CheckComparator<Check> {
        return CheckComparator()
    }

    class CheckComparator<Check> : Comparator<com.boscatov.navigationdrawer.scan.QRRequests.Check> {
        override fun compare(c1: com.boscatov.navigationdrawer.scan.QRRequests.Check, c2: com.boscatov.navigationdrawer.scan.QRRequests.Check): Int {
            val date1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(c1.dateTime)
            val date2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(c2.dateTime)
            return date2.compareTo(date1)
        }
    }
}