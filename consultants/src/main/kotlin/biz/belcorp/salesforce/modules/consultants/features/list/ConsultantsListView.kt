package biz.belcorp.salesforce.modules.consultants.features.list

import android.text.SpannableString
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultoraModel

interface ConsultantsListView {
    fun setCurrencySymbol(currencySymbol: String)
    fun showConsultantList(consultants: List<ConsultoraModel>)
    fun setHightlightName(name: String?)
    fun activeFiltroBusqueda()
    fun setBeautyConsultantTypeView(consultantTypeItem: Int)
    fun showNonDataAvailable()
    fun hideNonDataAvailable()
    fun showBeautyConsultantAmountByConstancyInfo(info: SpannableString)

    fun hideNonGrownConsultantDataAvailable()
    fun showNonGrownConsultantDataAvailable()

    fun showPossibleChangeConsultantsTitle()
    fun hidePossibleChangeConsultantsTitle()
}
