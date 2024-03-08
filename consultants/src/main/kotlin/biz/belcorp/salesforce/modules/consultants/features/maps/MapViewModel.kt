package biz.belcorp.salesforce.modules.consultants.features.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.features.permissions.PermissionsUtil
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.consultora.ConsultoraUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.location.GeoLocationUseCase
import biz.belcorp.salesforce.modules.consultants.features.list.mappers.ConsultoraModelDataMapper
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultoraModel
import biz.belcorp.salesforce.modules.consultants.features.maps.mappers.MapMapper
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class MapViewModel(
    private val permissionUtils: PermissionsUtil,
    private val useCase: GeoLocationUseCase,
    private val consultantUseCase: ConsultoraUseCase,
    private val mapper: ConsultoraModelDataMapper,
    private val mapMapper: MapMapper
) : ViewModel() {

    val location: LiveData<MapViewState>
        get() = _location

    private val _location = MutableLiveData<MapViewState>()

    private var hasConsultantLocation = false

    fun getConsultantInfo(id: Int) {
        viewModelScope.launch(handler) {
            io {
                val consultant = getConsultant(id)
                handleConsultantLocation(consultant)
            }
        }
    }

    fun saveLocation(id: Int, latLng: LatLng) {
        viewModelScope.launch(handler) {
            io {
                val consultant = getConsultant(id)
                useCase.saveGeoLocation(consultant.codigo.orEmpty(), latLng)
                changeConsultantLocation(consultant, latLng)
                val model = mapMapper.map(consultant)
                changeState(MapViewState.SuccessSave(model))
            }
        }
    }

    fun moveToConsultantLocation(id: Int) {
        viewModelScope.launch(handler) {
            val consultant = getConsultant(id)
            if (hasLocationPermissions()) {
                val model = mapMapper.map(consultant)
                if (hasLocation(consultant)) {
                    changeState(MapViewState.WithLocation(model))
                } else {
                    changeState(MapViewState.WithoutLocation(model))
                }
            }
        }
    }

    fun hasConsultantLocation() = hasConsultantLocation

    fun hasLocationPermissions(): Boolean = permissionUtils.isFineLocationPermissionGranted()

    private fun hasLocation(consultant: ConsultoraModel): Boolean = with(consultant) {
        hasConsultantLocation = (latitud != ZERO_DECIMAL && longitud != ZERO_DECIMAL)
        return hasConsultantLocation
    }

    private fun handleConsultantLocation(consultant: ConsultoraModel) {
        if (hasLocationPermissions()) {
            val model = mapMapper.map(consultant)
            val state = if (hasLocation(consultant)) MapViewState.WithLocation(model)
            else MapViewState.WithoutLocation(model)
            changeState(state)
        }
    }

    private fun changeState(state: MapViewState) {
        _location.postValue(state)
    }

    private suspend fun getConsultant(id: Int): ConsultoraModel {
        val consultant = consultantUseCase.getConsultant(id)
        return mapper.parseConsultant(consultant)
    }

    private fun changeConsultantLocation(consultant: ConsultoraModel, latLng: LatLng) {
        consultant.apply {
            latitud = latLng.latitude
            longitud = latLng.longitude
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            exception.message.orEmpty().let {
                Log.e(this::javaClass.name, it)
                changeState(MapViewState.Error)
            }
        }
    }

}
