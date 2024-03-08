package biz.belcorp.salesforce.modules.postulants.core.data.exceptions

class DBDataStoreException(cause: Throwable?) :
        Exception(cause?.message, cause)
