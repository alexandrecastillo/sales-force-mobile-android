package biz.belcorp.salesforce.modules.calculator.model

data class ContestDetailVariableModel (val variable: String?,
                                       val nivelBT: List<Int>?) {
    var monto: Int? = null
    var nivelSeleccionado: Int? = null
}
