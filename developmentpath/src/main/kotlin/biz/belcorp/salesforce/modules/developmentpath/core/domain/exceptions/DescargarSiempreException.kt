package biz.belcorp.salesforce.modules.developmentpath.core.domain.exceptions

class DescargarSiempreException(cause: Throwable?) :
        Exception(cause?.message, cause)