package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos

import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity
import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleSERddEntity
import biz.belcorp.salesforce.core.entities.sql.focos.FocoRddEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.DetalleFoco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.DetalleFocoSE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import com.google.gson.Gson

class FocoRddMapper {

    private val gson = Gson()

    private fun parsearDetalle(modelo: FocoDetalleSERddEntity) = DetalleFocoSE(
        personaId = modelo.consultoraId,
        focos = gson.fromJson(modelo.focos, Array<Long>::class.java).toList(),
        campania = modelo.campania ?: throw Exception("Campanña inválida"))

    fun parsearDetallesSE(modelos: List<FocoDetalleSERddEntity>) =
        modelos.map { parsearDetalle(it) }

    private fun parsearDetalle(modelo: FocoDetalleRddEntity) = DetalleFoco(
        campania = requireNotNull(modelo.campania),
        region = modelo.region,
        zona = modelo.zona,
        seccion = modelo.seccion,
        usuario = modelo.usuario,
        focos = parsearIds(modelo.focos))

    fun parsearIds(ids: String?): Array<Long> = gson.fromJson(ids ?: Constant.EMPTY_ARRAY, Array<Long>::class.java)

    fun parsearDetalles(modelos: List<FocoDetalleRddEntity>) = modelos.map { parsearDetalle(it) }

    fun parsearFoco(modelo: FocoRddEntity) =
        Foco(id = modelo.codigo, descripcion = modelo.descripcion)

    fun parsearFocos(modelos: List<FocoRddEntity>) = modelos.map { parsearFoco(it) }
}
