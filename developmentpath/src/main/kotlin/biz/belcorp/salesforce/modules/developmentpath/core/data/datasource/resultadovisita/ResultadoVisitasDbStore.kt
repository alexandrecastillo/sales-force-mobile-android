package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.resultadovisita

import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas.ConsultoraRDDMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Method
import com.raizlabs.android.dbflow.sql.language.Select

class ResultadoVisitasDbStore(private val consultoraRDDMapper: ConsultoraRDDMapper) {

    fun recuperarCantidadConsultorasFacturadas(): Long {
        return (Select(Method.count(ConsultoraEntity_Table.ConsultorasId.withTable().distinct()))
            from ConsultoraEntity::class
            innerJoin DetallePlanRutaRDDEntity::class
            on (ConsultoraEntity_Table.ConsultorasId.withTable()
            eq DetallePlanRutaRDDEntity_Table.ConsultorasID.withTable())
            where (ConsultoraEntity_Table.pasoPedido eq Constant.UNO)).longValue()
    }

    fun recuperarCantidadConsultorasNoFacturadas(): Long {
        return (Select(Method.count(ConsultoraEntity_Table.ConsultorasId.withTable().distinct()))
            from ConsultoraEntity::class
            innerJoin DetallePlanRutaRDDEntity::class
            on (ConsultoraEntity_Table.ConsultorasId.withTable()
            eq DetallePlanRutaRDDEntity_Table.ConsultorasID.withTable())
            where (ConsultoraEntity_Table.pasoPedido eq Constant.CERO)).longValue()
    }

    fun recuperarConsultorasFacturadas(): List<ConsultoraRdd> {
        return seleccionarPorPasoPedido(Constant.UNO)
    }

    fun recuperarConsultorasNoFacturadas(): List<ConsultoraRdd> {
        return seleccionarPorPasoPedido(Constant.CERO)
    }

    private fun seleccionarPorPasoPedido(value: Int): List<ConsultoraRdd> {
        val where = (Select(ConsultoraEntity_Table.ConsultorasId.distinct().withTable(),
            ConsultoraEntity_Table.Seccion.withTable(),
            ConsultoraEntity_Table.PrimerNombre.withTable(),
            ConsultoraEntity_Table.SegundoNombre.withTable(),
            ConsultoraEntity_Table.PrimerApellido.withTable(),
            ConsultoraEntity_Table.SegundoApellido.withTable(),
            ConsultoraEntity_Table.TelefonoCelular.withTable(),
            ConsultoraEntity_Table.TelefonoCasa.withTable(),
            ConsultoraEntity_Table.DocumentoIdentidad.withTable(),
            ConsultoraEntity_Table.Codigo.withTable(),
            ConsultoraEntity_Table.Segmento.withTable(),
            ConsultoraEntity_Table.SegmentoInternoFFVV.withTable(),
            ConsultoraEntity_Table.pasoPedido.withTable(),
            ConsultoraEntity_Table.DigVerif.withTable(),
            ConsultoraEntity_Table.CantidadProductoPPU.withTable()
        )
            from ConsultoraEntity::class
            innerJoin DetallePlanRutaRDDEntity::class
            on (ConsultoraEntity_Table.ConsultorasId.withTable()
            eq DetallePlanRutaRDDEntity_Table.ConsultorasID.withTable())
            where (ConsultoraEntity_Table.pasoPedido eq value)
            orderBy (DetallePlanRutaRDDEntity_Table.ID.withTable().asc()))

        return where.queryList().map { consultoraRDDMapper.parse(it) }
    }
}
