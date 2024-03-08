package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.puntaje

import biz.belcorp.salesforce.core.entities.sql.path.PuntajeReconocimientoEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.PuntajeReconocimiento

class PuntajeReconocimientoMapper {

    fun puntajeParser(model: PuntajeReconocimientoEntity?): PuntajeReconocimiento {
        return with(model) {
            biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.PuntajeReconocimiento(
                campania = this?.campania,
                region = this?.region,
                zona = this?.zona,
                seccion = this?.seccion,
                rol = this?.rol,
                puntaje = this?.puntaje
            )
        }
    }


}
