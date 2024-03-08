package biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera.loader

import android.view.View
import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraModel
import kotlinx.android.synthetic.main.fragment_cabecera_perfil.view.*
import kotlinx.android.synthetic.main.layout_perfil_datos_consultora.view.*

class PosibleConsultoraViewLoader(model: PerfilCabeceraModel) : CabeceraViewLoader(model) {

    init {
        model as PerfilCabeceraModel.PosibleConsultora
    }

    override fun loadRolData(view: View) = with(view) {
        layout_perfil_datos_persona_co?.visible()
        layout_perfil_datos_persona_gz?.gone()
        layout_perfil_datos_persona_gr?.gone()
        val drawable =
            ContextCompat.getDrawable(view.context, R.drawable.circulo_consultora_postulante)
        imgPintTipe?.setImageDrawable(drawable)
        txtTipe?.text = view.context.getString(R.string.rol_posible_consultora)
    }
}
