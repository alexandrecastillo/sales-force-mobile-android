package biz.belcorp.salesforce.modules.developmentpath.core.domain.exceptions

class DefaultErrorBundle(override val exception: Exception) : ErrorBundle {
    override val errorMessage: String
        get() = this.exception.message ?: DEFAULT_ERROR_MSG

    companion object {
        private const val DEFAULT_ERROR_MSG = "Unknown error"
    }
}
