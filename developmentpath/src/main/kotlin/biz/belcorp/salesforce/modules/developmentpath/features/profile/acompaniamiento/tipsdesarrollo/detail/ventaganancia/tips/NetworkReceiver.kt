package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import biz.belcorp.salesforce.core.utils.isOnline
import java.util.*

class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            val isOnline = context?.isOnline() ?: false
            ObservableObject.instance.updateValue(isOnline)
        }
    }

    class ObservableObject : Observable() {
        fun updateValue(connected: Boolean) {
            synchronized(this) {
                setChanged()
                notifyObservers(connected)
            }
        }

        companion object {
            val instance = ObservableObject()
        }
    }
}
