package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.acompaniamiento.CantidadIntencionPedidoEntity
import biz.belcorp.salesforce.core.entities.sql.acompaniamiento.CantidadIntencionPedidoEntity_Table
import biz.belcorp.salesforce.core.entities.sql.path.IntencionPedidoDbModel
import biz.belcorp.salesforce.core.entities.sql.path.IntencionPedidoDbModel_Table
import biz.belcorp.salesforce.core.utils.doOnCompletable
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.core.utils.guionSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import com.raizlabs.android.dbflow.kotlinextensions.*
import io.reactivex.Completable
import io.reactivex.Single

class IntencionPedidoDb {

    fun guardar(modelo: IntencionPedidoDbModel) {
        modelo.save()
    }

    fun recuperar(llaveUa: LlaveUA): IntencionPedidoDbModel? {
        val where = (select from IntencionPedidoDbModel::class
            where (IntencionPedidoDbModel_Table.Region.withTable() eq llaveUa.codigoRegion)
            and (IntencionPedidoDbModel_Table.Zona.withTable() eq llaveUa.codigoZona)
            and (IntencionPedidoDbModel_Table.Seccion.withTable() eq llaveUa.codigoSeccion)
            and (IntencionPedidoDbModel_Table.ConsultoraId.withTable() eq llaveUa.consultoraId))

        return where.querySingle()
    }

    fun recuperarNoEnviados(): List<IntencionPedidoDbModel> {
        val where = (select from IntencionPedidoDbModel::class
            where (IntencionPedidoDbModel_Table.Enviado eq false))

        return where.queryList()
    }

    fun marcarEnviados(): Completable {
        return doOnCompletable {
            com.raizlabs.android.dbflow.kotlinextensions.update<IntencionPedidoDbModel>()
                .set(IntencionPedidoDbModel_Table.Enviado eq true)
                .execute()
        }
    }

    fun guardarCantidadIntencionPedido(modelo: CantidadIntencionPedidoEntity): Completable {
        return doOnCompletable { modelo.save() }
    }

    fun obtenerCantidadIntencionPedido(llaveUa: LlaveUA): Single<String> {
        return doOnSingle {
            (select from CantidadIntencionPedidoEntity::class
                where (CantidadIntencionPedidoEntity_Table.Region.withTable() eq llaveUa.codigoRegion.guionSiNull())
                and (CantidadIntencionPedidoEntity_Table.Zona.withTable() eq llaveUa.codigoZona.guionSiNull())
                and (CantidadIntencionPedidoEntity_Table.Seccion.withTable() eq llaveUa.codigoSeccion.guionSiNull()))
                .querySingle()?.cantidad ?: Constant.CERO.toString()
        }
    }
}
