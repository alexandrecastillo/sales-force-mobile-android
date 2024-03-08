package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data

import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity
import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.FocoRddMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.DetalleFoco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.GuardadoFocosRepository
import com.google.gson.Gson
import com.raizlabs.android.dbflow.kotlinextensions.*

class GuardadoFocosDBDataStore (private val focoRddMapper: FocoRddMapper) :
    GuardadoFocosRepository {

    val gson = Gson()

    override fun guardar(detalles: List<DetalleFoco>) {
        detalles.forEach { entity ->
            val model = FocoDetalleRddEntity()

            model.campania = entity.campania
            model.region = entity.region ?: Constant.HYPHEN
            model.zona = entity.zona ?: Constant.HYPHEN
            model.seccion = entity.seccion ?: Constant.HYPHEN
            model.usuario = entity.usuario
            model.focos = gson.toJson(entity.focos)
            model.enviado = Constant.CERO

            model.save()
        }
    }

    override fun obtenerDetallesNoEnviados(): List<DetalleFoco> {
        val where = (select
            from FocoDetalleRddEntity::class
            where (FocoDetalleRddEntity_Table.Enviado eq Constant.CERO))

        return focoRddMapper.parsearDetalles(where.queryList())
    }

    override fun marcarDetallesComoEnviados() {
        val update = update<FocoDetalleRddEntity>()
            .set(FocoDetalleRddEntity_Table.Enviado eq Constant.UNO)
            .where(FocoDetalleRddEntity_Table.Enviado eq Constant.CERO)

        update.execute()
    }
}
