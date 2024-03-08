package biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.model

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoAGrabar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoConMadre
import biz.belcorp.salesforce.modules.developmentpath.utils.iniciales

class ReconocimientoDetalleMapper {

    fun parse(reconocimiento: ReconocimientoConMadre): ReconocimientoModel {
        return ReconocimientoModel(
            nombreReconocida = reconocimiento.madreReconocida.nombre,
            valoracion = reconocimiento.reconocimiento.valoracion,
            comentarios = reconocimiento.reconocimiento.comentarios,
            estaPendiente = reconocimiento.reconocimiento.pendienteReconocimiento,
            iniciales = reconocimiento.madreReconocida.nombre.iniciales(),
            codRol = reconocimiento.reconocimiento.rolReconocida.codigoRol,
            ua = reconocimiento.reconocimiento.uACodigo
        )
    }

    fun parse(
        idReconocimiento: Long,
        califcacion: Int,
        comentarios: String
    ): ReconocimientoAGrabar {
        return ReconocimientoAGrabar(
            id = idReconocimiento,
            calificacion = califcacion,
            comentarios = comentarios
        )
    }
}
