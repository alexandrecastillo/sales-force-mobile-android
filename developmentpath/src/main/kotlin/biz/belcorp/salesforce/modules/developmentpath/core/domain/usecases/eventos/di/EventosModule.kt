package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.*
import org.koin.dsl.module

internal val eventosModule = module {
    factory { CambioEventoUseCase(get(), get(), get(), get(), get(), get()) }
    factory { ConfirmarEventoAtraccionUseCase(get(), get(), get(), get(), get()) }
    factory { CrearEventoUseCase(get(), get(), get(), get(), get(), get()) }
    factory { EditarEventoUseCase(get(), get(), get(), get(), get(), get()) }
    factory { EliminarEventoUseCase(get(), get(), get(), get(), get(), get()) }
    factory { ProgramarAlarmasEventosUseCase(get(), get(), get(), get(), get()) }
    factory { RecuperarDatosAgregarEventoUseCase(get(), get(), get(), get(), get(), get()) }
    factory { RecuperarEventoUseCase(get(), get(), get(), get(), get()) }
    factory { RecuperarRelacionObservadorEventoUseCase(get(), get(), get(), get()) }
    factory { SolicitarEventosCambiadosUseCase(get(), get(), get(), get(), get(), get(), get(), get()) }
}
