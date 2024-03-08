package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents.DocumentoModel

data class NovedadesModel(
    val novedades: List<ListadoNovedadesModel>?,
    val incentivos: List<DocumentoModel>?
)
