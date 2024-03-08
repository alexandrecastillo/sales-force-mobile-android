package biz.belcorp.salesforce.modules.calculator.feature.calculator.analitycs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalyticsHelper.TAG_ANALYTICS
import biz.belcorp.salesforce.core.domain.usecases.firebase.FirebaseAnalyticsUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import kotlinx.coroutines.launch

class AnalyticsCalculatorViewModel(
    private val firebaseAnalyticsUseCase: FirebaseAnalyticsUseCase
) : ViewModel() {

    fun sendCalculatorScreen() {
        viewModelScope.launch(handler) {
            io {
                val request = FirebaseAnalyticsUseCase.RequestEvento(TagAnalytics.PANTALLA_CALCULADORA_CALCULA_TU_GANANCIA)
                firebaseAnalyticsUseCase.enviarPantallaCalculadora(request)
            }
        }
    }

    fun sendCalculatorEvent(tagAnalytics: TagAnalytics) {
        viewModelScope.launch(handler) {
            io {
                val request = FirebaseAnalyticsUseCase.RequestEvento(tagAnalytics)
                firebaseAnalyticsUseCase.enviarEventoCalculadora(request)
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            Log.e(TAG_ANALYTICS, message)
        }
    }
}
