package biz.belcorp.salesforce.core.entities.sql.eventos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "TipoEventoRdd", useBooleanGetterSetters = false)
class TipoEventoRddEntity(

    @Column(name = "Id")
    @PrimaryKey
    @SerializedName("tipoEventoId")
    var id: Long = 0,

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null,

    @Column(name = "CompartirObligatorio")
    @SerializedName("compartirObligatorio")
    var compartirObligatorio: Boolean = false,

    @Column(name = "Rol")
    @SerializedName("rol")
    var rol: String? = null,

    @Column(name = "AceptaDescripcionPersonalizada")
    @SerializedName("aceptaDescripcionPersonalizada")
    var aceptaDescripcionPersonalizada: Boolean = false
) : BaseModel()
