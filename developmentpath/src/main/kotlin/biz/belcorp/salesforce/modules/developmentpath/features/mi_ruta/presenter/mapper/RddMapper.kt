package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.Rdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.RddUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.BusquedaViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.NoPlanificadasViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.PlanificadasViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.RddViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.mapa.PersonaEnMapaViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper.card.MiRutaCardMapper
import java.util.*

class RddMapper(private val cabeceraMapper: CabeceraMapper,
                    private val calendarioMapper: CalendarioMapper,
                    private val personaEnMapaMapper: PersonaEnMapaMapper,
                    private val buscadorMapper: BuscadorMapper,
                    private val miRutaCardMapper: MiRutaCardMapper,
                    private val eventoMapper: EventoMapper) {

    fun map(response: RddUseCase.Response): RddViewModel {
        return RddViewModel(
                cabeceraViewModel = cabeceraMapper.map(response),
                calendarioViewModel = calendarioMapper.map(response.rdd),
                planificadasViewModel = obtenerPlanificadasViewModel(response),
                noPlanificadasViewModel = obtenerNoPlanificadasViewModel(response.rdd),
                busquedaViewModel = obtenerBusquedaViewModel(response.rdd),
                personasEnMapaViewModel = obtenerPersonasEnMapaViewModel(response.rdd))
    }

    private fun obtenerBusquedaViewModel(rdd: Rdd) =
            BusquedaViewModel(buscadorMapper.map(rdd.buscador.buscar()), rdd.sugerencias)

    fun obtenerPlanificadasViewModel(response: RddUseCase.Response): PlanificadasViewModel {
        return PlanificadasViewModel(
                planificadas = miRutaCardMapper.map(response.rdd.visitasDelDiaSeleccionado),
                eventos = eventoMapper.map(response.rdd.eventosDelDiaSeleccionado),
                verAgregarEventos = response.infoPlanRdd.rol == response.sesion.rol,
                diaSeleccionado = obtenerDiaSeleccionado(response.rdd))
    }

    private fun obtenerDiaSeleccionado(rdd: Rdd): Date {
        return rdd.diaSeleccionado?.fecha?.time ?: Date()
    }

    private fun obtenerNoPlanificadasViewModel(rdd: Rdd): NoPlanificadasViewModel {
        return NoPlanificadasViewModel(
                miRutaCardMapper.map(rdd.noPlanificadasNoPosiblesConsultoras),
                miRutaCardMapper.map(rdd.noPlanificadasProactivas),
                miRutaCardMapper.map(rdd.noPlanificadasNoProactivas))
    }

    private fun obtenerPersonasEnMapaViewModel(rdd: Rdd) =
            PersonaEnMapaViewModel(personaEnMapaMapper.map(rdd.personasPlanificadasHoy))
}
