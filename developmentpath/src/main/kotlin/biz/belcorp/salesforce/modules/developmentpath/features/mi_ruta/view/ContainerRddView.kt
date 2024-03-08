package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view

import biz.belcorp.salesforce.core.events.EventSubject

interface ContainerRddView {
    fun setupSyncObservers(subjects: Array<EventSubject>)
    fun ocultarSnackbar()
}
