package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades

import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionador

class ValidadorGuardadoHabilidades(private val seleccionadorHabilidades: Seleccionador<Habilidad>) {

    fun validar(): Boolean {
        return seleccionadorHabilidades.seleccionados.isNotEmpty()
    }
}
