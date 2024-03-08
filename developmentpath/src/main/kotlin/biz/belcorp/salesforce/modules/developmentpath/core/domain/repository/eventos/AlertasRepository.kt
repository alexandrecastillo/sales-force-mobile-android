package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Alerta

interface AlertasRepository {
    fun recuperar(): List<Alerta?>
}
