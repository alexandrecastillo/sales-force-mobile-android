package biz.belcorp.salesforce.core.entities.sql.directorio


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "UsuarioPadreSesion")
class UsuarioPadreSesionEntity : BaseModel() {

    @Column(name = "Codigo")
    @SerializedName("codigo")
    var codigo: String? = null

    @Column(name = "NombrePadre")
    @SerializedName("padre")
    var nombre: String? = null

    @PrimaryKey
    @Column(name = "EmailPadre")
    @SerializedName("emailPadre")
    var email: String? = null

    @Column(name = "RolPadre")
    @SerializedName("codigoRol")
    var rol: String? = null

}
