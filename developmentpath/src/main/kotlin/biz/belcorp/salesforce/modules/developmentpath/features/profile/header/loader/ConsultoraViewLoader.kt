package biz.belcorp.salesforce.modules.developmentpath.features.profile.header.loader

import android.content.res.ColorStateList
import android.view.View
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraModel
import kotlinx.android.synthetic.main.fragment_cabecera_perfil_v2.view.*

class ConsultoraViewLoader(model: PerfilCabeceraModel) : CabeceraViewLoader(model) {

    override val model = model as PerfilCabeceraModel.Consultora

    override fun loadRolData(view: View) {
        pintarTextoSegmento(view)
        printarColorAvatarYSegmento(view)
        pintarInformacionCabecera(view)
    }

    private fun pintarTextoSegmento(view: View) {
        view.txtSegmento?.text = model.segmento ?: ""
    }

    private fun printarColorAvatarYSegmento(view: View) {
        model.segmento?.let {
            if (it.isNotEmpty()) {
                view.segmento?.visibility = View.VISIBLE
                view.imgAvatar?.backgroundTintList =
                    ColorStateList.valueOf(obtenerColorIndicador(view))
            }
        }
        view.imgIndicadorSegmento?.setColorFilter(obtenerColorIndicador(view))
    }

    private fun pintarInformacionCabecera(view: View) {
        txtNombreCabecera?.text = decidirMostrarTextoCabecera() ?: ""
        imgIndicadorCabecera?.imageResource = R.drawable.ic_oval
        imgIndicadorCabecera?.setColorFilter(obtenerColorIndicador(view))
    }

    override fun obtenerColorIndicador(view: View): Int {
        return when (model.tipo) {
            ConsultoraRdd.Tipo.NUEVA -> view.getColor(R.color.rdd_nueva)
            ConsultoraRdd.Tipo.ESTABLECIDA, ConsultoraRdd.Tipo.C3 -> view.getColor(R.color.rdd_establecida)
            else -> view.getColor(R.color.rdd_postulante)
        }
    }
}
