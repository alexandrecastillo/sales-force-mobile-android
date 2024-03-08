package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultoras.mappers

import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEndPeriodJoined
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraPosibleCambioNivelJoined
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.Consultora

class ConsultoraPosibleCambioNivelEntityDataMapper {

    fun parse(joinned: ConsultoraEndPeriodJoined?): Consultora {
        val consultora = Consultora()

        joinned?.apply {
            consultora.let {
                it.consultorasId = joinned.consultoraID
                it.nombre = joinned.nombre
                it.codigo = joinned.codigoConsultora
                it.telefonoCasa = joinned.telefonoCasa
                it.telefonoCelular = joinned.telefonoCelular
                it.codigo = joinned.codigo
                it.digitoVerificador = joinned.digitoVerificador
                it.monto = joinned.monto
                it.seccion = joinned.seccion
                it.nivelActual = joinned.nivelActual
                it.nivelSiguiente = joinned.nivelSiguiente
            }
        }

        return consultora
    }

    fun parseConsultoraEndPeriodList(collection: Collection<ConsultoraEndPeriodJoined>): List<Consultora> {
        val list = mutableListOf<Consultora>()

        for (entity in collection) {
            list.add(parse(entity))
        }

        return list
    }

    fun parse(joinned: ConsultoraPosibleCambioNivelJoined?): Consultora {
        val consultora = Consultora()

        joinned?.apply {
            consultora.let {
                it.consultorasId = joinned.consultoraID
                it.nombre = joinned.nombre
                it.codigo = joinned.codigoConsultora
                it.telefonoCasa = joinned.telefonoCasa
                it.telefonoCelular = joinned.telefonoCelular
                it.codigo = joinned.codigo
                it.digitoVerificador = joinned.digitoVerificador
                it.monto = joinned.monto
                it.seccion = joinned.seccion
                it.nivelActual = joinned.nivelActual
                it.nivelSiguiente = joinned.nivelSiguiente
            }
        }

        return consultora
    }

    fun parseConsultoraList(collection: Collection<ConsultoraPosibleCambioNivelJoined>): List<Consultora> {
        val list = mutableListOf<Consultora>()

        for (entity in collection) {
            list.add(parse(entity))
        }

        return list
    }

    fun obtainConsultoraListSize(size: Int?): Int? {
        return size
    }
}
