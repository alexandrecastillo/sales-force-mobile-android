package biz.belcorp.salesforce.core.entities.sql.metodologia


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "ListadoMensajesSegmentacion")
class SegmentacionEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "Codigo")
    @SerializedName("codigo")
    var codigo: Int? = 0

    @Column(name = "ImagenMensaje")
    @SerializedName("imagenMensaje")
    var imagenMensaje: String? = null

    @Column(name = "TextoMensaje")
    @SerializedName("textoMensaje")
    var textoMensaje: String? = null

    @Column(name = "CodigoGrupoMensaje")
    @SerializedName("codigoGrupoMensaje")
    var codigoGrupoMensaje: Int? = 0

    @Column(name = "Nombre")
    @SerializedName("nombre")
    var nombre: String? = null

    @Column(name = "CampanaInicio")
    @SerializedName("campanaInicio")
    var campanaInicio: String? = null

    @Column(name = "CampanaFin")
    @SerializedName("campanaFin")
    var campanaFin: String? = null

}
