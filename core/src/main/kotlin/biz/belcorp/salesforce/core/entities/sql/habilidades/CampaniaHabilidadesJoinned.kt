package biz.belcorp.salesforce.core.entities.sql.habilidades

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class, allFields = false)
class CampaniaHabilidadesJoinned : BaseQueryModel() {

    @Column(name = "Campania")
    var campania: String = ""

    @Column(name = "Region")
    var region: String = ""

    @Column(name = "Codigo")
    var zona: String = ""

    @Column(name = "Seccion")
    var seccion: String = ""

    @Column(name = "Habilidades")
    var habilidades: String? = null

}
