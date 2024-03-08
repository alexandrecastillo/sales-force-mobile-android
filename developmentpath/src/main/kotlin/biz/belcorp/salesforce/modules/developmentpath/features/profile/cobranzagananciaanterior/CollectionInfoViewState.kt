package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzagananciaanterior

import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.CobranzaCampaniaAnteriorModel

sealed class CollectionInfoViewState {

    class Success(
        val dateString: String,
        val campaignName: String,
        val collectionInfo: CobranzaCampaniaAnteriorModel
    ) : CollectionInfoViewState()

    object Failed : CollectionInfoViewState()

}
