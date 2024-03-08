package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data

import biz.belcorp.salesforce.core.entities.sql.focos.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.exceptions.DBDataStoreException
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.FocoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.CabeceraFoco
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Method
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Observable

class FocoDBStore(private val focoMapper: FocoMapper) : FocoDataStore {

    override fun hasData(segmentoId: Int): Observable<Boolean> {
        return Observable.create {
            try {
                val count = (Select(Method.count())
                    from TituloFocoCampaniaEntity::class
                    where (TituloFocoCampaniaEntity_Table.SegmentoID.withTable() eq segmentoId)).longValue()

                it.onNext(count > 0)
            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }
    }

    override fun obtener(segmentoId: Int): Observable<List<CabeceraFoco>> {

        return Observable.create {
            try {
                val where = (Select(
                    TituloFocoCampaniaEntity_Table.Nombre.withTable(),
                    TituloFocoCampaniaEntity_Table.Descripcion.withTable(),
                    TituloFocoCampaniaEntity_Table.RutaImagen.withTable(),
                    TituloFocoCampaniaEntity_Table.TituloPasoID.withTable(),
                    DetalleFocoCompaniaEntity_Table.Nombre.withTable().`as`("DetalleNombre"),
                    DetalleFocoCompaniaEntity_Table.Descripcion.withTable().`as`("DetalleDescripcion"))

                    from TituloFocoCampaniaEntity::class
                    leftOuterJoin DetalleFocoCompaniaEntity::class on
                    (TituloFocoCampaniaEntity_Table.TituloPasoID.withTable() eq
                        DetalleFocoCompaniaEntity_Table.TituloPasoID.withTable())
                    where (TituloFocoCampaniaEntity_Table.SegmentoID.withTable() eq segmentoId))

                it.onNext(focoMapper.parse(where.queryCustomList(TituloDetalleFocoJoinned::class.java)))
            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }
    }


}
