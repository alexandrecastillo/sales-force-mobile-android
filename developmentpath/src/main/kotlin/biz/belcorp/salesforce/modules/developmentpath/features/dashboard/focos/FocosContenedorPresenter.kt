package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.EstructuraFocosHabilidades
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Regiones
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Secciones
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Zonas
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.DeterminarFocosParaRol

class FocosContenedorPresenter(private val view: FocosContenedorView, private val useCase: DeterminarFocosParaRol) {

    fun decidirFocosSegunRol() {
        useCase.ejecutar(FocosParaRolSubscriber())
    }

    inner class FocosParaRolSubscriber : BaseSingleObserver<EstructuraFocosHabilidades>() {

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }

        override fun onSuccess(t: EstructuraFocosHabilidades) {
            if (t.verMisFocos) {
                view?.instanciarMisFocos()
            }
            if (t.verMisHabilidades) {
                view?.instanciarMisHabilidades()
            }
            when (t.focosHabilidadesUa) {
                is Secciones -> view?.instanciarFocosEquipoGz()
                is Zonas -> view?.instanciarFocosEquipoGr()
                is Regiones -> view?.instanciarFocosEquipoDv()
            }
        }
    }
}
