package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades

import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadesAsignadasRDDEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import com.google.gson.Gson

class MisHabilidadesDataMapper {

    val gson = Gson()

    fun parse(
        habilidadesMaestras: List<HabilidadEntity>,
        habilidadesDetalle: HabilidadesAsignadasRDDEntity
    ): List<Habilidad> {

        val ids = gson.fromJson(habilidadesDetalle.habilidades ?: "[]", Array<Long>::class.java)

        val filtradas = habilidadesMaestras.filter { it.codigo in ids }
        return filtradas.map { parse(it) }
    }

    private fun parse(entidad: HabilidadEntity): Habilidad {
        return Habilidad(entidad.codigo, entidad.comentario, entidad.descripcion, entidad.iconoId)
    }
}
