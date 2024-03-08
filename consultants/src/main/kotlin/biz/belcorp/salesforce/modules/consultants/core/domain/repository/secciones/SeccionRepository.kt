package biz.belcorp.salesforce.modules.consultants.core.domain.repository.secciones

import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import io.reactivex.Observable

interface SeccionRepository {
    val all: Observable<List<Seccion>>
}
