package biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera.loader

import android.annotation.SuppressLint
import android.view.View
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraModel
import kotlinx.android.synthetic.main.fragment_cabecera_perfil.view.*
import kotlinx.android.synthetic.main.layout_perfil_datos_gerente_region.view.*

class GerenteRegionViewLoader(model: PerfilCabeceraModel) : CabeceraViewLoader(model) {

    override val model = model as PerfilCabeceraModel.GerenteRegion

    @SuppressLint("SetTextI18n")
    override fun loadRolData(view: View) = with(view) {
        layout_perfil_datos_persona_co?.gone()
        layout_perfil_datos_persona_gz?.gone()
        layout_perfil_datos_persona_gr?.visible()
        tv_estado_gr?.text = "GR ${model.estadoProductividad}"
    }
}
