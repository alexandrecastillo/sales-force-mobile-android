package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.model

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.R

class RegionCoberturadaModel(val gerenteRegion: GerenteRegionModel,
                             llaveUA: LlaveUA,
                             codigoCampania: String,
                             visitasRealizadas: Int,
                             visitasPlanificadas: Int,
                             planId: Long,
                             planValido: Boolean) :
        RegionModel(
                llaveUA = llaveUA,
                codigoCampania = codigoCampania,
                visitasRealizadas = visitasRealizadas,
                visitasPlanificadas = visitasPlanificadas,
                planId = planId,
                planValido = planValido) {

    class GerenteRegionModel(val nombre: String,
                             val estadoProductividad: String)

    override fun construirTitulo(context: Context): String {
        return context.getString(R.string.avance_region_titulo_coberturada,
                                 llaveUA.codigoRegion,
                                 gerenteRegion.nombre)
    }

    override fun construirColorTitulo(context: Context): Int {
        return ContextCompat.getColor(context, R.color.meta_avance_campania)
    }

    override val mostrarProductividad: Int
        get() = View.VISIBLE

    override val productividad: String
        get() = gerenteRegion.estadoProductividad
}
