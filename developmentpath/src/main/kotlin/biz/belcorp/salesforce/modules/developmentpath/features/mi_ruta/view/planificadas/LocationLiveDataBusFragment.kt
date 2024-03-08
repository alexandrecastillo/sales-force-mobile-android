package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.planificadas

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import com.google.android.gms.maps.model.LatLng

abstract class LocationLiveDataBusFragment : BaseFragment() {

    private val locationObserver = Observer<ConsumableEvent> {
            (it.value as Bundle).apply {
                val latLng = get(HASH_CONSULTANT_LAT_LNG) as LatLng
                val consultantId = get(HASH_CONSULTANT_ID) as Int

                onConsultantLocationUpdated(consultantId, latLng)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSyncState(this)
    }

    private fun observeSyncState(owner: LifecycleOwner) {
        LiveDataBus
            .from<PlanificadasFragment>(EventSubject.CONSULTANTS_LOCATION)
            .observe(owner, locationObserver)
    }

    abstract fun onConsultantLocationUpdated(consultantId: Int, location: LatLng)

    companion object {
        const val HASH_CONSULTANT_LAT_LNG = "HASH_CONSULTANT_LAT_LNG"
        const val HASH_CONSULTANT_ID = "HASH_CONSULTANT_ID"
    }

}
