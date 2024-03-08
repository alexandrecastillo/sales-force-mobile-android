package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.verdetalle.ReconocimientoCampaniaActualUseCase
import org.koin.dsl.module

internal val reconocimientoModule = module {
    factory { AdministrarReconocimientosUseCase(get(), get(), get(), get()) }
    factory { MostrarReconocimientoUseCase(get(), get(), get(), get(), get(), get()) }
    factory { ReconocimientoCampaniaActualUseCase(get(), get(), get(), get()) }
    factory { ComportamientosCampaniaActualUseCase(get(), get(), get(), get()) }
    factory { ReconocimientoAvanceUseCase(get(), get(), get(), get(), get()) }
    factory { ProgresoReconocimientoUseCase(get(), get(), get(), get(), get()) }
    factory { MostrarIrAReconocerUseCase(get(), get(), get(), get()) }
    factory { ManageReconcocimientosUseCase(get(), get(), get(), get(), get()) }
    factory { SaveRecognitionUseCase(get()) }
}
