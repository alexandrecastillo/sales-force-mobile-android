package biz.belcorp.salesforce.base.features.home.shortcuts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetShortcutOptionsUseCase
import biz.belcorp.salesforce.base.utils.ecriptation.JwtEncryption
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.options.Option
import biz.belcorp.salesforce.core.domain.usecases.browser.GetWebUrlUseCase
import biz.belcorp.salesforce.core.domain.usecases.browser.WebTopic
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import kotlinx.coroutines.launch
import org.json.JSONObject

internal class ShortcutsViewModel(
    private val useCase: GetShortcutOptionsUseCase,
    private val mapper: ShortcutMapper,
    private val getWebUrlUseCase: GetWebUrlUseCase
) : ViewModel() {

    val viewState: LiveData<ShortcutsViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<ShortcutsViewState>()

    fun getShortcuts() {
        viewModelScope.launch(handler) {
            io {
                var options = useCase.getOptions()
                if (options.isEmpty()) {
                    options = getShortcutOption()
                    Log.d("Option shortcut view model", "botones custom")
                }else{
                    Log.d("Option shortcut view model", "botones server")
                }

                val optionsModel = mapper.map(options)
                _viewState.postValue(ShortcutsViewState.Success(optionsModel))
            }
        }
    }

    fun getUrlByTopic(webTopic: WebTopic?) {
        viewModelScope.launch(handler) {
            io {
                val url = getWebUrlUseCase.getUrl(requireNotNull(webTopic))

                when (webTopic) {
                    WebTopic.MATERIALES_REDES_SOCIALES -> {
                        val jObject = JSONObject()
                        jObject.put(KEY_COUNTRY, getWebUrlUseCase.session.countryIso)
                        jObject.put(KEY_PAGE, Constant.VALUE_SOCIAL_NETWORK_MATERIAL)
                        jObject.put(KEY_USERCODE, getWebUrlUseCase.session.codigoConsultora)
                        jObject.put(KEY_CLEARSESSION, true)
                        jObject.put(KEY_ISMOBILEAPP, true)
                        jObject.put(KEY_ISNOTEMBEBED, true)

                        val token = JwtEncryption.newInstance()?.let {
                            it.encrypt(Constant.SECRET_LOGIN_EXTERNO, jObject.toString())
                        } ?: run {
                            ""
                        }

                        val urlMateriales = "$url$token"
                        _viewState.postValue(ShortcutsViewState.LoadExternalWebPage(urlMateriales))
                    }

                    else -> _viewState.postValue(ShortcutsViewState.LoadExternalWebPage(url))
                }
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

    private fun getShortcutOption(): ArrayList<Option> =
        when (UserProperties.session?.rol) {
            Rol.GERENTE_ZONA -> {
                ShortcutHelper.getShortcutGZ(UserProperties.session?.countryIso)
            }
            Rol.GERENTE_REGION -> {
                ShortcutHelper.getShortcutGR()
            }
            else -> {
                ShortcutHelper.getShortcutSE()
            }
        }


    companion object {
        private const val KEY_COUNTRY = "Pais"
        private const val KEY_PAGE = "Pagina"
        private const val KEY_USERCODE = "CodigoUsuario"
        private const val KEY_CLEARSESSION = "LimpiarSesion"
        private const val KEY_ISMOBILEAPP = "EsAppMobile"
        private const val KEY_ISNOTEMBEBED = "NoEsEmbebido"
    }

}
