package me.atomx2u.rssr.util

import me.atomx2u.rssr.data.util.TimeUtils
import me.atomx2u.rssr.device.util.AppInfoUtils
import java.io.File

class FileLoggerImpl(
    private val timeUtils: TimeUtils,
    private val appInfoUtils: AppInfoUtils
): FileLogger {

    override fun v(msg: String) {
        writeToCurrentDayFile("VERBOSE", msg)
    }

    override fun w(msg: String) {
        writeToCurrentDayFile("INFO", msg, "log/warning/")
    }

    override fun e(msg: String) {
        writeToCurrentDayFile("ERROR", msg, "log/error/")
    }

    private fun writeToCurrentDayFile(header: String, msg: String, folder: String = "log/") {
        val logFileName = "${timeUtils.getCurrentDay()}.log"
        val path = "${appInfoUtils.getAppAbsolutePath()}/$folder"
        File(path).apply {
            if (!exists()) {
                mkdirs()
            } else if (!isDirectory) {
                if (!delete()) {
                    throw Exception("$path is not path, and can not be deleted.")
                } else{
                    mkdirs()
                }
            }
            File("$path$logFileName").writeText(
                "$header-${timeUtils.getCurrentMillis()}: $msg\n"
            )
        }
    }
}