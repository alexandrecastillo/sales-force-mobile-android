package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.view

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.model.AvanceModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.model.ConsultantInfoModel

interface AvanceVisitasView {

    fun pintarAvance(avance: AvanceModel)
    fun showProgressCampaignInfo(consultantInfoModel: ConsultantInfoModel)
}
