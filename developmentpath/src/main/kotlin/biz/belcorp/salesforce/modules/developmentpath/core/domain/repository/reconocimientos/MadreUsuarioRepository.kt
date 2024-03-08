package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.UsuarioMadre

interface MadreUsuarioRepository {
    fun recuperar(): UsuarioMadre?
}
