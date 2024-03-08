package biz.belcorp.salesforce.core.entities.sql.focos

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class, allFields = false)
class TituloDetalleFocoJoinned : BaseQueryModel() {

    @Column(name = "Nombre")
    var nombreTitulo: String? = null

    @Column(name = "Descripcion")
    var descripcionTitulo: String? = null

    @Column(name = "RutaImagen")
    var rutaImagen: String? = null

    @Column(name = "SegmentoID")
    var segmentoId: Int = 0

    @Column(name = "TituloPasoID")
    var tituloPasoId: Int = 0

    @Column(name = "DetalleNombre")
    var nombreDetalle: String? = null

    @Column(name = "DetalleDescripcion")
    var descripcionDetalle: String? = null

}
