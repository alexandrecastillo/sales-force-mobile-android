package biz.belcorp.salesforce.modules.developmentpath.common.broadcast.di

import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.*
import org.koin.dsl.module

internal val broadcastModule = module {
    factory { SenderCambioAcuerdos(get()) }
    factory { SenderCambioReconocimiento(get()) }
    factory { SenderEventoEditado(get()) }
    factory { SenderPlanificacionVisitaExitosa(get()) }
    factory { SenderRecargarVistaPlan(get()) }
    factory { SenderRegistroVisitaExitoso(get()) }
    factory { SenderVisibilidadMenu(get()) }
    factory { SenderIrAUbicacion(get()) }
    factory { SenderRecargarFocos(get()) }
    factory { SenderRecargarMisFocos(get()) }
    factory { SenderRecargarReconocimientosASuperior(get()) }
    factory { SenderRecargarHabilidades(get()) }
    factory { SenderRecargarHabilidadesEquipo(get()) }
    factory { SenderValorIndicador(get()) }
    factory { SenderIrAPerfil(get()) }
    factory { SenderRecargarHabilidadesPropias(get()) }
}
