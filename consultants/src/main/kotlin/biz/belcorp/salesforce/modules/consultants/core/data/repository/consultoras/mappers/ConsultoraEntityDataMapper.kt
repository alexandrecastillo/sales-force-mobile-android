package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultoras.mappers


import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.Consultora

class ConsultoraEntityDataMapper {

    fun parseConsultora(entity: ConsultoraEntity?, isPDV: Boolean = false): Consultora? {
        var consultora: Consultora? = null

        if (entity != null) {
            consultora = Consultora()
            consultora.consultorasId = entity.consultorasId
            consultora.zona = entity.zona
            consultora.seccion = entity.seccion
            consultora.nombre = entity.nombre
            consultora.codigo = entity.codigo
            consultora.telefonoCasa = entity.telefonoCasa
            consultora.telefonoCelular = entity.telefonoCelular

            consultora.segmento = entity.segmento
            consultora.saldoPendiente = entity.saldoPendiente
            consultora.primerNombre = entity.primerNombre
            consultora.segundoNombre = entity.segundoNombre
            consultora.primerApellido = entity.primerApellido
            consultora.segundoApellido = entity.segundoApellido

            try {
                consultora.latitud = entity.latitud?.toDouble()
            } catch (exception: NumberFormatException) {
                consultora.latitud = null
            }
            try {
                consultora.longitud = entity.longitud?.toDouble()
            } catch (exception: NumberFormatException) {
                consultora.longitud = null
            }
            consultora.digitoVerificador = entity.digitoVerificador

            if (isPDV) {
                consultora.nivel = entity.nivelPDV
                consultora.constancia = entity.constanciaPDV
            } else {
                consultora.nivel = entity.nivel
                consultora.constancia = entity.constancia
            }
        }

        return consultora
    }

    fun parseConsultoraList(
        collection: Collection<ConsultoraEntity>,
        isPDV: Boolean = false
    ): List<Consultora> {
        val list = ArrayList<Consultora>(20)
        var detalle: Consultora?
        for (entity in collection) {
            detalle = parseConsultora(entity, isPDV)
            if (detalle != null) {
                list.add(detalle)
            }
        }

        return list
    }

}
