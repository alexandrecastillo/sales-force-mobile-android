package biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera.loader

import android.view.View
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraModel
import kotlinx.android.synthetic.main.fragment_cabecera_perfil.view.*


abstract class CabeceraViewLoader(open val model: PerfilCabeceraModel) {

    fun load(view: View) {
        loadBasicData(view)
        loadRolData(view)
    }

    private fun loadBasicData(view: View) = with(view) {
        if (!model.nombres.isNullOrBlank()) {
            txtnameConsultant?.text = model.nombres
        }
        if (!model.cumpleanios.isNullOrBlank()) {
            txtCumpl?.text = model.cumpleanios
        }
        if (!model.telefono.isNullOrBlank()) {
            txtPhone?.text = model.telefono
        }
        validarEmail(view)
    }

    private fun validarEmail(view: View) {
        if (!model.email.isNullOrEmpty()) {
            view.txtEmail?.text = model.email
            view.image_email?.setImageResource(R.drawable.ic_email_blue)
        }
    }

    abstract fun loadRolData(view: View)
}
