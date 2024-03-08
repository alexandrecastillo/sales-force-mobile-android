package biz.belcorp.salesforce.modules.postulants.core.data.repository.parametrounete

import biz.belcorp.salesforce.core.entities.sql.unete.ParametroUneteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.ParametroUneteEntity_Table
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.postulants.core.data.exceptions.DBDataStoreException
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.raizlabs.android.dbflow.sql.language.OrderBy
import com.raizlabs.android.dbflow.sql.language.Select
import com.raizlabs.android.dbflow.sql.language.property.Property
import io.reactivex.Single


class ParametroUneteDBStore constructor() {

    fun listSuspend(tipoParametro: Int): List<ParametroUneteEntity> {
        return Select().from(ParametroUneteEntity::class.java)
            .where(
                ParametroUneteEntity_Table.FK_IdTipoParametro.eq(tipoParametro),
                ParametroUneteEntity_Table.Estado.eq(Constant.STATUS_ENABLE)
            )
            .orderBy(OrderBy.fromProperty(ParametroUneteEntity_Table.Nombre).ascending())
            .queryList()
    }

    fun listSuspend(tipoParametro: Int, nombre: String): Single<List<ParametroUneteEntity>> {

        return Single.create {
            try {
                val entity = Select().from(ParametroUneteEntity::class.java)
                    .where(
                        ParametroUneteEntity_Table.Estado.eq(Constant.STATUS_ENABLE),
                        ParametroUneteEntity_Table.Nombre.like("%$nombre%"),
                        ParametroUneteEntity_Table.FK_IdTipoParametro.eq(tipoParametro)
                    )
                    .orderBy(OrderBy.fromProperty(ParametroUneteEntity_Table.Nombre).ascending())
                    .queryList()
                it.onSuccess(entity)

            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }

    }

    fun list(tipoParametro: Int, nombre: String): Single<ParametroUneteEntity> {

        return doOnSingle {
            requireNotNull(
                Select().from(ParametroUneteEntity::class.java)
                    .where(
                        ParametroUneteEntity_Table.FK_IdTipoParametro.eq(tipoParametro),
                        ParametroUneteEntity_Table.Nombre.eq(nombre),
                        ParametroUneteEntity_Table.Estado.eq(Constant.STATUS_ENABLE)
                    )
                    .orderBy(OrderBy.fromProperty(ParametroUneteEntity_Table.Nombre).ascending())
                    .querySingle()
            )
        }
    }

    fun listByPadre(parametroUnete: Int): Single<List<ParametroUneteEntity>> {
        return Single.create {
            try {
                val entity = Select().from(ParametroUneteEntity::class.java)
                    .where(
                        ParametroUneteEntity_Table.FK_IdParametroUnete.eq(parametroUnete),
                        ParametroUneteEntity_Table.Estado.eq(Constant.STATUS_ENABLE)
                    )
                    .orderBy(OrderBy.fromProperty(ParametroUneteEntity_Table.Nombre).ascending())
                    .queryList()
                it.onSuccess(entity)

            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }
    }

    fun list(tipoParametro: Int): Single<List<ParametroUneteEntity>> {
        return Single.create {
            try {
                val entity = Select().from(ParametroUneteEntity::class.java)
                    .where(
                        ParametroUneteEntity_Table.FK_IdTipoParametro.eq(tipoParametro),
                        ParametroUneteEntity_Table.Estado.eq(Constant.STATUS_ENABLE)
                    )
                    .orderBy(OrderBy.fromProperty(ParametroUneteEntity_Table.Nombre).ascending())
                    .queryList()
                it.onSuccess(entity)

            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }
    }

    fun list(tipoParametro: Int, orderBy: Property<String>): Single<List<ParametroUneteEntity>> {
        return Single.create {
            try {
                val entity = Select().from(ParametroUneteEntity::class.java)
                    .where(
                        ParametroUneteEntity_Table.FK_IdTipoParametro.eq(tipoParametro),
                        ParametroUneteEntity_Table.Estado.eq(Constant.STATUS_ENABLE)
                    )
                    .orderBy(OrderBy.fromProperty(orderBy).ascending())
                    .queryList()
                it.onSuccess(entity)

            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }
    }

    fun listZonasSMS(tipoParametro: Int, zona: String): Single<List<ParametroUneteEntity>> {
        return Single.create {
            try {
                val entity = Select().from(ParametroUneteEntity::class.java)
                    .where(
                        ParametroUneteEntity_Table.FK_IdTipoParametro.eq(tipoParametro),
                        ParametroUneteEntity_Table.Estado.eq("1"),
                        ParametroUneteEntity_Table.Nombre.like("%$zona%"),
                        ParametroUneteEntity_Table.FK_IdParametroUnete.isNot(0)
                    )
                    .orderBy(OrderBy.fromProperty(ParametroUneteEntity_Table.Nombre).ascending())
                    .queryList()
                it.onSuccess(entity)

            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }
    }

    fun getParametroUnete(tipoParametro: Int, zona: String): Single<List<ParametroUneteEntity>> {
        return Single.create {
            try {
                val entity = Select().from(ParametroUneteEntity::class.java)
                    .where(
                        ParametroUneteEntity_Table.FK_IdTipoParametro.eq(tipoParametro),
                        ParametroUneteEntity_Table.Estado.eq("1"),
                        ParametroUneteEntity_Table.Nombre.like("%$zona%")
                    )
                    .orderBy(OrderBy.fromProperty(ParametroUneteEntity_Table.Nombre).ascending())
                    .queryList()
                it.onSuccess(entity)

            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }
    }

    fun getParametroUnete(tipoParametro: Int): Single<List<ParametroUneteEntity>> {
        return Single.create {
            try {
                val entity = Select().from(ParametroUneteEntity::class.java)
                    .where(
                        ParametroUneteEntity_Table.FK_IdTipoParametro.eq(tipoParametro),
                        ParametroUneteEntity_Table.Estado.eq("1")
                    )
                    .orderBy(OrderBy.fromProperty(ParametroUneteEntity_Table.Nombre).ascending())
                    .queryList()
                it.onSuccess(entity)

            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }
    }


}
