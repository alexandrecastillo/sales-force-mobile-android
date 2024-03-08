package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.detalle

interface ProgresoReconocimientoView {
    fun showComportamientosUltimasSeisCampanias(progresos: List<ProgresoReconocimientoFragment.ProgresoModel>)
    fun pintarCampanias(campanias: List<String>)
}
