package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.NotificacionesEventosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.SenderIrAAgregarEvento
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.SenderIrAVerDetalleEvento
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.alarmas.AlarmasWorkManager
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.alarmas.AlarmasWorker
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.model.DetalleEventoMapper
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.presenter.DetalleEventoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.presenter.AgregarEventoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.sincronizacion.*
import org.koin.dsl.module

val eventosModule = module {
    factory { AgregarEventoModelMapper() }
    factory { DetalleEventoMapper() }
    factory { params ->
        DetalleEventoPresenter(params[0], get(), get(), get(), get(), get())
    }
    factory<AlarmasWorker> { AlarmasWorkManager(get()) }
    factory { AgregarEventoPresenter(get(), get(), get(), get()) }
    factory<BroadcastEventosHandler> { BroadcastEventosHelper(get()) }
    factory<NotificacionesEventosPresenter> { NotificacionesEventosWorker(get(), get()) }
    factory<NotificacionEventosHandler> { NotificacionEventoHelper(get()) }
    factory { SenderIrAAgregarEvento(get()) }
    factory { SenderIrAVerDetalleEvento(get()) }
}
