package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.view

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.model.MiFocoModel

interface MisFocosView {

    fun permitirAsignacion()

    fun pintarFocos(focos: List<MiFocoModel>): Unit?

}
