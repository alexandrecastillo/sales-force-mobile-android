package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.AcuerdoModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.campaniaactual.AcuerdosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.consolidado.ConsolidadoAcuerdosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.AcuerdosHistoricosModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.AcuerdosHistoricosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.AcuerdosReconocimientosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.ComportamientosMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.ComportamientosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.detalle.ReconocimientoCampaniaActualPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.detalle.VerReconocimientoMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.ProgresoComportamientosMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.ProgresoComportamientosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.detalle.ProgresoReconocimientoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer.GuardarReconocimientoMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer.ReconocerComportamientosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.redireccion.IrAReconocerPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.util.ProveedorIconoComportamiento
import org.koin.core.module.Module
import org.koin.dsl.module

internal val acuerdosReconocimientosModules by lazy {
    listByElementsOf<Module>(
        acuerdosModule,
        acuerdosHistoricoModule,
        acuerdosConsolidadoModule,
        comportamientosModule,
        reconocimietosModule
    )
}

private val acuerdosModule = module {
    factory { AcuerdoModelMapper() }
    factory { params ->
        AcuerdosPresenter(
            view = params[0],
            mapper = get(),
            obtenerAcuerdosCampaniaActualUseCase = get(),
            obtenerAcuerdosUseCase = get(),
            modificarAcuerdosUseCase = get(),
        )
    }
}

private val acuerdosHistoricoModule = module {
    factory { AcuerdosHistoricosModelMapper() }
    factory { params ->
        AcuerdosHistoricosPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
}

private val acuerdosConsolidadoModule = module {
    factory { params ->
        ConsolidadoAcuerdosPresenter(
            view = params[0],
            useCase = get()
        )
    }
}

private val comportamientosModule = module {
    factory { ProveedorIconoComportamiento() }

    factory { VerReconocimientoMapper() }
    factory { ComportamientosMapper(get()) }
    factory { ProgresoComportamientosMapper(get()) }
    factory { GuardarReconocimientoMapper() }

    factory { params ->
        ReconocimientoCampaniaActualPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
    factory { params ->
        ComportamientosPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
    factory { params ->
        ProgresoReconocimientoPresenter(
            view = params[0],
            useCase = get()
        )
    }
    factory { params ->
        ProgresoComportamientosPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
    factory { params ->
        ReconocerComportamientosPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
    factory { params ->
        IrAReconocerPresenter(
            view = params[0],
            useCase = get()
        )
    }
}

private val reconocimietosModule = module {
    factory { params ->
        AcuerdosReconocimientosPresenter(
            view = params[0],
            useCase = get()
        )
    }
}
