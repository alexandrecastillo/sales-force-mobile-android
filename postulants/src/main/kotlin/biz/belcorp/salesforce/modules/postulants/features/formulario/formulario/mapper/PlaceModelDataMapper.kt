package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.Place
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.entities.PlaceModel

class PlaceModelDataMapper {

    fun map(places: List<Place>): List<PlaceModel> {

        val modelList: MutableList<PlaceModel> = mutableListOf()
        places.forEach {
            modelList.add(PlaceModel(it.id, it.description))
        }
        return modelList
    }

}
