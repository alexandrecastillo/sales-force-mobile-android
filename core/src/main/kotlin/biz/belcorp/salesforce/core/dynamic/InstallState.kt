package biz.belcorp.salesforce.core.dynamic

sealed class InstallState {

    data class Downloading(val progress: Long, val total: Long) : InstallState()

    class Success(val id: Int) : InstallState()

    object Installed : InstallState()

    class Retry(val message: String) : InstallState()

    class Failed(val message: String) : InstallState()

}
