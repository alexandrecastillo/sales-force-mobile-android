package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.parametros.ParametrosRdd

interface ConfigRddRepository {
    fun get(planId: Long): ParametrosRdd?
}
