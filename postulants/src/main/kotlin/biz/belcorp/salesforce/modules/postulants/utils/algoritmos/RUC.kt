package biz.belcorp.salesforce.modules.postulants.utils.algoritmos

object RUC {

    private const val LENGHT = 11
    private const val PREFIX_NATURAL = "10"
    private const val PREFIX_JURIDICO = "20"

    fun validate(ruc: String): Boolean {
        return with(ruc.trim()) {
            isNotEmpty() && length == LENGHT &&
                (startsWith(PREFIX_NATURAL) || startsWith(PREFIX_JURIDICO))
        }
    }

}
