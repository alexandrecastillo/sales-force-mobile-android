package biz.belcorp.salesforce.modules.postulants.features.evaluation.container

import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class PostulantEvaluationContainerPresenter(
    private val useCaseSession: ObtenerSesionUseCase
) : Presenter {

    private var mView: PostulantEvaluationContainerView? = null

    val sesion get() = requireNotNull(useCaseSession.obtener())

    fun setView(mView: PostulantEvaluationContainerView) {
        this.mView = mView
    }

    fun getForm() {
        this.mView?.loadForm(sesion?.countryIso ?: Constant.EMPTY_STRING)
    }
}
