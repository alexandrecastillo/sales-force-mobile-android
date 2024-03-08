package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cabecera.CabeceraUseCase

class CabeceraPresenter(
    private val view: CabeceraView,
    private val cabeceraUseCase: CabeceraUseCase,
    private val cabeceraModelMapper: CabeceraModelMapper
) {

    fun obtener(planId: Long) {
        cabeceraUseCase.ejecutar(planId, CabeceraSubscriber())
    }

    private fun cargarCabecera(viewModel: CabeceraViewModel) {
        cargarTextos(viewModel)
        pintarBoton(viewModel)
        mostrarUOcultarBotonRetroceder(viewModel)
        configurarBoton(viewModel)
    }

    private fun cargarTextos(viewModel: CabeceraViewModel) {
        viewModel.periodoCampania?.let {
            view.cargarNombreCampania(
                it,
                viewModel.nombreCortoCampania
            )
        }
        view.cargarRolZonaSeccion(viewModel.titulo,viewModel.rol,viewModel.sesion)
        view.cargarIniciales(viewModel.iniciales)
    }

    private fun pintarBoton(viewModel: CabeceraViewModel) {
        if (viewModel.esDuenia) {
            view.pintarMiRutaEnBoton()
        } else {
            view.pintarVerRutaEnBoton()
        }
    }

    private fun configurarBoton(viewModel: CabeceraViewModel) {
        if (viewModel.planId != null && viewModel.rol != null) {
            view.configurarBotonVerMiRuta(viewModel.planId, viewModel.rol)
            view.habilitarBotonVerMiRuta()
        } else {
            view.deshabilitarBotonVerMiRuta()
        }
    }


    private fun mostrarUOcultarBotonRetroceder(viewModel: CabeceraViewModel) {
        if (viewModel.esDuenia) {
            view.mostrarBotonRetroceder()
        } else {
            view.ocultarBotonRetroceder()
        }
    }

    private inner class CabeceraSubscriber : BaseSingleObserver<CabeceraUseCase.Response>() {

        override fun onError(e: Throwable) = e.printStackTrace()

        override fun onSuccess(t: CabeceraUseCase.Response) {
            doAsync {
                val viewModel = cabeceraModelMapper.map(t)
                uiThread {
                    cargarCabecera(viewModel)
                }
            }
        }
    }
}
