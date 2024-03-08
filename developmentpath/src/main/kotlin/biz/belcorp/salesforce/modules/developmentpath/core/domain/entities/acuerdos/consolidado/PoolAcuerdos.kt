package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo

class PoolAcuerdos(private val acuerdos: List<Acuerdo>) {
    fun buscar(codigoCampania: String): List<Acuerdo> {
        return acuerdos.filter { it.codigoCampania == codigoCampania }
    }
}
