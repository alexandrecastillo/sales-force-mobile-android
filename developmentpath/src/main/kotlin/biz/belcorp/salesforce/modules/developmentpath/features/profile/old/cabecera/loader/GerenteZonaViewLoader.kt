package biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera.loader

import android.annotation.SuppressLint
import android.view.View
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraModel
import kotlinx.android.synthetic.main.fragment_cabecera_perfil.view.*
import kotlinx.android.synthetic.main.layout_perfil_datos_gerente_zona.view.*

class GerenteZonaViewLoader(model: PerfilCabeceraModel) : CabeceraViewLoader(model) {

    override val model = model as PerfilCabeceraModel.GerenteZona

    @SuppressLint("SetTextI18n")
    override fun loadRolData(view: View) = with(view) {
        layout_perfil_datos_persona_co?.gone()
        layout_perfil_datos_persona_gz?.visible()
        layout_perfil_datos_persona_gr?.gone()

        mostrarCamposNueva(view)
        tv_estado_gz?.text = "GZ ${model.estadoProductividad}"
        textGzNueva?.text = model.textoNueva
    }

    private fun mostrarCamposNueva(view: View) {
        if (model.esNueva) {
            view.textGzNueva?.visibility = View.VISIBLE
            view.imageGzNueva?.visibility = View.VISIBLE
        }
    }
}
