package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades

interface NovedadesView {
    fun pintarNovedades(novedades: List<ListadoNovedadesModel>)
    fun pintarNovedadesVacio()
}
