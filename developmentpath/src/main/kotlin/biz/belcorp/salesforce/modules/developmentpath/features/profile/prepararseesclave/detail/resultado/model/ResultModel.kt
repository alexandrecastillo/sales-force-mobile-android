package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.model

import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica

data class ResultModel(
    val campaign: String,
    val goalReal: ContenedorInfoBasica,
    val results: ContenedorInfoBasica
)