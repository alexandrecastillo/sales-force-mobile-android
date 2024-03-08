package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.Documento
import io.reactivex.Single

interface DocumentoRepository {
    fun obtenerDocumentos(params: Params): Single<List<Documento>>
}
