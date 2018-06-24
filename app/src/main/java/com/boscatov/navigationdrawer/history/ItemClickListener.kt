package com.boscatov.navigationdrawer.history

import com.boscatov.navigationdrawer.scan.QRRequests.Check

interface ItemClickListener {
    fun onItemClick(check: Check)
}