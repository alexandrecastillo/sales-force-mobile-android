package biz.belcorp.salesforce.modules.consultants.features.maps.mappers

import biz.belcorp.salesforce.modules.consultants.core.domain.entities.inicial
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultoraModel
import biz.belcorp.salesforce.modules.consultants.features.maps.model.MapModel
import com.google.android.gms.maps.model.LatLng

class MapMapper {

    fun map(consultantModel: ConsultoraModel): MapModel = with(consultantModel) {
        return MapModel(
            name = createName(this),
            fullName = createFullName(this),
            position = LatLng(latitud, longitud)
        )
    }

    private fun createName(consultant: ConsultoraModel): String = with(consultant) {
        return primerNombre.inicial() + segundoNombre.inicial()
    }

    private fun createFullName(consultant: ConsultoraModel): String = with(consultant) {
        return primerNombre.inicial() + primerApellido.inicial()
    }


}
