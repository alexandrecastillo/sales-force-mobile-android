package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoComportamiento

class ReconocimientoComportamientoAsociador(
    private val comportamientos: List<Comportamiento>,
    private val idsReconocidos: List<Long>
) {

    fun asociar(): List<ReconocimientoComportamiento> {
        return comportamientos.map { comportamiento ->
            val reconocido = verificarSiIdEstaReconocido(comportamiento.id)
            ReconocimientoComportamiento(comportamiento, reconocido)
        }
    }

    private fun verificarSiIdEstaReconocido(id: Long) = idsReconocidos.contains(id)
}
