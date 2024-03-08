package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadesAsignadasRDDEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadesAsignadasRDDEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.HabilidadMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.asignar.HabilidadesAsignaRepository
import com.google.gson.Gson
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class HabilidadesAsignaDBDataStore(
    private val gson: Gson,
    private val mapper: HabilidadMapper
) : HabilidadesAsignaRepository {

    override fun obtenerDetallesNoEnviados(): List<HabilidadesAsignaRepository.DetalleHabilidad> {
        val where = (select from HabilidadesAsignadasRDDEntity::class
            where (HabilidadesAsignadasRDDEntity_Table.Enviado eq Constant.CERO))
        return where.queryList().map { mapper.parsearDetalleHabilidad(it) }
    }

    override fun marcarDetallesComoEnviados() {
        val update = update<HabilidadesAsignadasRDDEntity>()
            .set(HabilidadesAsignadasRDDEntity_Table.Enviado eq Constant.UNO)
            .where(HabilidadesAsignadasRDDEntity_Table.Enviado eq Constant.CERO)
        update.execute()
    }

    override fun guardar(request: HabilidadesAsignaRepository.Request) {
        val habilidades = gson.toJson(request.habilidades)
        val model = HabilidadesAsignadasRDDEntity()
        model.habilidades = habilidades
        model.campania = request.campania
        model.enviado = Constant.CERO
        model.region = request.codigoRegion
        model.usuario = request.usuario
        model.seccion = request.codigoSeccion ?: Constant.HYPHEN
        model.codigoZona = request.codigoZona ?: Constant.HYPHEN
        model.save()
    }

    override fun obtenerPorUA(llaveUA: LlaveUA, campania: String): Array<Long> {
        return if (llaveUA.codigoZona == null)
            obtenerParaGr(requireNotNull(llaveUA.codigoRegion))
        else
            obtenerParaGz(requireNotNull(llaveUA.codigoRegion),
                requireNotNull(llaveUA.codigoZona), campania)
    }

    private fun obtenerParaGr(codigoRegion: String): Array<Long> {
        val sinCodigo = "-"
        val where = (Select(HabilidadesAsignadasRDDEntity_Table.Habilidades) from HabilidadesAsignadasRDDEntity::class
            innerJoin CampaniaUaEntity::class
            on ((HabilidadesAsignadasRDDEntity_Table.Region.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (CampaniaUaEntity_Table.Codigo.withTable()
            eq HabilidadesAsignadasRDDEntity_Table.Campania.withTable()))
            where (HabilidadesAsignadasRDDEntity_Table.Region.withTable() eq codigoRegion)
            and (HabilidadesAsignadasRDDEntity_Table.Zona.withTable() eq sinCodigo)
            and (HabilidadesAsignadasRDDEntity_Table.Seccion.withTable() eq sinCodigo))

        val modeloDetalles = where.querySingle()
        return gson.fromJson(modeloDetalles?.habilidades
            ?: Constant.EMPTY_ARRAY, Array<Long>::class.java)
    }

    private fun obtenerParaGz(codigoRegion: String,
                              codigoZona: String?,
                              campania: String): Array<Long> {
        val sinCodigo = Constant.HYPHEN
        val zona = codigoZona.takeIf { it != null } ?: sinCodigo
        val where = (select from HabilidadesAsignadasRDDEntity::class
            where (HabilidadesAsignadasRDDEntity_Table.Region.withTable() eq codigoRegion)
            and (HabilidadesAsignadasRDDEntity_Table.Zona.withTable() eq zona)
            and (HabilidadesAsignadasRDDEntity_Table.Seccion.withTable() eq sinCodigo)
            and (HabilidadesAsignadasRDDEntity_Table.Campania.withTable() eq campania))

        val modeloDetalles = where.querySingle()
        return gson.fromJson(modeloDetalles?.habilidades
            ?: Constant.EMPTY_ARRAY, Array<Long>::class.java)
    }
}
