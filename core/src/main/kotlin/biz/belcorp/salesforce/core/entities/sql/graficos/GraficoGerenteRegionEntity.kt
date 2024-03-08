package biz.belcorp.salesforce.core.entities.sql.graficos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel


@Table(database = AppDatabase::class, name = "GraficoGerenteRegion")
class GraficoGerenteRegionEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "Region")
    var region: String? = ""

    @PrimaryKey
    @Column(name = "Zona")
    var zona: String? = ""

    @PrimaryKey
    @Column(name = "Seccion")
    var seccion: String? = ""

    @Column(name = "Contenido")
    var contenido: String? = ""
}
