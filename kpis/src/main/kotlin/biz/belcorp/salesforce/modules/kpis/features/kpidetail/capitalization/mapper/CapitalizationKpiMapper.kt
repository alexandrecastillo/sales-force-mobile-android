package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.mapper

import biz.belcorp.salesforce.core.utils.formatWithThousands
import biz.belcorp.salesforce.core.utils.takeLastTwo
import biz.belcorp.salesforce.core.utils.toPercentageFormat
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.CapitalizationTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model.CapitalizationKpiOnSalesModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model.PossiblesPotentialKpiModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.ValueAttributes
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.DefaultGridModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.LeftAlignedGridModel
import kotlin.math.floor

class CapitalizationKpiMapper(val textResolver: CapitalizationTextResolver) {

    fun map(isParent: Boolean, capitalizationIndicator: CapitalizationIndicator) =
        with(capitalizationIndicator) {
            CapitalizationKpiOnSalesModel(
                resultsLabel = textResolver.formatYourResultsInCampaign(
                    isParent,
                    campaign.takeLastTwo()
                ),
                retentionPercentage = textResolver.formatPercentage(pegRetentionPercentage.toPercentageFormat()),
                potentialKpiModel = getPotentialKpi(this),
                capitalizationKpi = mapCapitalizationKpi(this),
                tooltip = textResolver.getToolTipText(isParent)
            )
        }

    private fun mapCapitalizationKpi(capitalizationIndicator: CapitalizationIndicator) =
        with(capitalizationIndicator) {
            listOf(
                CoupledModel.GridWithHeaderItemModel(
                    header = CoupledModel.SingleItem(
                        label = ValueAttributes(
                            value = textResolver.getCapitalizationTitle(),
                            textAppearanceInt = R.style.TextAppearance_Gain_Single_Item_Label
                        ),
                        value = ValueAttributes(
                            value = capitalizationReal.formatWithThousands(),
                            textAppearanceInt = R.style.TextAppearance_Gain_Single_Item_Label
                        )
                    ),
                    kpiValues = getCapitalizationItems(this)
                )
            )
        }

    private fun getCapitalizationItems(capitalizationIndicator: CapitalizationIndicator) =
        with(capitalizationIndicator) {
            listOf(
                DefaultGridModel(
                    textResolver.getIncomesTitle(),
                    capitalizationEntries.formatWithThousands()
                ),
                DefaultGridModel(
                    textResolver.getReentriesTitle(),
                    capitalizationReentries.formatWithThousands()
                ),
                DefaultGridModel(
                    textResolver.getExpensesTitle(),
                    capitalizationExpenses.formatWithThousands()
                )
            )
        }

    private fun getSectionCapitalizationItems(capitalizationIndicator: CapitalizationIndicator) =
        with(capitalizationIndicator) {
            val successEntries =
                floor((capitalizationEntries - capitalizationProactive + (capitalizationProactive / 2)).toDouble()).toInt()

            listOf(
                DefaultGridModel(
                    textResolver.getAdjustedIncomesTitle(),
                    successEntries.formatWithThousands()
                ),
                DefaultGridModel(
                    textResolver.getReentriesTitle(),
                    capitalizationReentries.formatWithThousands()
                ),
                DefaultGridModel(
                    textResolver.getExpensesTitle(),
                    capitalizationExpenses.formatWithThousands()
                )
            )
        }

    private fun getPotentialKpi(capitalizationIndicator: CapitalizationIndicator) =
        with(capitalizationIndicator) {
            PossiblesPotentialKpiModel(
                totalPotentialKpiLabel = textResolver.getTotalPotentialsQuantityLabel(potentialTotal),
                potentialKpi = listOf(
                    DefaultGridModel(
                        label = textResolver.getPossibleEntries(),
                        value = potentialEntries.toString()
                    ),
                    DefaultGridModel(
                        label = textResolver.getPossiblesReentries(),
                        value = potentialReentries.toString()
                    )
                )
            )
        }

    fun mapBillingPeriodKpiData(
        capitalizationIndicator: CapitalizationIndicator,
        flagShowProactive: Boolean = false,
        isSE: Boolean = false
    ): List<CoupledModel> = with(capitalizationIndicator) {

        val items = arrayListOf(
            CoupledModel.SingleItem(
                label = ValueAttributes(
                    value = textResolver.getRetentionPEGsLabel(),
                    textAppearanceInt = R.style.TextAppearance_Retention_Single_Item_Label
                ),
                value = ValueAttributes(
                    value = textResolver.formatPercentage(pegRetentionPercentage.toPercentageFormat()),
                    textAppearanceInt = R.style.TextAppearance_Retention_Single_Item_Value
                )
            ),
            CoupledModel.GridsItemModel(
                kpiValues = listOf(
                    LeftAlignedGridModel(
                        label = textResolver.getPEGsRetainedText(),
                        value = pegRetentionReal.toString()
                    ),
                    LeftAlignedGridModel(
                        label = textResolver.getPEGSRetentionRemainingText(),
                        value = pegRetentionRemaining.toString()
                    )
                )
            ),
            CoupledModel.GridWithHeaderItemModel(
                header = CoupledModel.SingleItem(
                    label = ValueAttributes(
                        value = textResolver.getCapitalizationProjected(),
                        textAppearanceInt = R.style.TextAppearance_Gain_Single_Item_Label
                    ),
                    value = ValueAttributes(
                        value = capitalizationProjected.toString(),
                        textAppearanceInt = R.style.TextAppearance_Gain_Single_Item_Label
                    )
                ),
                kpiValues = getCapitalizationItems(this)
            )
        )

        if (flagShowProactive && isSE) {
            val successEntries =
                floor((capitalizationEntries - capitalizationProactive + (capitalizationProactive / 2)).toDouble()).toInt()

            items.add(
                CoupledModel.GridWithHeaderItemModel(
                    header = CoupledModel.SingleItem(
                        label = ValueAttributes(
                            value = textResolver.getCapitalizationSectionProjected(),
                            textAppearanceInt = R.style.TextAppearance_Gain_Single_Item_Label
                        ),
                        value = ValueAttributes(
                            value = (successEntries ?: 0).plus(
                                capitalizationReentries
                            ).minus(capitalizationExpenses).toString(),
                            textAppearanceInt = R.style.TextAppearance_Gain_Single_Item_Label
                        )
                    ),
                    kpiValues = getSectionCapitalizationItems(this)
                )
            )
        }

        return items
    }
}

