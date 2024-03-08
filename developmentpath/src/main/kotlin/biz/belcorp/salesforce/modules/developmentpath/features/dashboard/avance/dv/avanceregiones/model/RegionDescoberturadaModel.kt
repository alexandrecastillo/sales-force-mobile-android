package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.model

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.R

class RegionDescoberturadaModel(llaveUA: LlaveUA,
                                codigoCampania: String,
                                visitasRealizadas: Int,
                                visitasPlanificadas: Int,
                                planId: Long,
                                planValido: Boolean) :
        RegionModel(llaveUA = llaveUA,
                    codigoCampania = codigoCampania,
                    visitasRealizadas = visitasRealizadas,
                    visitasPlanificadas = visitasPlanificadas,
                    planId = planId,
                    planValido = planValido) {

    override fun construirTitulo(context: Context): String {
        return context.getString(R.string.avance_region_titulo_descoberturada, llaveUA.codigoRegion)
    }

    override fun construirColorTitulo(context: Context): Int {
        return ContextCompat.getColor(context, R.color.estado_negativo)
    }

    override val mostrarProductividad: Int
        get() = View.GONE

    override val productividad: String
        get() = Constant.EMPTY_STRING

}
