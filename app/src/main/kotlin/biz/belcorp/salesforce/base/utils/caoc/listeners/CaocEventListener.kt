package biz.belcorp.salesforce.base.utils.caoc.listeners

import java.io.Serializable

interface CaocEventListener : Serializable {

    fun onLaunchErrorActivity()

    fun onRestartAppFromErrorActivity()

}
