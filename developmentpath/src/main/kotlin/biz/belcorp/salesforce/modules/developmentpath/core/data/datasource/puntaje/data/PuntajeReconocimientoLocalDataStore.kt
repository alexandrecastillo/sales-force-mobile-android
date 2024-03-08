package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.puntaje.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.path.PuntajeReconocimientoEntity
import biz.belcorp.salesforce.core.entities.sql.path.PuntajeReconocimientoEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import com.raizlabs.android.dbflow.kotlinextensions.*

class PuntajeReconocimientoLocalDataStore {
    fun obtenerPuntaje(llaveUA: LlaveUA, campania: String?, rol: Rol): PuntajeReconocimientoEntity? {

        val where = (select from PuntajeReconocimientoEntity::class
            where (PuntajeReconocimientoEntity_Table.Region eq llaveUA.codigoRegion)
            and (PuntajeReconocimientoEntity_Table.Zona eq llaveUA.codigoZona)
            and (PuntajeReconocimientoEntity_Table.Seccion eq llaveUA.codigoSeccion)
            and (PuntajeReconocimientoEntity_Table.Campania eq campania)
            and (PuntajeReconocimientoEntity_Table.Rol eq rol.codigoRol))
        return where.querySingle()
    }

}
