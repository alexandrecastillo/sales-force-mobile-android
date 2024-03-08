package biz.belcorp.salesforce.core.domain.exceptions

class NoSaleForceStatusDataException : Exception(MESSAGE) {
    companion object{
        const val MESSAGE = "No se pudo obtener los datos de Sale Force Status del usuario"

    }
}
