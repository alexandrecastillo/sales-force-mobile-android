package biz.belcorp.salesforce.modules.developmentpath.features.resultadovisitas

data class ListarResultadoVisitasModel(val campaniaNombreLargo: String,
                                       val campaniaNombreCorto: String,
                                       val campaniaAnteriorNombreCorto: String,
                                       val facturacion: Boolean,
                                       var consultoras: List<ConsultoraModel>,
                                       var sugerencias: List<String>,
                                       var filtro: String = "") {

    val cantidadConsultoras: Int get() =  consultoras.size
}
