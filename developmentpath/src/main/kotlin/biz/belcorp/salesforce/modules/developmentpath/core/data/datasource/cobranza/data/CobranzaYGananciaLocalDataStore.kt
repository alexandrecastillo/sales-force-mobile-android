package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.cobranza.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.datos.CobranzaYGananciaEntity
import biz.belcorp.salesforce.core.entities.sql.datos.CobranzaYGananciaEntity_Table
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.kotlinextensions.*
import io.reactivex.Single

class CobranzaYGananciaLocalDataStore {

    fun obtenerDbCobranza(llave: LlaveUA): Single<CobranzaYGananciaEntity> {
        val where = (select from CobranzaYGananciaEntity::class
            where (CobranzaYGananciaEntity_Table.Region.withTable() eq llave.codigoRegion)
            and (CobranzaYGananciaEntity_Table.Zona.withTable() eq llave.codigoZona)
            and (CobranzaYGananciaEntity_Table.Seccion.withTable() eq llave.codigoSeccion))
        return Single.just(where.querySingle())
    }

    fun actualizarDb(cobranzaYGanancia: CobranzaYGananciaEntity): Single<CobranzaYGananciaEntity> {
        return doOnSingle {
            cobranzaYGanancia.update()
            cobranzaYGanancia
        }
    }
}
