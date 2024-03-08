package biz.belcorp.salesforce.modules.developmentpath.core.domain.exceptions

interface ErrorBundle {
    val exception: Exception
    val errorMessage: String
}
