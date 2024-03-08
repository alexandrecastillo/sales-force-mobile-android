package biz.belcorp.salesforce.modules.developmentpath.features.manager

import android.util.Log
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.kinesis.KinesisTag
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.kinesis.KinesisManagerUseCase

class KinesisManagerPresenter(private val kinesisManagerUseCase: KinesisManagerUseCase) {

    fun enviarPantalla(kinesisTag: KinesisTag) {
        val request = KinesisManagerUseCase.RequestKinesis(kinesisTag, KinesisObservable())
        kinesisManagerUseCase.ejecutarEnvio(request)
    }

    class KinesisObservable : BaseSingleObserver<String>() {

        override fun onError(e: Throwable) {
            Logger.loge("Kinesis", e.localizedMessage, e)
        }

        override fun onSuccess(t: String) {
            super.onSuccess(t)
            Log.d("TAG_KINESIS", t)
        }
    }
}
