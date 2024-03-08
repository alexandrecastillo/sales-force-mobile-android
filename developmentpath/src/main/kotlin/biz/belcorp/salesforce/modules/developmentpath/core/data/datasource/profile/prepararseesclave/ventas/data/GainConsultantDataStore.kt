package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.data

import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.ConsultantSaleEntity

class GainConsultantDataStore : SaleConsultantDataStore() {

    fun getGainConsultant(
        consultantCode: String,
        campaigns: List<String>
    ): List<ConsultantSaleEntity> {
        return super.getConsultantSales(consultantCode, campaigns)
    }
}
