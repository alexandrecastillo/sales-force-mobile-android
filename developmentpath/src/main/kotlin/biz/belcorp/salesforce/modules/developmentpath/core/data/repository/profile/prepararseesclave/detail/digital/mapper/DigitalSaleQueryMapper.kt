package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.mapper

import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.model.DigitalSaleCoRequestParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.model.DigitalSaleSeRequestParams
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.model.DigitalSaleQueryParams

class DigitalSaleQueryMapper {
    fun mapConsultantParams(queryParams: DigitalSaleQueryParams): DigitalSaleCoRequestParams =
        with(queryParams) {
            return DigitalSaleCoRequestParams(
                country = country,
                campaign = campaigns,
                consultantCode = consultantCode,
                region = uakey.codigoRegion.orEmpty(),
                zone = uakey.codigoZona.orEmpty(),
                section = uakey.codigoSeccion.orEmpty()
            )
        }

    fun mapBusinessPartnerParams(queryParams: DigitalSaleQueryParams): DigitalSaleSeRequestParams =
        with(queryParams) {
            return DigitalSaleSeRequestParams(
                country = country,
                campaign = campaigns,
                region = uakey.codigoRegion.orEmpty().deleteHyphen(),
                zone = uakey.codigoZona.orEmpty().deleteHyphen(),
                section = uakey.codigoSeccion.orEmpty().deleteHyphen()
            )
        }
}
