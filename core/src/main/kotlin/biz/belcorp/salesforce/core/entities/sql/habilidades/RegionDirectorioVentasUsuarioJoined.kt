package biz.belcorp.salesforce.core.entities.sql.habilidades

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class, allFields = false)
class RegionDirectorioVentasUsuarioJoined : BaseQueryModel() {

    @Column(name = "CodRegion")
    var codRegion: String? = null

    @Column(name = "CodZona")
    var codZona: String? = null

    @Column(name = "Estado")
    var estado: String? = null

    @Column(name = "PrimerApellido")
    var primerApellido: String? = null

    @Column(name = "PrimerNombre")
    var primerNombre: String? = null

    @Column(name = "Usuario")
    var usuario: String? = null

    @Column(name = "CodRol")
    var codRol: String? = null
}
