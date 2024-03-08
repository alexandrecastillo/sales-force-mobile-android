package biz.belcorp.salesforce.core.features.uainfo

import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.utils.CoreTextResolver
import biz.belcorp.salesforce.core.utils.SingleMapper

class UaInfoMapper(private val textResolver: CoreTextResolver) :
    SingleMapper<UaInfo, UaInfoModel>() {

    override fun map(value: UaInfo): UaInfoModel = with(value) {
        return UaInfoModel(
            code = code,
            person = person,
            uaKey = uaKey,
            role = role,
            isCovered = isCovered,
            campaign = campaign,
            isThirdPerson = isThirdPerson
        )
    }

    fun mapToSelector(models: List<UaInfo>): List<SelectorModel> {
        return models.map { map(it) }.map { map(it) }
    }

    fun map(model: UaInfoModel): SelectorModel {
        return SelectorModel(
            key = model.code.toString(),
            label = model.labelText
        ).apply {
            this.model = model
        }
    }

    fun map(uaName: String, role: Rol): String {
        return textResolver.formatUaChildText(uaName, role = role)
    }
}
