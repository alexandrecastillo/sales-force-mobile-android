package biz.belcorp.salesforce.modules.postulants.core.domain.exception

class DefaultErrorBundle(override val exception: Exception) : ErrorBundle {
    override val errorMessage: String
        get() = this.exception.message ?: DEFAULT_ERROR_MSG

    companion object {
        private const val DEFAULT_ERROR_MSG = "Unknown error"
    }
}
