package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos

import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.ObtenerCampaniasRddUseCase
import biz.belcorp.salesforce.modules.developmentpath.utils.numeroCampania

class AcuerdosReconocimientosPresenter(
    private val view: AcuerdosReconocimientosView,
    private val useCase: ObtenerCampaniasRddUseCase
) {
    fun getLastCampaignsSync(personId: Long, rol: Rol){
        val req = ObtenerCampaniasUseCase.Request.GetLatestCampaigns(personId, rol, GetLatestCampaignsSubscriber() )
        useCase.getLatestCampaignsSync(req)
    }

    private inner class GetLatestCampaignsSubscriber :
        BaseSingleObserver<ObtenerCampaniasUseCase.Response.GetLatestCampaigns>() {
        override fun onSuccess(t: ObtenerCampaniasUseCase.Response.GetLatestCampaigns) {
            view.setLatestThreeCampaignAgreements(t.campaigns)
        }
    }


}
