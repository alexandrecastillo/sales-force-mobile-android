package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.verdetalle

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoComportamiento
import io.reactivex.SingleObserver

class ReconocerInput {
    data class Recuperar(
        val personaId: Long,
        val rol: Rol,
        val subscriber: SingleObserver<ReconocerOutput.Recuperar>
    )
}

class ReconocerOutput {
    data class Recuperar(
        val persona: PersonaRdd,
        val reconocimientos: List<ReconocimientoComportamiento>
    )
}
