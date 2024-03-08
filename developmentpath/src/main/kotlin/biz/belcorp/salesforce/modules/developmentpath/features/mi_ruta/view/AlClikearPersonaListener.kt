package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.card.MiRutaCardModel

interface AlClikearPersonaListener {

    fun alClickearMenu(card: MiRutaCardModel)

    fun alClickearPlanificar(card: MiRutaCardModel)

    fun alClickearGeorefencia(card: MiRutaCardModel)

    fun alClickearCard(card: MiRutaCardModel)
}
