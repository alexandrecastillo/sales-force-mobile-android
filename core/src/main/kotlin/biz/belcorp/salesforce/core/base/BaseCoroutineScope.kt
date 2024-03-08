package biz.belcorp.salesforce.core.base

import kotlinx.coroutines.*


interface BaseCoroutineScope : CoroutineScope {

    val handler: CoroutineExceptionHandler

    fun onExceptionHandler(exception: Throwable) = Unit

    suspend fun <T> uiThread(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.Main + handler, block)
    }

    fun doAsync(block: suspend CoroutineScope.() -> Unit) {
        launch(handler, block = block)
    }

}
