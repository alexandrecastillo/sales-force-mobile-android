package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.acuerdos.CampaniaAcuerdosJoinned
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.datos.AcuerdoEntity
import biz.belcorp.salesforce.core.entities.sql.datos.AcuerdoEntity_Table
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class AcuerdoDBDataStore {

    fun obtenerAcuerdos(ua: LlaveUA): List<CampaniaAcuerdosJoinned> {
        val where = (Select(
            CampaniaUaEntity_Table.NombreCorto.withTable(),
            AcuerdoEntity_Table.Campania.withTable(),
            AcuerdoEntity_Table.Region.withTable(),
            AcuerdoEntity_Table.Zona.withTable(),
            AcuerdoEntity_Table.Seccion.withTable(),
            AcuerdoEntity_Table.ConsultoraID.withTable(),
            AcuerdoEntity_Table.IdLocal.withTable(),
            AcuerdoEntity_Table.Contenido.withTable(),
            AcuerdoEntity_Table.FechaRegistro.withTable()
        )

            from AcuerdoEntity::class
            leftOuterJoin CampaniaUaEntity::class
            on (CampaniaUaEntity_Table.Codigo.withTable()
            eq AcuerdoEntity_Table.Campania.withTable())
            where ((AcuerdoEntity_Table.Region.withTable()) eq (ua.codigoRegion ?: "-"))
            and (AcuerdoEntity_Table.Zona.withTable() eq (ua.codigoZona ?: "-"))
            and (AcuerdoEntity_Table.Seccion.withTable() eq (ua.codigoSeccion ?: "-"))
            and (AcuerdoEntity_Table.ConsultoraID.withTable() eq (ua.consultoraId ?: -1L))
            and (AcuerdoEntity_Table.Activo.withTable() eq 1)
            and ((CampaniaUaEntity_Table.Region.withTable()) eq (ua.codigoRegion ?: "-"))
            and (CampaniaUaEntity_Table.Zona.withTable() eq (ua.codigoZona ?: "-"))
            and (CampaniaUaEntity_Table.Seccion.withTable() eq (ua.codigoSeccion ?: "-")))

        return where.queryCustomList(CampaniaAcuerdosJoinned::class.java)
    }
}
