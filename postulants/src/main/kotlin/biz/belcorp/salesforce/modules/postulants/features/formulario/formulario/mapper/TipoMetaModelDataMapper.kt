package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.tipo.TipoMeta
import biz.belcorp.salesforce.modules.postulants.features.entities.TipoMetaModel

class TipoMetaModelDataMapper {

    fun parse(tipoMetas: List<TipoMeta>) = tipoMetas.map { parse(it) }

    fun parse(pojo: TipoMeta) = TipoMetaModel().parse(pojo)

}
