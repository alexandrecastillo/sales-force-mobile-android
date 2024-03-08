package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper.card

import biz.belcorp.salesforce.core.domain.entities.color.Color
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.MenuPersonaModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.card.MiRutaCardModel

class MiRutaCardMapper(
    private val datosBasicosMapper: DatosBasicosMapper,
    private val datosVisitaMapper: DatosVisitaMapper,
    private val datosRolMapper: DatosRolMapper,
    private val datosTipDesarrolloMapper: DatosTipsDesarrolloMapper,
    private val menuPersonaMapper: MenuPersonaMapper
) {

    fun map(entities: List<Any>): List<MiRutaCardModel> = entities.map { map(it) }

    fun map(entity: Any): MiRutaCardModel {
        return MiRutaCardModel(
            datosBasicos = obtenerDatosBasicos(entity),
            datosVisita = obtenerDatosVisita(entity),
            datosRol = obtenerDatosRol(entity),
            datosMenu = obtenerMenuModel(entity),
            datosTipsDesarrollo = obtenerDatosTipsDesarrollo(entity)
        )
    }

    private fun obtenerDatosTipsDesarrollo(entity: Any): MiRutaCardModel.DatosTipsDesarrollo? {
        return when (entity) {
            is ConsultoraRdd -> {
                MiRutaCardModel.DatosTipsDesarrollo(
                    entity.suggestedMessage ?: Constant.EMPTY_STRING,
                    Color.VERDE
                )
            }
            is Visita -> {
                if (entity.persona is ConsultoraRdd) {
                    MiRutaCardModel.DatosTipsDesarrollo(
                        (entity.persona as ConsultoraRdd).suggestedMessage ?: Constant.EMPTY_STRING,
                        Color.VERDE
                    )
                } else {
                    null
                }
            }
            else -> null
        }
    }

    private fun obtenerDatosBasicos(entity: Any): MiRutaCardModel.DatosBasicos {
        return when (entity) {
            is Visita -> datosBasicosMapper.map(entity.persona)
            is PersonaRdd -> datosBasicosMapper.map(entity)
            else -> throw Exception(ERROR_MESSAGE_DATA)
        }
    }

    private fun obtenerDatosVisita(entity: Any): MiRutaCardModel.DatosVisita {
        return when (entity) {
            is Visita -> datosVisitaMapper.mapPlanificada(entity)
            is PersonaRdd -> datosVisitaMapper.mapNoPlanificada(requireNotNull(entity.primeraVisitaNoRegistrada))
            else -> throw Exception(ERROR_MESSAGE_VISIT)
        }
    }

    private fun obtenerDatosRol(entity: Any): MiRutaCardModel.DatosRol {
        return when (entity) {
            is Visita -> obtenerDatosRol(entity.persona)
            is PosibleConsultoraRdd -> datosRolMapper.map(entity)
            is ConsultoraRdd -> datosRolMapper.map(entity)
            is SociaEmpresariaRdd -> datosRolMapper.map(entity)
            is GerenteZonaRdd -> datosRolMapper.map(entity)
            is GerenteRegionRdd -> datosRolMapper.map(entity)
            else -> throw Exception(ERROR_MESSAGE_VISIT)
        }
    }

    private fun obtenerMenuModel(entity: Any): MenuPersonaModel {
        return when (entity) {
            is Visita -> menuPersonaMapper.map(entity)
            is PersonaRdd -> menuPersonaMapper.map(entity)
            else -> throw Exception(ERROR_MESSAGE_VISIT)
        }
    }

    companion object {
        private const val ERROR_MESSAGE_VISIT =
            "Tipo inválido. No se pueden obtenerPorZonaId datos de visita"
        private const val ERROR_MESSAGE_DATA =
            "Tipo inválido. No se pueden obtenerPorZonaId datos básicos"
    }
}
