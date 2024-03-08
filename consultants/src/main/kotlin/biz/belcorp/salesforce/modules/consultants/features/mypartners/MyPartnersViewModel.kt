package biz.belcorp.salesforce.modules.consultants.features.mypartners

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerEntity
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.mypartners.MyPartnersStoreUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.mypartners.MyPartnersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPartnersViewModel(
    private val partnerUseCase: MyPartnersUseCase,
    private val partnerStoreUseCase: MyPartnersStoreUseCase,
    private val configurationUseCase: ConfigurationUseCase
) : ViewModel() {


    val viewState: LiveData<MyPartnersViewState>
        get() = viewModel

    private val viewModel = MutableLiveData<MyPartnersViewState>()

    private val _originalData = MutableLiveData<List<MyPartnerEntity>>(emptyList())
    val originalData: LiveData<List<MyPartnerEntity>> = _originalData

    private val _phoneCode = MutableLiveData<String>()
    val phoneCode: LiveData<String> = _phoneCode

    fun getMyPartnerInformation() {
        viewModelScope.launch(handler) {
            io {
                partnerUseCase.syncMyPartners()
                val partners = partnerStoreUseCase.getMyPartners()
                _originalData.postValue(partners)
                viewModel.postValue(MyPartnersViewState.SuccessPartners(partners))
            }
        }
    }

    private fun fetchFromDataBase() {
        val partners = partnerStoreUseCase.getMyPartners()
        _originalData.postValue(partners)
        viewModel.postValue(MyPartnersViewState.SuccessPartners(partners))
    }

    fun getConfiguration() {
        viewModelScope.launch(Dispatchers.IO) {
            val config = configurationUseCase.getConfiguration()
            _phoneCode.postValue(config.phoneCode)
        }
    }


    private val handler = CoroutineExceptionHandler { _, _ ->
        ui {
            fetchFromDataBase()
        }
    }


    sealed class MyPartnersViewState {
        class SuccessPartners(val data: List<MyPartnerEntity>) : MyPartnersViewState()
        class Failure(val message: String) : MyPartnersViewState()
    }
}
