package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model

data class CalculadoraDetalleConcursoVariableSociaModel(val variable: String?,
                                                        val nivelBT: List<Int>?) {
    var monto: Int? = null
    var nivelSeleccionado: Int? = null
}
