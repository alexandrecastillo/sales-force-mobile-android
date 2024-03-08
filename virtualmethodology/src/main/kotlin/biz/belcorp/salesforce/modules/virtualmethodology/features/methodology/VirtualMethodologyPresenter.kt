package biz.belcorp.salesforce.modules.virtualmethodology.features.methodology

import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.usecases.GetGroupSegUseCase
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.mappers.GroupSegMapper

class VirtualMethodologyPresenter(
    private val view: MethodologyView,
    private val getGroupSegUseCase: GetGroupSegUseCase,
    private val groupSegMapper: GroupSegMapper
) : BasePresenter() {

    fun listGroupsSeg() {
        doAsync {
            val result = getGroupSegUseCase.get()
            val list = groupSegMapper.map(result)
            uiThread {
                view.showGroupsSeg(list)
            }
        }
    }
}
