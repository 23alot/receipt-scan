package com.boscatov.richnotification

import android.widget.Toast
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context


class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        Toast.makeText(context, "12345", Toast.LENGTH_LONG).show()
    }

    companion object {
        private val TAG = "MyBroadcastReceiver"
    }
}