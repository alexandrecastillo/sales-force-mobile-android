package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades

import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import com.google.gson.Gson

class HabilidadesMapper {

    private val gson = Gson()

    fun map(
        serializado: String?,
        modelos: List<HabilidadEntity>
    ): MutableList<Habilidad> {
        val ids = gson.fromJson(serializado ?: Constant.EMPTY_ARRAY, Array<Long>::class.java)

        val modelosFiltrados = modelos.filter { ids.contains(it.codigo) }

        return modelosFiltrados.map {
            Habilidad(
                id = it.codigo,
                comentario = it.comentario,
                descripcion = it.descripcion,
                tipoIcono = it.iconoId
            )
        }.toMutableList()
    }
}
