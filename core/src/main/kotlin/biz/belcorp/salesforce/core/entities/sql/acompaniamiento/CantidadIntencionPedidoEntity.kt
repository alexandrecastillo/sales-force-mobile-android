package biz.belcorp.salesforce.core.entities.sql.acompaniamiento


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "CantidadIntencionPedido")
class CantidadIntencionPedidoEntity : BaseModel() {

    @Column(name = "Region")
    @PrimaryKey
    var region: String? = null
    @Column(name = "Zona")
    @PrimaryKey
    var zona: String? = null
    @Column(name = "Seccion")
    @PrimaryKey
    var seccion: String? = null
    @Column(name = "Cantidad")
    var cantidad: String? = "0"
}
