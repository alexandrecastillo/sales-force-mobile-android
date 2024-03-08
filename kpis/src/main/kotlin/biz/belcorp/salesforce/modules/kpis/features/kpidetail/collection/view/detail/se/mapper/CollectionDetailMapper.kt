package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.mapper

import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.CountryISO
import biz.belcorp.salesforce.core.domain.entities.saleforcestatus.SaleForceStatus
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.ProfitIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.RetentionIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model.CapitalizationKpiOnSalesModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.CollectionTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.ValueAttributes
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.common.CollectionMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.model.GainDetailSeModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.ContentBaseModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.Single
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.GridModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.LeftAlignedGridModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.LeftAlignedWithDetailsExpandedGridModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.LeftAlignedWithDetailsGridModel
import biz.belcorp.salesforce.modules.kpis.utils.StringUtils
import java.util.*

class CollectionDetailMapper(private val textResolver: CollectionTextResolver) :
    CollectionMapper(textResolver) {

    var sumAvanceDeGanancia = 0.0

    fun mapToCollection(container: CollectionContainer): GainDetailSeModel =
        with(container) {

            val previousCollection = collectionList.previousData
                ?: CollectionIndicator(campaign = collectionList.previousCampaign)
            val previousProfit =
                profitList.previousData ?: ProfitIndicator(campaign = profitList.previousCampaign)

            val recoveryAdvanceTitle =
                textResolver.getRecoveryAdvanceTitle(campaign)
            val profitReceivedTitle = textResolver.getProfitReceivedTitle(campaign)
            val chargedOrderTitle = textResolver.getChargedOrderTitle()

            val recoveryAdvanceList =
                createRecoveryAdvanceList(currencySymbol, previousCollection, previousProfit)
            val chargedOrderList = createChargedOrderList(previousCollection)
            val profitReceivedList = createProfitReceivedList(currencySymbol, previousProfit)

            val syncDateFormatted = textResolver.formatSyncDateLabel(
                syncDate.parseToDayMonthString(),
                syncDate.parseToHourMinAmPmString()
            )
            val tooltip = textResolver.getTooltip()

            return GainDetailSeModel(
                recoveryAdvanceTitle = recoveryAdvanceTitle,
                profitReceivedTitle = profitReceivedTitle,
                chargedOrderTitle = chargedOrderTitle,
                recoveryAdvanceList = recoveryAdvanceList,
                chargedOrderList = chargedOrderList,
                profitReceivedList = profitReceivedList,
                tooltip = tooltip,
                syncDate = syncDateFormatted
            )
        }

    fun mapToCollection(
        container: CollectionContainer,
        saleForceStatus: SaleForceStatus,
        capitalizationKpiOnSaleModel: CapitalizationKpiOnSalesModel
    ): GainDetailSeModel =
        with(container) {

            val previousCollection = collectionList.previousData
                ?: CollectionIndicator(campaign = collectionList.previousCampaign)
            val previousProfit =
                profitList.previousData ?: ProfitIndicator(campaign = profitList.previousCampaign)

            val retentionCollection = retentionList

            val capitalizationList = capitalizationList.previousData

            val recoveryAdvanceTitle =
                textResolver.getRecoveryAdvanceTitle(campaign)
            val profitReceivedTitle = textResolver.getProfitReceivedTitle(campaign)
            val chargedOrderTitle = textResolver.getChargedOrderTitle()

            val recoveryAdvanceList =
                createRecoveryAdvanceList(currencySymbol, previousCollection, previousProfit, saleForceStatus, capitalizationKpiOnSaleModel)
            val chargedOrderList = createChargedOrderList(previousCollection)
            val profitReceivedList =
                if(showProfitForSEEstablecida(UserProperties.session?.countryIso!!, saleForceStatus)){
                    createProfitReceivedListForSEEstablecida(currencySymbol, previousProfit, saleForceStatus, campaign, previousCollection, retentionCollection)
                }
                else{
                    createProfitReceivedListForSENueva(currencySymbol, previousProfit, saleForceStatus, campaign, previousCollection, retentionCollection, capitalizationList)
                }


            val syncDateFormatted = textResolver.formatSyncDateLabel(
                syncDate.parseToDayMonthString(),
                syncDate.parseToHourMinAmPmString()
            )
            val tooltip = textResolver.getTooltip()

            return GainDetailSeModel(
                recoveryAdvanceTitle = recoveryAdvanceTitle,
                profitReceivedTitle = profitReceivedTitle,
                chargedOrderTitle = chargedOrderTitle,
                recoveryAdvanceList = recoveryAdvanceList,
                chargedOrderList = chargedOrderList,
                profitReceivedList = profitReceivedList,
                tooltip = tooltip,
                syncDate = syncDateFormatted
            )
        }

    private fun showProfitForSEEstablecida(countryIso: String, saleForceStatus: SaleForceStatus, ) : Boolean{
        return  saleForceStatus.classification.equals("Establecida",true)  ||
                saleForceStatus.classification.equals("Reingresos",true)  ||
                    (countryIso == CountryISO.CHILE && saleForceStatus.classification.equals("Nuevas", true) &&
                        (saleForceStatus.subclassification.equals("Nueva 1", true) ||
                         saleForceStatus.subclassification.equals("Nueva 2", true) ||
                         saleForceStatus.subclassification.equals("Nueva 3", true)))
    }

    private fun createProfitReceivedListForSEEstablecida(
        currencySymbol: String,
        previousProfit: ProfitIndicator,
        saleForceStatus: SaleForceStatus,
        campaign: String,
        previousCollection: CollectionIndicator,
        retentionCollection: RetentionIndicator
    ): List<CoupledModel> {

        val profitReceivedList = arrayListOf<CoupledModel>()

        //region Ganancia de Pedidos a la fecha

        val gainOrdersDate = getGainOrdersDate(currencySymbol, previousCollection, previousProfit)
        profitReceivedList.add(gainOrdersDate)
        //endregion Ganancia de Pedidos a la fecha

        //region Otras formas de ganancia

        val otherGains = getOthersGain(currencySymbol, previousProfit, saleForceStatus, retentionCollection)
        profitReceivedList.add(otherGains)

        //endregion Otras formas de ganancia

        //region Avance de Ganancia

        val gainProjectedModel : CoupledModel = if(isSENueva(saleForceStatus.classification!!)){
            getGainProjectedModelSENueva(currencySymbol, previousProfit, campaign, sumAvanceDeGanancia)
        } else{
            getGainProjectedModelSEEstablecida(currencySymbol, previousProfit, campaign, sumAvanceDeGanancia)
        }
        profitReceivedList.add(0,gainProjectedModel)

        //endregion Avance de Ganancia

        return profitReceivedList
    }

    private fun createProfitReceivedListForSENueva(
        currencySymbol: String,
        previousProfit: ProfitIndicator,
        saleForceStatus: SaleForceStatus,
        campaign: String,
        previousCollection: CollectionIndicator,
        retentionCollection: RetentionIndicator,
        capitalizationList: CapitalizationIndicator?
    ): List<CoupledModel> {

        val profitReceivedList = arrayListOf<CoupledModel>()

        val gainsForSENueva = getGainsForSENueva(currencySymbol, previousCollection, previousProfit, capitalizationList,retentionCollection ,saleForceStatus, campaign)
        profitReceivedList.add(gainsForSENueva)

        return profitReceivedList
    }

    private fun getGainOrdersDate(
        currencySymbol: String,
        previousCollection: CollectionIndicator,
        profit: ProfitIndicator
    ): CoupledModel{

        val listGridModelsWithGainOrdersDate = arrayListOf<GridModel>()
        var totalGainOrderDate = 0.0

        profit.ordersRange.forEachIndexed{
            _, item  ->
            if(!item.range.equals("Bajo Valor Plus", true) &&
                !item.range.equals("Pedidos Bajo Valor Plus", true) &&
                !item.range.equals("Pedidos de Pedidos Bajo Valor Plus", true)){
                listGridModelsWithGainOrdersDate.add(LeftAlignedGridModel(
                    label = textResolver.getGainOrderDateLabel(item.range),
                    value = item.amount.toString()))
                totalGainOrderDate += item.amount
            }
        }

        sumAvanceDeGanancia += totalGainOrderDate

        return CoupledModel.GridWithHeaderItemModel(
            header = CoupledModel.SingleItem(
                label = ValueAttributes(
                    textResolver.getOrderDateTitle(),
                    R.style.TextAppearance_Gain_Grid_Single_Item_Label
                ),
                value = ValueAttributes(
                    formatCurrency(currencySymbol, totalGainOrderDate.toDouble()),
                    R.style.TextAppearance_Gain_Grid_Single_Item_Value
                )
             ),
            kpiValues = listGridModelsWithGainOrdersDate,
            spanItems = 1,
            hasDividerDecoration = true)

    }

    private fun getGainsForSENueva(
        currencySymbol: String,
        previousCollection: CollectionIndicator,
        previousProfit: ProfitIndicator,
        previousCapitalization: CapitalizationIndicator?,
        retentionCollection: RetentionIndicator,
        saleForceStatus: SaleForceStatus,
        campaign: String
    ): CoupledModel{

        val listGridModelsGainsSENueva = arrayListOf<GridModel>()
        var totalGainsSENueva = 0.0

        //region Capitalizacion

        val capitalizationTitle = textResolver.getCapitalizacionTitle()
        val capitalizationValue =  formatCurrency(currencySymbol, previousProfit.competitionCapitalization)
        val capitalizationDetails = textResolver.getCapitalizacionMessage(previousCapitalization!!.capitalizationReal.toString())

        totalGainsSENueva += previousProfit.competitionCapitalization

        val capitalizationItem = LeftAlignedWithDetailsGridModel(
            label = capitalizationTitle,
            value = capitalizationValue,
            details = capitalizationDetails
        )

        listGridModelsGainsSENueva.add(capitalizationItem)

        //endregion Capitalizacion

        //region Retencion 6d6

        /*
        Para Socias con Clasificación: Nueva

        Para PE se debe mostrar Ganancia 6d6 Bajo Valor y 6d6 Alto Valor
        Para BO Se debe mostrar solo Ganancia 6d6 (sin separación). Se debe de tomar el valor de 6d6 Bajo Valor
        Para CL, solo para socias con subclasificación "Nueva 2", "Nueva 3" se debe mostrar Ganancia 6d6 Bajo Valor y 6d6 Alto Valor
        Para el resto de paises que no estan mencionados en la parte de nuevas no se debe agregar el 6d6 (ni diferenciado ni simple).
        Para el Caso de Chile que la socia sea subclasificacion Nueva 1 no se debe mostrar 6d6.

         */

        if(retention6d6NeededNueva(UserProperties.session?.countryIso!!)){
            val retenttion6d6Title = textResolver.getRetention6d6Title()
            var retenttion6d6ValueUnformatted = 0.0
            if (previousProfit.competition6d6Total != null){
                retenttion6d6ValueUnformatted = previousProfit.competition6d6Total
            }
            val retenttion6d6Value =  formatCurrency(currencySymbol, retenttion6d6ValueUnformatted)
            val retenttion6d6Message = textResolver.getRetention6d6Detail(retentionCollection._6d6Low?.toInt().toString())

            totalGainsSENueva +=  previousProfit.competition6d6Total

            val retention6d6Item = LeftAlignedWithDetailsGridModel(
                label = retenttion6d6Title,
                value = retenttion6d6Value,
                details = retenttion6d6Message
            )

            listGridModelsGainsSENueva.add(retention6d6Item)

            sumAvanceDeGanancia += totalGainsSENueva
        }

        //endregion Retencion 6d6

        //region 6d6 Diferenciada
        // Only for: Colombia, México (solo socias nivel Platinum y diamante), costa rica, salvador, Guatemala, Dominicana
        if(retention6d6DifferentiatedNeededNueva(UserProperties.session?.countryIso!!, saleForceStatus.subclassification)){

            val retention6d6Sum = retentionCollection._6d6Low!!.toInt() + retentionCollection._6d6High!!.toInt()
            val retention6d6SumPercentage = previousProfit.competition6d6LowValue + previousProfit.competition6d6HighValue

            totalGainsSENueva += retention6d6SumPercentage


            val retention6d6DifferentiatedItem = LeftAlignedWithDetailsExpandedGridModel(
                label = textResolver.getRetention6d6DifferentiatedDetailTitle(retention6d6Sum.toString()),
                value = formatCurrency(currencySymbol,retention6d6SumPercentage),
                detailsFirstRow = textResolver.getRetention6d6DifferentiatedDetailFirstRow(retentionCollection._6d6Low!!.toInt().toString()),
                detailsFirstRowValue = formatCurrency(currencySymbol, previousProfit.competition6d6LowValue),
                detailsSecondRow = textResolver.getRetention6d6DifferentiatedDetailSecondtRow(retentionCollection._6d6High!!.toInt().toString()),
                detailsSecondRowValue = formatCurrency(currencySymbol, previousProfit.competition6d6HighValue)
            )

            listGridModelsGainsSENueva.add(retention6d6DifferentiatedItem)

            sumAvanceDeGanancia += totalGainsSENueva

        }

        //endregion 6d6 Diferenciada

        //region Cambio Nivel

        val cambioNivelTitle = textResolver.getCambioDeNivelTitle()
        val cambioNivelValue = formatCurrency(currencySymbol, previousProfit.competitionChangeLevel)
        val cambioNivelDetails = ""

        val cambioNivelItem = LeftAlignedWithDetailsGridModel(
            label = cambioNivelTitle,
            value = cambioNivelValue,
            details = cambioNivelDetails!!
        )

        listGridModelsGainsSENueva.add(cambioNivelItem)


        //endregion Cambio Nivel

        //region Ganancia Monto Asegurado

        if(secureGainAmountNeeded(UserProperties.session?.countryIso!!, saleForceStatus.classification)){
            val secureGainAmountTitle = textResolver.getSegureAmountGain()
            val secureGainAmountValue = formatCurrency(currencySymbol, previousProfit.competitionNewFixed)
            val secureGainAmountDetails = ""

            totalGainsSENueva += previousProfit.competitionNewFixed

            val secureGainAmountItem = LeftAlignedWithDetailsGridModel(
                label = secureGainAmountTitle,
                value = secureGainAmountValue,
                details = secureGainAmountDetails)

            listGridModelsGainsSENueva.add(secureGainAmountItem)
        }

        //endregion Ganancia Monto Asegurado

        return CoupledModel.GridWithHeaderItemModel(
            header = CoupledModel.SingleItem(
                label = ValueAttributes(
                    textResolver.formatTotalGainSENueva(campaign),
                    R.style.TextAppearance_Gain_Grid_Single_Item_Label
                ),
                value = ValueAttributes(
                    formatCurrency(currencySymbol, totalGainsSENueva),
                    R.style.TextAppearance_Gain_Grid_Single_Item_Value
                )
            ),
            kpiValues = listGridModelsGainsSENueva,
            spanItems = 1,
            hasDividerDecoration = true)

    }

    private fun secureGainAmountNeeded(
        countryIso: String,
        classification: String?, ): Boolean {
        return ( countryIso == CountryISO.MEXICO && (classification.equals("Nuevas", true) || classification.equals("Reingreso", true)) ) ||
            (classification.equals("Nuevas", true))
    }

    private fun getOthersGain(
        currencySymbol: String,
        previousProfit: ProfitIndicator,
        saleForceStatus: SaleForceStatus,
        retentionCollection: RetentionIndicator
    ): CoupledModel{

        val listGridModelsOthersGain = arrayListOf<GridModel>()
        var totalGainOthers = 0.0

        //region Retencion 6d6

        /*
        Para Socias con Clasificación: Establecida o Reingreso -->
            Para EC,BO,SV,PA y MX (solo para Socias con Nivel "Bronce", Nivel "Plata" y "Oro")
            Se debe mostrar solo Ganancia 6d6 (sin separación). Se debe tomar el valor de 6d6 Bajo valor.
         */

        if(retention6d6Needed(UserProperties.session?.countryIso!!, saleForceStatus.level)){
            val retenttion6d6Title = textResolver.getRetention6d6Title()
            var retenttion6d6ValueUnformatted = 0.0
            if (previousProfit.competition6d6Total != null){
                retenttion6d6ValueUnformatted = previousProfit.competition6d6Total
            }
            val retenttion6d6Value =  formatCurrency(currencySymbol, retenttion6d6ValueUnformatted)
            val retenttion6d6Message = textResolver.getRetention6d6Detail(retentionCollection._6d6Low?.toInt().toString())

            totalGainOthers +=  previousProfit.competition6d6Total

            val retention6d6Item = LeftAlignedWithDetailsGridModel(
                label = retenttion6d6Title,
                value = retenttion6d6Value,
                details = retenttion6d6Message
            )

            listGridModelsOthersGain.add(retention6d6Item)

            sumAvanceDeGanancia += totalGainOthers
        }

        //endregion Retencion 6d6

        //region 6d6 Diferenciada

        /*
        Para Socias con Clasificación: Establecida o Reingreso -->
            Para CO,PE,CL,GT,CR, DO y MX (en este caso solo Socias con Nivel "Platinum" y Nivel "Diamante"
            se debe mostrar Ganancia 6d6 Bajo Valor y 6d6 Alto Valor*/

        if(retention6d6DifferentiatedNeeded(UserProperties.session?.countryIso!!, saleForceStatus.level)){

            val retention6d6SumPercentage = previousProfit.competition6d6LowValue + previousProfit.competition6d6HighValue
            val retention6d6Sum = retentionCollection._6d6Low!!.toInt()

            val consultants6d6LowValue = if (retentionCollection._6d6Low - retentionCollection._6d6High!! > 0 ) retentionCollection._6d6Low - retentionCollection._6d6High else 0


            totalGainOthers += retention6d6SumPercentage


            val retention6d6DifferentiatedItem = LeftAlignedWithDetailsExpandedGridModel(
                label = textResolver.getRetention6d6DifferentiatedDetailTitle(retention6d6Sum.toString()),
                value = formatCurrency(currencySymbol,retention6d6SumPercentage),
                detailsFirstRow = textResolver.getRetention6d6DifferentiatedDetailFirstRow(consultants6d6LowValue.toInt().toString()),
                detailsFirstRowValue = formatCurrency(currencySymbol, previousProfit.competition6d6LowValue),
                detailsSecondRow = textResolver.getRetention6d6DifferentiatedDetailSecondtRow(retentionCollection._6d6High!!.toInt().toString()),
                detailsSecondRowValue = formatCurrency(currencySymbol, previousProfit.competition6d6HighValue)
            )

            listGridModelsOthersGain.add(retention6d6DifferentiatedItem)

            sumAvanceDeGanancia += totalGainOthers

        }

        //endregion 6d6 Diferenciada

        //region Concursos

        //TODO Implement in other ticket PFFVV-313
        /*
        if (previousProfit.ordersTotal.gtZero()) {
            val concursosTitle = textResolver.getGainByCompetitionTitle()
            val concursosValue =  formatCurrency(currencySymbol, previousProfit.ordersTotal)
            val concursosDetails = previousProfit.competitionTacticBonusLevel

            val concursosItem = LeftAlignedWithDetailsGridModel(
                label = concursosTitle,
                value = concursosValue,
                details = concursosDetails!!
            )

            listGridModelsOthersGain.add(concursosItem)
        }

        if (previousProfit.competitionTotal.gtZero()) {
            val concursosTotalTitle = textResolver.getGainByCompetitionTitle()
            val concursosTotalValue =  formatCurrency(currencySymbol, previousProfit.total)
            val concursosTotalDetails = previousProfit.competitionTacticBonusLevel

            val concursosItemTotal = LeftAlignedWithDetailsGridModel(
                label = concursosTotalTitle,
                value = concursosTotalValue,
                details = concursosTotalDetails!!
            )

            listGridModelsOthersGain.add(concursosItemTotal)

        }

         */

        //endregion Concursos

        //region Cambio Nivel

        val cambioNivelTitle = textResolver.getCambioDeNivelTitle()
        val cambioNivelValue = formatCurrency(currencySymbol, previousProfit.competitionChangeLevel)
        val cambioNivelDetails = ""

        val cambioNivelItem = LeftAlignedWithDetailsGridModel(
            label = cambioNivelTitle,
            value = cambioNivelValue,
            details = cambioNivelDetails!!
        )

        listGridModelsOthersGain.add(cambioNivelItem)

        sumAvanceDeGanancia += previousProfit.competitionChangeLevel


        //endregion Cambio Nivel

        return CoupledModel.GridWithHeaderItemModel(
            header = CoupledModel.SingleItem(
                label = ValueAttributes(
                    textResolver.getOtherWaysProfit(),
                    R.style.TextAppearance_Gain_Grid_Single_Item_Label
                ),
                value = ValueAttributes(
                    formatCurrency(currencySymbol, totalGainOthers),
                    R.style.TextAppearance_Gain_Grid_Single_Item_Value
                )
            ),
            kpiValues = listGridModelsOthersGain,
            spanItems = 1,
            hasDividerDecoration = true)

    }

    private fun retention6d6Needed(countryIso: String, level: String?): Boolean {
        return countryIso == CountryISO.ECUADOR ||
                countryIso == CountryISO.BOLIVIA ||
                countryIso == CountryISO.PANAMA ||
                countryIso == CountryISO.SALVADOR ||
            (countryIso == CountryISO.MEXICO &&
                (level.equals("Bronce",true) ||
                level.equals("Plata", true) ||
                level.equals("Oro", true)))
    }

    private fun retention6d6NeededNueva(countryIso: String): Boolean {
        return countryIso == CountryISO.BOLIVIA
    }

    private fun retention6d6DifferentiatedNeeded(countryIso: String, level: String?): Boolean {
        return countryIso == CountryISO.COLOMBIA ||
            countryIso == CountryISO.PERU ||
            countryIso == CountryISO.CHILE ||
            (countryIso == CountryISO.MEXICO && level.equals("Platinum", true) && level.equals("Diamante", true)) ||
            countryIso == CountryISO.COSTA_RICA ||
            countryIso == CountryISO.SALVADOR ||
            countryIso == CountryISO.GUATEMALA ||
            countryIso == CountryISO.DOMINICANA
    }

    private fun retention6d6DifferentiatedNeededNueva(countryIso: String, sublevel: String?): Boolean {
        return countryIso == CountryISO.PERU ||
            (countryIso == CountryISO.CHILE && sublevel.equals("Nueva 2", true) && sublevel.equals("Nueva 3", true))
    }

    private fun getGainProjectedModelSEEstablecida(
        currencySymbol: String,
        profit: ProfitIndicator,
        campaign: String,
        sumAvanceDeGanancia: Double
    ): CoupledModel =
        with(profit) {
            return CoupledModel.SingleItem(
                label = ValueAttributes(
                    value = textResolver.formatTotalGainSEEstablecida(campaign),
                    textAppearanceInt = R.style.TextAppearance_Gain_Single_Item_Label
                ),
                value = ValueAttributes(
                    value = formatCurrency(currencySymbol, sumAvanceDeGanancia),
                    textAppearanceInt = R.style.TextAppearance_Gain_Single_Item_Value
                )
            )
        }

    private fun getGainProjectedModelSENueva(
        currencySymbol: String,
        profit: ProfitIndicator,
        campaign: String,
        sumAvanceDeGanancia: Double
    ): CoupledModel =
        with(profit) {
            return CoupledModel.SingleItem(
                label = ValueAttributes(
                    value = textResolver.formatTotalGainSENueva(campaign),
                    textAppearanceInt = R.style.TextAppearance_Gain_Single_Item_Label
                ),
                value = ValueAttributes(
                    value = formatCurrency(currencySymbol, sumAvanceDeGanancia),
                    textAppearanceInt = R.style.TextAppearance_Gain_Single_Item_Value
                )
            )
        }

    private fun createRecoveryAdvanceList(
        currencySymbol: String,
        collection: CollectionIndicator,
        profit: ProfitIndicator,
        saleForceStatus: SaleForceStatus,
        capitalizationKpiOnSaleModel: CapitalizationKpiOnSalesModel
    ): List<ContentBaseModel> {

        // In fourth item:
        // If SE -- Establecida : Shows: Ganancia Potencial -- If SE -- Nueva : Shows Ingresos Logrados

        val capitalizationItem = capitalizationKpiOnSaleModel.capitalizationKpi.first().kpiValues.filter{ item ->
            item.label.equals("Ingresos")
        }.first()

        val ingresos = capitalizationItem.value

        val fourthItemSingle = if(isSENueva(saleForceStatus.classification!!)){
            Single(
                textResolver.getIngresosTitle(),
                ingresos,
                Constant.NEGATIVE_NUMBER_ONE,
                getDescriptionColor()
            )
        }
        else{
            Single(
                textResolver.getPotentialTitle(),
                formatCurrency(currencySymbol, profit.ordersPotential),
                Constant.NEGATIVE_NUMBER_ONE,
                getDescriptionColor()
            )
        }

        return listOf(
            Single(
                textResolver.getSociaLevelTitle(),
                StringUtils.capitalizePhrase(saleForceStatus.level!!) ,
                Constant.NEGATIVE_NUMBER_ONE,
                getDescriptionColor()
            ),
            Single(
                textResolver.getSociaCampaignResultTitle(),
                StringUtils.capitalizePhrase(saleForceStatus.achievement!!),
                Constant.NEGATIVE_NUMBER_ONE,
                getDescriptionColor()
            ),
            Single(
                textResolver.getRecoveryTitle(),
                textResolver.formatPercentage(collection.percentage.toPercentageFormat()),
                Constant.NEGATIVE_NUMBER_ONE,
                getDescriptionColor()
            ),
            fourthItemSingle
        )
    }

    private fun getDescriptionColor() =
        ContextCompat.getColor(textResolver.context, R.color.colorGridRetentionValue)

    private fun formatCurrency(currency: String, value: Double): String {
        return textResolver.formatCurrency(currency, value.formatWithLowerThousands())
    }

    private fun createRecoveryAdvanceList(
        currencySymbol: String,
        collection: CollectionIndicator,
        profit: ProfitIndicator
    ): List<ContentBaseModel> {
        return listOf(
            Single(
                textResolver.getRecoveryTitle(),
                textResolver.formatPercentage(collection.percentage.toPercentageFormat()),
                Constant.NEGATIVE_NUMBER_ONE,
                getDescriptionColor()
            ),
            Single(
                textResolver.getProfitAdvanceTitle(),
                formatCurrency(currencySymbol, collection.ordersTotalGained),
                Constant.NEGATIVE_NUMBER_ONE,
                getDescriptionColor()
            ),
            Single(
                textResolver.getPotentialTitle(),
                formatCurrency(currencySymbol, profit.ordersPotential),
                Constant.NEGATIVE_NUMBER_ONE,
                getDescriptionColor()
            )
        )
    }

    private fun createProfitReceivedList(
        currencySymbol: String,
        previousProfit: ProfitIndicator
    ): List<CoupledModel> {
        val profitReceivedList = arrayListOf<CoupledModel>()
        val gainProjectedModel = getGainProjectedModel(currencySymbol, previousProfit)
        profitReceivedList.add(gainProjectedModel)

        if (previousProfit.ordersTotal.gtZero()) {
            val gainByOrdersModel = getGainByOrdersModel(currencySymbol, previousProfit)
            profitReceivedList.add(gainByOrdersModel)
        }

        if (previousProfit.competitionTotal.gtZero()) {
            val gainByCompetitionModel = getGainByCompetitionModel(currencySymbol, previousProfit)
            profitReceivedList.add(gainByCompetitionModel)
        }
        return profitReceivedList
    }

    private fun getGainProjectedModel(
        currencySymbol: String,
        profit: ProfitIndicator
    ): CoupledModel =
        with(profit) {
            return CoupledModel.SingleItem(
                label = ValueAttributes(
                    value = textResolver.formatTotalGain(),
                    textAppearanceInt = R.style.TextAppearance_Gain_Single_Item_Label
                ),
                value = ValueAttributes(
                    value = formatCurrency(currencySymbol, total),
                    textAppearanceInt = R.style.TextAppearance_Gain_Single_Item_Value
                )
            )
        }

    private fun getGainByOrdersModel(
        currencySymbol: String,
        profit: ProfitIndicator
    ): CoupledModel =
        with(profit) {
            return CoupledModel.GridWithHeaderItemModel(
                header = CoupledModel.SingleItem(
                    label = ValueAttributes(
                        textResolver.getGainByOrdersTitle(),
                        R.style.TextAppearance_Gain_Grid_Single_Item_Label
                    ),
                    value = ValueAttributes(
                        formatCurrency(currencySymbol, ordersTotal),
                        R.style.TextAppearance_Gain_Grid_Single_Item_Value
                    )
                ),
                kpiValues = getGainByOrdersValues(currencySymbol, this),
                spanItems = Constant.NUMBER_TWO,
                hasDividerDecoration = false
            )
        }

    private fun getGainByCompetitionModel(
        currencySymbol: String,
        profit: ProfitIndicator
    ): CoupledModel =
        with(profit) {
            return CoupledModel.GridWithHeaderItemModel(
                header = CoupledModel.SingleItem(
                    label = ValueAttributes(
                        textResolver.getGainByCompetitionTitle(),
                        R.style.TextAppearance_Gain_Grid_Single_Item_Label
                    ),
                    value = ValueAttributes(
                        formatCurrency(currencySymbol, competitionTotal),
                        R.style.TextAppearance_Gain_Grid_Single_Item_Value
                    )
                ),
                kpiValues = getGainByCompetitionValues(currencySymbol, this),
                spanItems = Constant.NUMBER_TWO,
                hasDividerDecoration = false
            )
        }
    private fun getGainByOrdersValues(
        currencySymbol: String,
        profit: ProfitIndicator
    ): List<GridModel> =
        with(profit) {
            val sortedList = ordersRange.sortedBy { it.order }
            return sortedList.map {
                LeftAlignedGridModel(
                    label = it.range,
                    value = formatCurrency(currencySymbol, it.amount)
                )
            }
        }

    private fun getGainByCompetitionValues(
        currencySymbol: String,
        profit: ProfitIndicator
    ): List<GridModel> =
        with(profit) {
            val competitionList = arrayListOf<GridModel>()
            if (competitionCapitalization.gtZero()) {
                val capitalization = LeftAlignedGridModel(
                    label = textResolver.getCapitalizationTitle().toUpperCase(Locale.getDefault()),
                    value = formatCurrency(currencySymbol, competitionCapitalization)
                )
                competitionList.add(capitalization)
            }
            if (competition6d6Total.gtZero()) {
                val retention6d6 = LeftAlignedGridModel(
                    label = textResolver.getRetentionTitle().toUpperCase(Locale.getDefault()),
                    value = formatCurrency(currencySymbol, competition6d6Total)
                )
                competitionList.add(retention6d6)
            }

            return competitionList
        }

}
