package biz.belcorp.salesforce.modules.developmentpath.core.data.exceptions

class DBDataStoreException(cause: Throwable?) :
    Exception(cause?.message, cause)
