package com.clawgui.android.core.util

import android.util.Log as AndroidLog

/**
 * 统一日志出口。所有 tag 自带 `ClawGUI/` 前缀,排查时一条命令能捞全:
 * ```
 * adb logcat -d | grep ClawGUI
 * ```
 *
 * 每条日志强制带 `sessionKey`(可为 null),跨线程/跨 channel 时能对上同一笔 turn。
 */
object Log {
    private const val TAG_PREFIX = "ClawGUI/"

    fun i(tag: String, key: String?, msg: String) {
        AndroidLog.i(TAG_PREFIX + tag, format(key, msg))
    }

    fun w(tag: String, key: String?, msg: String, t: Throwable? = null) {
        if (t == null) AndroidLog.w(TAG_PREFIX + tag, format(key, msg))
        else AndroidLog.w(TAG_PREFIX + tag, format(key, msg), t)
    }

    fun e(tag: String, key: String?, msg: String, t: Throwable? = null) {
        if (t == null) AndroidLog.e(TAG_PREFIX + tag, format(key, msg))
        else AndroidLog.e(TAG_PREFIX + tag, format(key, msg), t)
    }

    private fun format(key: String?, msg: String): String =
        if (key.isNullOrBlank()) msg else "[$key] $msg"
}
