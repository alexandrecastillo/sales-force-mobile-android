package biz.belcorp.salesforce.core.domain.exceptions


class SyncInsertDataException(message: String = MESSAGE) : Throwable(message) {

    companion object {

        const val MESSAGE = "No se pudieron grabar los datos."

    }

}
