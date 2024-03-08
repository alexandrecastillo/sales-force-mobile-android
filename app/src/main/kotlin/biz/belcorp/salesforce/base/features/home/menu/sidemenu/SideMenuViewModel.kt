package biz.belcorp.salesforce.base.features.home.menu.sidemenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetMenuSubOptionsUseCase
import biz.belcorp.salesforce.base.features.home.menu.mappers.MenuOptionMapper
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.entities.terms.LinkSE
import biz.belcorp.salesforce.core.domain.usecases.auth.LogoutUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import kotlinx.coroutines.launch

class SideMenuViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val termUseCase: TermConditionsUseCase,
    private val useCase: GetMenuSubOptionsUseCase,
    private val mapper: MenuOptionMapper
) : ViewModel() {

    val viewState: LiveData<SideMenuViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<SideMenuViewState>()

    fun getOptionsMenu(parentCode: Int) {
        viewModelScope.launch(handler) {
            io {
                val options = useCase.getOptions(parentCode)
                val optionsModel = mapper.map(options, parentCode)
                val populateState = SideMenuViewState.Populate(optionsModel)
                _viewState.postValue(populateState)
            }
        }
    }

    fun logout() {
        viewModelScope.launch(handler) {
            io {
                logoutUseCase.logout()
                _viewState.postValue(SideMenuViewState.LogoutSuccess)
            }
        }
    }

    fun checkSELinkStatus() {
        viewModelScope.launch(handler) {
            io {
                val isApproved = termUseCase.isTermApproved(ApproveTermsParams.LINK_SE)
                val isBlocked = termUseCase.isTermBlocked(ApproveTermsParams.LINK_SE)
                _viewState.postValue(
                    SideMenuViewState.TermValidation(
                        getSideMenuState(isApproved, isBlocked)
                    )
                )
            }
        }
    }

    private fun getSideMenuState(isApproved: Boolean, isBlocked: Boolean): LinkSE {
        return if (!isApproved) {
            LinkSE.LinkSEForApprove
        } else {
            checkBlock(isBlocked)
        }
    }

    private fun checkBlock(isBlocked: Boolean): LinkSE {
        return if (isBlocked)
            LinkSE.LinkSEBlocked
        else
            LinkSE.LinkSEApproved
    }


    fun shareLinkUnete() {
        viewModelScope.launch(handler) {
            io {
                val linkUnete = termUseCase.getLinkUnete()
                _viewState.postValue(SideMenuViewState.LinkUneteSuccess(linkUnete))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value = SideMenuViewState.Failed(message)
        }
    }


}
