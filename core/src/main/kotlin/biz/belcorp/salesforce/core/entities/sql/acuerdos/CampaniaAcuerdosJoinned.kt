package biz.belcorp.salesforce.core.entities.sql.acuerdos

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class, allFields = false)
class CampaniaAcuerdosJoinned : BaseQueryModel() {

    @Column(name = "IdLocal")
    var idLocal: Long = 0

    @Column(name = "ConsultoraID")
    var zona: String = ""

    @Column(name = "Campania")
    var campania: String = ""

    @Column(name = "NombreCorto")
    var nombreCorto: String = ""

    @Column(name = "Contenido")
    var contenido: String = ""

    @Column(name = "FechaRegistro")
    var fecha: String = ""
}
