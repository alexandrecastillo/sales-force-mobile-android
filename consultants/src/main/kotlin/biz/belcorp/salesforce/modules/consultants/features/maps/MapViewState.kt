package biz.belcorp.salesforce.modules.consultants.features.maps

import biz.belcorp.salesforce.modules.consultants.features.maps.model.MapModel

open class MapViewState {

    class SuccessSave(val model: MapModel) : MapViewState()
    class WithLocation(val model: MapModel) : MapViewState()
    class WithoutLocation(val model: MapModel) : MapViewState()
    class WithoutPermission : MapViewState()
    object Error : MapViewState()

}
