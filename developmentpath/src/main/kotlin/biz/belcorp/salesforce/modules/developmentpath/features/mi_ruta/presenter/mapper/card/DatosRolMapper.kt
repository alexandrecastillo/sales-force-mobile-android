package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper.card

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.*
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.card.MiRutaCardModel

class DatosRolMapper {

    fun map(entity: ConsultoraRdd): MiRutaCardModel.DatosRolConsultora {
        return MiRutaCardModel.DatosRolConsultora(
            rol = entity.rol,
            colorSegmento = obtenerColorSegmento(entity),
            segmento = entity.segmentoInterno.orEmpty(),
            color = obtenerColorSegmento(entity),
            cantidadProductosPPU = entity.cantidadProductoPPU,
            mostrarCantidadProductosPPU = entity.mostrarCantidadProductosPPU(),
            codigo = entity.codigo
        )
    }

    private fun obtenerColorSegmento(entity: ConsultoraRdd): MiRutaCardModel.Color {
        return when (entity.tipo) {
            ConsultoraRdd.Tipo.NUEVA -> MiRutaCardModel.Color.SANDIA
            ConsultoraRdd.Tipo.ESTABLECIDA, ConsultoraRdd.Tipo.C3 -> MiRutaCardModel.Color.AMARILLO
            else -> MiRutaCardModel.Color.NINGUNO
        }
    }

    fun map(entity: SociaEmpresariaRdd): MiRutaCardModel.DatosRolSociaEmpresaria {
        return MiRutaCardModel.DatosRolSociaEmpresaria(
            rol = entity.rol,
            nivelProductividad = entity.levelProductivity,
            exitosa = entity.exitosa,
            esNueva = entity.esNueva,
            textoNueva = entity.subClasificacionLider,
            color = obtenerColorExito(entity),
            codigo = entity.codigo
        )
    }

    private fun obtenerColorExito(entity: SociaEmpresariaRdd): MiRutaCardModel.Color {
        return if (entity.exitosa) {
            MiRutaCardModel.Color.VERDE
        } else {
            MiRutaCardModel.Color.ROJO
        }
    }

    fun map(entity: PosibleConsultoraRdd) =
        MiRutaCardModel.DatosRolPosibleConsultora(
            rol = entity.rol,
            tipo = entity.texto,
            color = obtenerColorPosibleConsultora(),
            codigo = entity.documento
        )

    private fun obtenerColorPosibleConsultora() = MiRutaCardModel.Color.AZUL_POSTULANTE

    fun map(entity: GerenteZonaRdd): MiRutaCardModel.DatosRolGerenteZona {
        return MiRutaCardModel.DatosRolGerenteZona(
            estadoProductividad = entity.estadoProductividad.orEmpty(),
            rol = entity.rol,
            esNueva = entity.esNueva,
            textoNueva = entity.textoNueva,
            color = obtenerColorProductividad(entity.estadoProductividad),
            codigo = entity.id.toString()
        )
    }

    fun map(entity: GerenteRegionRdd): MiRutaCardModel.DatosRolGerenteRegion {
        return MiRutaCardModel.DatosRolGerenteRegion(
            estadoProductividad = entity.estadoProductividad.orEmpty(),
            rol = entity.rol,
            color = obtenerColorProductividad(entity.estadoProductividad),
            codigo = entity.id.toString()
        )
    }

    private fun obtenerColorProductividad(productividad: String?): MiRutaCardModel.Color {
        return when {
            productividad?.contains("Estable") == true ->
                MiRutaCardModel.Color.AMARILLO_ESTABLE
            productividad?.contains("Productiva") == true ->
                MiRutaCardModel.Color.VERDE
            productividad?.contains("Crítica") == true ->
                MiRutaCardModel.Color.ROJO
            else ->
                MiRutaCardModel.Color.NINGUNO
        }
    }

    private val SociaEmpresariaRdd.levelProductivity
        get() = this.let {
            val stringBuilder = StringBuilder()
            if (seccion.nivel != null) stringBuilder.append(seccion.nivel!!.trim())
            if (seccion.nivel == null && productividad != null) stringBuilder.append(productividad.trim())
            if (seccion.nivel != null && productividad != null) stringBuilder.append(" ${Constant.HYPHEN} ${productividad.trim()}")
            if (seccion.nivel == null && productividad == null) stringBuilder.append(Constant.HYPHEN)

            stringBuilder.toString()
        }

}
