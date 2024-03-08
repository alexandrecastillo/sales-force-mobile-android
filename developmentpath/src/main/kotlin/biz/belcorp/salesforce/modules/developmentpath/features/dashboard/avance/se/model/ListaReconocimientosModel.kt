package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.se.model

class ListaReconocimientosModel(val nombreReconocida: String,
                                val codigoRolReconocida: String,
                                val codigoUnidadAdministrativa: String,
                                val reconocimientos: List<ReconocimientoModel>) {

    val poseeReconocimientos get() = reconocimientos.isNotEmpty()
}
