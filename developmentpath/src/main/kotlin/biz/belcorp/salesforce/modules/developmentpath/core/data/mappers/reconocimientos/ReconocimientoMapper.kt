package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.reconocimientos

import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetalleEntity
import biz.belcorp.salesforce.core.utils.guionSiVacioONull
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosRepository
import com.google.gson.Gson

class ReconocimientoMapper {

    private val gson get() = Gson()

    fun map(request: ReconocimientosRepository.GuardarRequest): ComportamientoDetalleEntity {
        return ComportamientoDetalleEntity(
            id = Constant.MENOS_UNO.toLong(),
            campania = request.codigoCampania,
            region = request.llaveUA.codigoRegion.guionSiVacioONull(),
            zona = request.llaveUA.codigoZona.guionSiVacioONull(),
            seccion = request.llaveUA.codigoSeccion.guionSiVacioONull(),
            consultoraID = request.llaveUA.consultoraId ?: Constant.MENOS_UNO.toLong(),
            consultoraRecomiendaID = request.idPersonaSesion?.toInt(),
            comportamiento = gson.toJson(request.idsReconocidos),
            enviado = false,
            fechaModificacion = null
        )
    }

}
