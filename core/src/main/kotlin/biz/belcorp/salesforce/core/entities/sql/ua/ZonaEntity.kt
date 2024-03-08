package biz.belcorp.salesforce.core.entities.sql.ua


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "ZonasGR")
class ZonaEntity : BaseModel() {

    @Column(name = "Codigo")
    @PrimaryKey
    var codigo: String? = null

    @Column(name = "Region")
    var region: String? = null

    @Column(name = "GerenteZona")
    var gerenteZona: String? = null

}
