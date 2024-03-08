package biz.belcorp.salesforce.modules.developmentpath.features.profile.header.loader

import android.content.res.ColorStateList
import android.view.View
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.getFont
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraModel
import biz.belcorp.salesforce.modules.developmentpath.utils.establecerDistintasFuentes
import kotlinx.android.synthetic.main.fragment_cabecera_perfil_v2.view.*
import biz.belcorp.salesforce.base.R as BaseR

class SociaViewLoader(model: PerfilCabeceraModel) : CabeceraViewLoader(model) {

    companion object {
        private const val GUION = "-"
        private const val SEPARADOR = "*"
    }

    override val model = model as PerfilCabeceraModel.SociaEmpresaria

    override fun loadRolData(view: View) {
        pintarTextoSegmento(view)
        pintarInformacionCabecera(view)
    }

    override fun obtenerColorIndicador(view: View): Int {
        val color = when (model.nivel) {
            SociaEmpresariaRdd.Nivel.BRONCE,
            SociaEmpresariaRdd.Nivel.PRE_BRONCE -> BaseR.color.bronce
            SociaEmpresariaRdd.Nivel.ORO -> BaseR.color.oro
            SociaEmpresariaRdd.Nivel.PLATA -> BaseR.color.plata
            SociaEmpresariaRdd.Nivel.PLATINUM -> BaseR.color.platinum
            SociaEmpresariaRdd.Nivel.DIAMANTE -> BaseR.color.diamante
            else -> BaseR.color.colorPrimary
        }
        return view.getColor(color)
    }

    private fun pintarTextoSegmento(view: View) {
        val segmento =
            model.nivelProductividad?.plus(" $GUION $SEPARADOR${obtenerTextoSociaExitosa(view)}")
        segmento?.let {
            if (it.isNotEmpty()) {
                view.segmento.visibility = View.VISIBLE
                view.imgAvatar.backgroundTintList =
                    ColorStateList.valueOf(obtenerColorIndicador(view))
            }
        }

        val fuente = view.getFont(BaseR.font.lato_regular)
        val colorPrincipal = view.getColor(BaseR.color.gray_5)
        val colorSecundario = obtenerColorTextoSociaExitosa(view)

        view.txtSegmento?.text = segmento?.establecerDistintasFuentes(
            SEPARADOR,
            fuente,
            fuente,
            colorPrincipal,
            colorSecundario
        )
        view.imgIndicadorSegmento?.setColorFilter(obtenerColorIndicador(view))
    }

    private fun pintarInformacionCabecera(view: View) {
        txtNombreCabecera?.text = decidirMostrarTextoCabecera() ?: ""
        imgIndicadorCabecera?.imageResource = R.drawable.ic_oval
        imgIndicadorCabecera?.setColorFilter(obtenerColorIndicador(view))
    }

    private fun obtenerTextoSociaExitosa(view: View): String {
        val textoSociaExitosa =
            if (model.exitosa) R.string.socia_exitosa else R.string.socia_no_exitosa
        return view.context.getString(textoSociaExitosa)
    }

    private fun obtenerColorTextoSociaExitosa(view: View): Int {
        val color = if (model.exitosa) BaseR.color.positivo else BaseR.color.negativo
        return view.getColor(color)
    }
}
