package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.acuerdos

import biz.belcorp.salesforce.core.entities.sql.acuerdos.CampaniaAcuerdosJoinned
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toDate
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.AcuerdosCampania

class AcuerdoEntityDataMapper {

    fun parsearAcuerdoCampania(model: CampaniaAcuerdosJoinned) =
        AcuerdosCampania(
            id = model.idLocal,
            campania = model.campania,
            zona = model.zona,
            nombreCorto = model.nombreCorto,
            contenido = model.contenido,
            fecha = requireNotNull(model.fecha.toDate())
        )

}
