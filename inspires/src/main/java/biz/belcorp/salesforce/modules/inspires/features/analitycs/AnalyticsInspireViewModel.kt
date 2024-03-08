package biz.belcorp.salesforce.modules.inspires.features.analitycs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalyticsHelper.TAG_ANALYTICS
import biz.belcorp.salesforce.core.domain.usecases.firebase.FirebaseAnalyticsUseCase
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.Inspira
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class AnalyticsInspireViewModel(
    private val firebaseAnalyticsUseCase: FirebaseAnalyticsUseCase
) : ViewModel() {

    fun sendInspireScreen(tagAnalytics: TagAnalytics) {
        viewModelScope.launch(handler) {
            io {
                val request = FirebaseAnalyticsUseCase.RequestEvento(tagAnalytics)
                firebaseAnalyticsUseCase.enviarPantallaInspira(request)
            }
        }
    }

    fun sendInspireEvent(tagAnalytics: TagAnalytics, inspira: Inspira) {
        viewModelScope.launch(handler) {
            io {
                val request = FirebaseAnalyticsUseCase.RequestEvento(tagAnalytics)
                firebaseAnalyticsUseCase.enviarEventoInspira(request, inspira.label, inspira.screenName)
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
