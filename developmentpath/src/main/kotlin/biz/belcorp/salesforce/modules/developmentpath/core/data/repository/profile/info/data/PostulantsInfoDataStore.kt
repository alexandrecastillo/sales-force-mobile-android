package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.data

import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity_Table
import com.raizlabs.android.dbflow.kotlinextensions.eq
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.where
import com.raizlabs.android.dbflow.sql.language.Select

class PostulantsInfoDataStore {

    fun getPostulant(id: Long): PostulanteEntity? {
        val where = (
            Select(
                PostulanteEntity_Table.SolicitudPostulanteID.withTable(),
                PostulanteEntity_Table.NumeroDocumento.withTable(),
                PostulanteEntity_Table.PrimerNombre.withTable(),
                PostulanteEntity_Table.SegundoNombre.withTable(),
                PostulanteEntity_Table.ApellidoPaterno.withTable(),
                PostulanteEntity_Table.ApellidoMaterno.withTable(),
                PostulanteEntity_Table.Telefono.withTable(),
                PostulanteEntity_Table.Celular.withTable(),
                PostulanteEntity_Table.CorreoElectronico.withTable(),
                PostulanteEntity_Table.Direccion.withTable(),
                PostulanteEntity_Table.Latitud.withTable(),
                PostulanteEntity_Table.Longitud.withTable(),
                PostulanteEntity_Table.EstadoPostulante.withTable(),
                PostulanteEntity_Table.FuenteIngreso.withTable(),
                PostulanteEntity_Table.FechaNacimiento.withTable()
            )
                from PostulanteEntity::class
                where (PostulanteEntity_Table.SolicitudPostulanteID.withTable() eq id.toInt())
            )
        return where.querySingle()
    }


}
