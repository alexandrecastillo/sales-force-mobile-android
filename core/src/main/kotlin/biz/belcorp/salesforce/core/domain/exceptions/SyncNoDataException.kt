package biz.belcorp.salesforce.core.domain.exceptions


class SyncNoDataException(message: String = MESSAGE) : Throwable(message) {

    companion object {

        const val MESSAGE = "No se pudieron obtener los datos."

    }

}
