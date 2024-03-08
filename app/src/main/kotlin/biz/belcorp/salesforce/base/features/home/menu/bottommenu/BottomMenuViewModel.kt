package biz.belcorp.salesforce.base.features.home.menu.bottommenu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetMenuOptionsUseCase
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.CALCULATOR
import biz.belcorp.salesforce.base.features.home.menu.mappers.MenuOptionMapper
import biz.belcorp.salesforce.base.features.home.shortcuts.ShortcutHelper
import biz.belcorp.salesforce.core.domain.entities.options.Option
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.io
import kotlinx.coroutines.launch


class BottomMenuViewModel(
    private val useCase: GetMenuOptionsUseCase,
    private val mapper: MenuOptionMapper
) : ViewModel() {

    val viewState: LiveData<BottomMenuViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<BottomMenuViewState>()

    fun getOptionsMenu() {
        viewModelScope.launch {
            io {
                var options = useCase.getOptions()
                if (options.isEmpty()) {
                    options = getShortcutOption()
                    Log.d("Option Bottom view model", "botones custom")
                }
                Log.d("Options Bottom view model", options.size.toString() + " botones del server")
                val optionsModel = mapper.map(options.filter { it.code.toInt() != CALCULATOR })
                val populateState = BottomMenuViewState.Populate(optionsModel)
                _viewState.postValue(populateState)
            }
        }
    }

    private fun getShortcutOption(): ArrayList<Option> =
        if (UserProperties.session?.rol == Rol.GERENTE_REGION) {
            ShortcutHelper.getOptionsGRMenuBottom()
        } else {
            ShortcutHelper.getOptionsMenuBottom()
        }

}
