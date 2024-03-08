package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos.reconocer

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.Comportamiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionable
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver

class ReconocerInput {
    data class Recuperar(
        val personaId: Long,
        val rol: Rol,
        val subscriber: SingleObserver<ReconocerOutput.Recuperar>
    )

    data class Invertir(
        val indice: Int,
        val subscriber: SingleObserver<ReconocerOutput.Recuperar>
    )

    data class Guardar(val subscriber: CompletableObserver)
}

class ReconocerOutput {
    data class Recuperar(
        val persona: PersonaRdd,
        val comportamientos: List<Seleccionable<Comportamiento>>,
        val totalComportamientos: Int,
        val totalSeleccionados: Int
    )
}
