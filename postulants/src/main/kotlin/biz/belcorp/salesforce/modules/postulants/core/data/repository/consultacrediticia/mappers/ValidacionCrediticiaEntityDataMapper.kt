package biz.belcorp.salesforce.modules.postulants.core.data.repository.consultacrediticia.mappers

import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio.ConsultaCrediticia2Entity
import biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio.ConsultaCrediticiaInternaEntity
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.crediticio.ValidacionCrediticiaExterna
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.crediticio.ValidacionCrediticiaInterna
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoCrediticio

class ValidacionCrediticiaEntityDataMapper {

    fun parseValidacionCrediticiaInterna(entity: ConsultaCrediticiaInternaEntity): ValidacionCrediticiaInterna {

        val consultaCrediticiaInterna = ValidacionCrediticiaInterna()

        consultaCrediticiaInterna.bloqueosInternos = entity.tieneBloqueo
        consultaCrediticiaInterna.bloqueos = BloqueoEntityDataMapper().parseBloqueo(entity.bloqueos)

        consultaCrediticiaInterna.valoracionBelcorp = entity.valoracionBelcorp
        consultaCrediticiaInterna.motivoBloqueo = entity.motivoBloqueo
        consultaCrediticiaInterna.deudaBelcorp = entity.deudaBelcorp

        return consultaCrediticiaInterna
    }

    fun parseValidacionCrediticiaExterna(
        pais: String,
        entity: ConsultaCrediticia2Entity
    ): ValidacionCrediticiaExterna {

        val consultaCrediticiaExterna = ValidacionCrediticiaExterna()

        consultaCrediticiaExterna.enumEstadoCrediticio = entity.enumEstadoCrediticio
        consultaCrediticiaExterna.bloqueosExternos = getBloqueoExterno(entity.enumEstadoCrediticio)
        consultaCrediticiaExterna.mensaje = entity.mensaje.orEmpty()
        consultaCrediticiaExterna.requiereAprobacionSAC = entity.requiereAprobacionSAC
        consultaCrediticiaExterna.primerNombre = entity.primerNombre.orEmpty()
        consultaCrediticiaExterna.segundoNombre = entity.segundoNombre.orEmpty()
        consultaCrediticiaExterna.primerApellido = entity.primerApellido.orEmpty()
        consultaCrediticiaExterna.segundoApellido = entity.segundoApellido.orEmpty()
        consultaCrediticiaExterna.respuestaServicio = entity.respuestaServicio.orEmpty()
        consultaCrediticiaExterna.fechaNacimiento = entity.fechaNacimiento.orEmpty()

        when (pais) {
            Pais.PERU.codigoIso -> {
                if (entity.enumEstadoCrediticio == 1 || entity.enumEstadoCrediticio == 2) {
                    consultaCrediticiaExterna.esApta = true
                    consultaCrediticiaExterna.explicaciones =
                        entity.explicaciones?.mapNotNull { it.valor } ?: emptyList()
                } else if (entity.enumEstadoCrediticio == 3) {
                    consultaCrediticiaExterna.esApta = false
                    if (entity.buro == "SENTINEL") {
                        consultaCrediticiaExterna.explicaciones =
                            entity.motivos?.mapNotNull { it.descripcion } ?: emptyList()
                    } else if (entity.buro == "EQUIFAX") {
                        consultaCrediticiaExterna.explicaciones =
                            entity.explicaciones?.mapNotNull { it.valor } ?: emptyList()
                    }
                }
            }
        }

        return consultaCrediticiaExterna
    }

    private fun getBloqueoExterno(valueBloqueoExterno: Int): Boolean {
        return !(UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.SIN_CONSULTAR.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.CONDICIONADA_FIADOR.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.CONDICIONADA_CARTA_DESCARGO.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.CARTA_DESCARGO_Y_FIADOR.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.CONDICIONADA.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.SUJETO_NO_ENCONTRADO.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.PODRIA_SER_CONSULTORA.value == valueBloqueoExterno
        )
    }

}
