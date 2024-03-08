package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas

import biz.belcorp.salesforce.core.domain.entities.session.Sesion


interface SessionPersonRepository {

    fun get(): Sesion

}
