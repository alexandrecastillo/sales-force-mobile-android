package biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.data

import biz.belcorp.salesforce.core.entities.sql.calculator.CalculatingResultEntity
import biz.belcorp.salesforce.core.entities.sql.calculator.CalculatingResultEntity_Table
import biz.belcorp.salesforce.core.utils.deleteAll
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select
import com.raizlabs.android.dbflow.sql.language.Update
import io.reactivex.Single

class CalculatingResultDBDataStore {

    fun all(
        codRegion: String?,
        codZona: String?,
        codSeccion: String?
    ): Single<List<CalculatingResultEntity>> {

        return doOnSingle {
            Select().from(CalculatingResultEntity::class.java)
                .where(
                    CalculatingResultEntity_Table.CodRegion.eq(codRegion)
                        .and(CalculatingResultEntity_Table.CodZona.eq(codZona))
                        .and(CalculatingResultEntity_Table.CodSeccion.eq(codSeccion))
                )
                .queryList()

        }
    }

    fun getCalculatingResult(
        codRegion: String?,
        codZona: String?,
        codSeccion: String?
    ): List<CalculatingResultEntity> {

        return requireNotNull(
            Select().from(CalculatingResultEntity::class.java)
                .where(
                    CalculatingResultEntity_Table.CodRegion.eq(codRegion)
                        .and(CalculatingResultEntity_Table.CodZona.eq(codZona))
                        .and(CalculatingResultEntity_Table.CodSeccion.eq(codSeccion))
                )
                .queryList()
        )
    }


    fun insert(entity: CalculatingResultEntity) {
        entity.deleteAndInsert()
    }

    fun delete() {
        CalculatingResultEntity().deleteAll()
    }

    fun getPending(): List<CalculatingResultEntity> {
        return (select
            from CalculatingResultEntity::class
            where (CalculatingResultEntity_Table.CodResultado.isNull)
            or (CalculatingResultEntity_Table.CodResultado eq 0)).queryList()
    }

    fun checkDispatched(codResultado: Int) {
        val query = Update(CalculatingResultEntity::class.java)
            .set(CalculatingResultEntity_Table.CodResultado eq codResultado)
            .where(CalculatingResultEntity_Table.CodResultado.isNull) or (CalculatingResultEntity_Table.CodResultado eq 0)
        query.execute()
    }

}
