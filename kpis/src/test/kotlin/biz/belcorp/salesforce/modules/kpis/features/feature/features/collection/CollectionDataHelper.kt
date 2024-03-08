package biz.belcorp.salesforce.modules.kpis.features.feature.features.collection

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionConsolidated
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock
import io.mockk.every
import io.mockk.mockk

object CollectionDataHelper {

    val collectionContainerMock by lazy {
        mockk<CollectionContainer> {
            every { role } returns Rol.GERENTE_ZONA
            every { currencySymbol } returns NewCycleIndicatorMock.configuration.currencySymbol
            every { campaign } returns NewCycleIndicatorMock.campaignMock.codigo
            every { collectionList } returns mockk {
                every { currentData } returns mockk {
                    every { region } returns Constant.EMPTY_STRING
                    every { zone } returns Constant.EMPTY_STRING
                    every { section } returns Constant.EMPTY_STRING
                    every { percentage } returns Constant.ZERO_DECIMAL
                    every { invoicedSale } returns Constant.ZERO_DECIMAL
                    every { amountCollected } returns Constant.ZERO_DECIMAL
                    every { ordersRange } returns listOf(
                        mockk {
                            every { position } returns Constant.NUMBER_ZERO
                            every { range } returns Constant.EMPTY_STRING
                            every { collected } returns Constant.NUMBER_ONE
                            every { total } returns Constant.NUMBER_ONE
                        }
                    )
                }
                every { previousData } returns mockk {
                    every { region } returns Constant.EMPTY_STRING
                    every { zone } returns Constant.EMPTY_STRING
                    every { section } returns Constant.EMPTY_STRING
                    every { percentage } returns Constant.ZERO_DECIMAL
                    every { invoicedSale } returns Constant.ZERO_DECIMAL
                    every { amountCollected } returns Constant.ZERO_DECIMAL
                    every { ordersRange } returns listOf(
                        mockk {
                            every { position } returns Constant.NUMBER_ZERO
                            every { range } returns Constant.EMPTY_STRING
                            every { collected } returns Constant.NUMBER_ONE
                            every { total } returns Constant.NUMBER_ONE
                        }
                    )
                }
            }
            every { isThirdPerson } returns false
        }
    }

    val collectionConsolidatedMock by lazy {
        mockk<CollectionConsolidated> {
            every { pairData } returns listOf(Pair(uaInfo, collectionItem))
            every { uaList } returns listOf(uaInfo)
            every { role } returns Rol.GERENTE_ZONA
            every { currencySymbol } returns NewCycleIndicatorMock.configuration.currencySymbol
            every { campaign } returns NewCycleIndicatorMock.campaignMock.codigo
            every { collectionList } returns listOf(collectionItem)
            every { isThirdPerson } returns false
        }
    }

    private val uaInfo by lazy {
        mockk<UaInfo> {
            every { uaKey } returns LlaveUA()
        }
    }

    private val collectionItem by lazy {
        mockk<CollectionIndicator> {
            every { region } returns Constant.EMPTY_STRING
            every { zone } returns Constant.EMPTY_STRING
            every { section } returns Constant.EMPTY_STRING
            every { percentage } returns Constant.ZERO_DECIMAL
            every { invoicedSale } returns Constant.ZERO_DECIMAL
            every { amountCollected } returns Constant.ZERO_DECIMAL
            every { ordersRange } returns listOf(
                mockk {
                    every { position } returns Constant.NUMBER_ZERO
                    every { range } returns Constant.EMPTY_STRING
                    every { collected } returns Constant.NUMBER_ONE
                    every { total } returns Constant.NUMBER_ONE
                }
            )
        }
    }

}
