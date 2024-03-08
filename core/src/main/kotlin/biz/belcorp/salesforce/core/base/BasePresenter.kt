package biz.belcorp.salesforce.core.base

import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter : BaseCoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    override val handler = CoroutineExceptionHandler { _, exception ->
        GlobalScope.launch(Dispatchers.Main) {
            onExceptionHandler(exception)
        }
    }

    open fun onDestroy() {
        job.cancel()
    }

}
