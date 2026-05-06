package com.example.journey.utils

import android.content.ClipData
import android.content.Context
import android.content.ClipboardManager

fun Context.copyToClipboard(
    text: String,
    label: String = "Journey Data"
){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
}