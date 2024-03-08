package biz.belcorp.salesforce.core.entities.sql.ua


import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "SeccionesGZ")
class SeccionEntity : BaseModel() {

    @Column(name = "SeccionId")
    var seccionId: Int = 0

    @PrimaryKey
    @Column(name = "Codigo")
    var codigo: String? = null

    @Column(name = "Descripcion")
    var descripcion: String? = null

    @Column(name = "NombreCompleto")
    var sociaEmpresaria: String? = null

    @Column(name = "ConsultoraCodigo")
    var consultoraCodigo: String = Constant.EMPTY_STRING

    @PrimaryKey
    @Column(name = "Zona")
    var zona: String? = null

    @PrimaryKey
    @Column(name = "Region")
    var region: String? = null

}
