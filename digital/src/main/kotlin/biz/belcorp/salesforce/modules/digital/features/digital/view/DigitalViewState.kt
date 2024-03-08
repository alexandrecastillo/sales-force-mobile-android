package biz.belcorp.salesforce.modules.digital.features.digital.view

import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType

sealed class DigitalViewState {
    class Success(
        val role: Rol,
        val uaKey: LlaveUA,
        val uas: List<SelectorModel>
    ) : DigitalViewState()

    class ShowInfo(
        val uaKey: LlaveUA,
        @DigitalFilterType val type: Int,
        val asParent: Boolean,
        val detailTitle: String,
        val childDescription: String = Constant.EMPTY_STRING,
        var updateHeader: Boolean = false
    ): DigitalViewState()

    class ShowChildInfo(
        val uaKey: LlaveUA,
        val description: String,
        @DigitalFilterType val type: Int
    ) : DigitalViewState()

    class ShowParentInfo(
        val uaKey: LlaveUA,
        @DigitalFilterType val type: Int
    ) : DigitalViewState()

    object Failure : DigitalViewState()
}
