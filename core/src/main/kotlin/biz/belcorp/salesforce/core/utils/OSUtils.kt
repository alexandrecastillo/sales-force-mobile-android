package biz.belcorp.salesforce.core.utils

import android.os.Process
import kotlin.system.exitProcess

object OSUtils {

    fun killCurrentProcess() {
        Process.killProcess(Process.myPid())
        exitProcess(10)
    }

}
