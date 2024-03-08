package biz.belcorp.salesforce.modules.developmentpath.common.view

import biz.belcorp.salesforce.modules.developmentpath.core.domain.foco.FocoModel


interface FocoView {

    fun mostrarFocos(focos: List<FocoModel>)
    fun mostrarBanner()
    fun ocultarBanner()
}
