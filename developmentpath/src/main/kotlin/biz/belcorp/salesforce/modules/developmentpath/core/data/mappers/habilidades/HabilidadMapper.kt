package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades

import biz.belcorp.salesforce.core.entities.sql.habilidades.CampaniaHabilidadesJoinned
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadesAsignadasRDDEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.ReconocimientoHabilidadesRDDEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.HabilidadesReconocidasCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.asignar.HabilidadesAsignaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.reconocer.HabilidadesReconoceRepository
import com.google.gson.Gson

class HabilidadMapper {

    private fun parsearHabilidad(habilidad: HabilidadEntity) =
        Habilidad(
            id = habilidad.codigo,
            comentario = habilidad.comentario,
            descripcion = habilidad.descripcion,
            tipoIcono = habilidad.iconoId)


    fun parsearHabilidades(habilidades: List<HabilidadEntity>) = habilidades.map {
        parsearHabilidad(it)
    }

    fun parsearDetalleHabilidad(modelo: HabilidadesAsignadasRDDEntity) =
        HabilidadesAsignaRepository.DetalleHabilidad(
            codigoZona = modelo.codigoZona.toString(),
            habilidades = modelo.habilidades,
            codigoSeccion = modelo.seccion ?: Constant.HYPHEN,
            usuario = modelo.usuario
                ?: throw Exception("Usuario inválido"),
            codigoRegion = modelo.region
                ?: throw Exception("Region inválido"),
            campania = modelo.campania
                ?: throw Exception("Campaña inválida"))

    fun parsearHabilidadReconocidaJoinned(modelo: CampaniaHabilidadesJoinned): HabilidadesReconocidasCampania {
        val gson = Gson()

        return HabilidadesReconocidasCampania(
            campania = modelo.campania,
            habilidades = gson.fromJson(modelo.habilidades ?: Constant.EMPTY_ARRAY, Array<Long>::class.java))
    }

    fun parsearHabilidadReconocida(modelo: ReconocimientoHabilidadesRDDEntity) =
        HabilidadesReconoceRepository.HabilidadReconocida(
            codigoZona = modelo.zona,
            campania = modelo.campania,
            habilidades = modelo.habilidades,
            usuarioReconoce = modelo.usuarioReconoce,
            usuarioReconocida = modelo.usuarioReconocida,
            codigoSeccion = modelo.seccion,
            codigoRegion = modelo.region)
}
