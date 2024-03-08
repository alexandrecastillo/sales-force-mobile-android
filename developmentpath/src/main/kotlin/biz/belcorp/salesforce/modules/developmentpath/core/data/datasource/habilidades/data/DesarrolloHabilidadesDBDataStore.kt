package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.habilidades.DesarrolloHabilidadEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.DesarrolloHabilidadEntity_Table
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.core.utils.guionSiVacioONull
import com.raizlabs.android.dbflow.kotlinextensions.*
import io.reactivex.Single

class DesarrolloHabilidadesDBDataStore {

    fun getDesarrolloHabilidades(llaveUA: LlaveUA): Single<List<DesarrolloHabilidadEntity>> {
        return doOnSingle {
            (
                select from DesarrolloHabilidadEntity::class
                    where (DesarrolloHabilidadEntity_Table.Region eq llaveUA.codigoRegion.guionSiVacioONull()
                    and (DesarrolloHabilidadEntity_Table.Zona eq llaveUA.codigoZona.guionSiVacioONull())
                    and (DesarrolloHabilidadEntity_Table.Seccion eq llaveUA.codigoSeccion.guionSiVacioONull()))).list
        }
    }
}
