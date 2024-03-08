package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.acompaniamiento.CumplimientoConsolidadoEntity
import biz.belcorp.salesforce.core.entities.sql.acompaniamiento.CumplimientoConsolidadoEntity_Table
import biz.belcorp.salesforce.core.utils.guionSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.data.entities.acuerdos.consolidado.CumplimientoModel
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado.CumplimientoCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado.CumplimientoPrecalculado
import com.google.gson.Gson
import com.raizlabs.android.dbflow.kotlinextensions.*

class CumplimientoAcuerdosLocalDataStore {

    fun obtener(llaveUA: LlaveUA): List<CumplimientoPrecalculado> {
        val where = (select from CumplimientoConsolidadoEntity::class
            where (CumplimientoConsolidadoEntity_Table.Region.withTable()
            eq llaveUA.codigoRegion.guionSiNull())
            and (CumplimientoConsolidadoEntity_Table.Zona.withTable()
            eq llaveUA.codigoZona.guionSiNull())
            and (CumplimientoConsolidadoEntity_Table.Seccion.withTable()
            eq llaveUA.codigoSeccion.guionSiNull())
            and (CumplimientoConsolidadoEntity_Table.ConsultoraId.withTable()
            eq (llaveUA.consultoraId ?: -1L)))
        val modelos = where.queryList()
        return modelos.flatMap { parse(it) }
    }

    private fun parse(modelo: CumplimientoConsolidadoEntity): List<CumplimientoPrecalculado> {
        val cumplimientos = Gson().fromJson(modelo.valores, Array<CumplimientoModel>::class.java)
        return cumplimientos.map { detalle ->
            val campania = parsearCampania(detalle)
            val estado = parsearEstado(detalle.cumplimiento)
            CumplimientoPrecalculado(campania, estado)
        }
    }

    private fun parsearCampania(detalle: CumplimientoModel) =
        Campania.construirDummy(codigo = detalle.campania)

    private fun parsearEstado(cumplimiento: Boolean): CumplimientoCampania.Estado {
        return if (cumplimiento) {
            CumplimientoCampania.Estado.CUMPLIDO
        } else {
            CumplimientoCampania.Estado.NO_CUMPLIDO
        }
    }
}
