package biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.readString
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.cloud.dto.NewCycleQuery
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiData
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.BonusInfo
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleGridContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicatorContainer
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

class NewCycleMockDataHelper {

    private fun getNewCycle(): NewCycleQuery.Data {
        val jsonString = readString("core/data/new-cycle-response.json")
        return JsonEncodedDefault.decodeFromString(NewCycleQuery.Data.serializer(), jsonString)
    }

    fun fetchNewCycle(): NewCycleIndicatorContainer {
        return NewCycleIndicatorContainer(listIndicators =  parse(getNewCycle()),
            currencySymbol = EMPTY_STRING,
            isBilling = true,
            role = Rol.GERENTE_ZONA,
            bonusInfo = BonusInfo(Constant.EMPTY_DOUBLE, Constant.EMPTY_DOUBLE),
            isThirdPerson = true
        )
    }

    fun fetchNewCycleGrid(): NewCycleGridContainer {
        return NewCycleGridContainer(
            childIndicatorList = emptyList(),
            isBilling = true,
            role = Rol.GERENTE_REGION,
            isParent = false
        )
    }

    private fun parse(model: NewCycleQuery.Data): KpiData<NewCycleIndicator> {
        val list = model.kpiRetention.map { getNewCycleIndicator(it) }
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, "202005", "202004")
    }

    private fun getNewCycleIndicator(newCycleResponse: NewCycleQuery.Data.KpiRetention): NewCycleIndicator {
        return NewCycleIndicator(
            newCycleResponse.campaign,
            newCycleResponse.region.orEmpty(),
            newCycleResponse.section.orEmpty(),
            newCycleResponse.zone.orEmpty(),
            newCycleResponse.profile,
            newCycleResponse.highValueOrders.i6d6.zeroIfNull(),
            newCycleResponse.highValueOrders.i5d5.zeroIfNull(),
            newCycleResponse.highValueOrders.i4d4.zeroIfNull(),
            newCycleResponse.highValueOrders.i3d3.zeroIfNull(),
            newCycleResponse.highValueOrders.i2d2.zeroIfNull(),
            newCycleResponse.highValueOrders.i1d1.zeroIfNull(),
            newCycleResponse.highValueOrders.retentionPercentage,
            newCycleResponse.lowValueOrders.i6d6.zeroIfNull(),
            newCycleResponse.lowValueOrders.i5d5.zeroIfNull(),
            newCycleResponse.lowValueOrders.i4d4.zeroIfNull(),
            newCycleResponse.lowValueOrders.i3d3.zeroIfNull(),
            newCycleResponse.lowValueOrders.i2d2.zeroIfNull(),
            newCycleResponse.lowValueOrders.i1d1.zeroIfNull(),
            newCycleResponse.lowValueOrders.retentionPercentage
        )
    }
}

