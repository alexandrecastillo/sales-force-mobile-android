package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.postulants

import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.PostulantKpi
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model.PostulantKpiModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.DefaultGridModel

class PostulantKpiMapper(val textResolver: PostulantKpiTextResolver) {

    fun map(isParent: Boolean, postulantModel: PostulantKpi): PostulantKpiModel =
        with(postulantModel) {
            return PostulantKpiModel(
                capitalizationLabel = textResolver.formatHandleYourCapitalization(
                    isParent,
                    currentCampaign
                ),
                kpiValues = listOf(
                    DefaultGridModel(
                        label = textResolver.getPreApprovedText(),
                        value = preApproved.toString()
                    ),
                    DefaultGridModel(
                        label = textResolver.getApprovedText(),
                        value = approved.toString()
                    ),
                    DefaultGridModel(
                        label = textResolver.getInEvaluationText(),
                        value = inEvaluation.toString()
                    ),
                    DefaultGridModel(
                        label = textResolver.getAnticipatedIncomesText(),
                        value = anticipatedEntries.toString()
                    )
                )
            )
        }
}
