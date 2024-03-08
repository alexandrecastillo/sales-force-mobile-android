package biz.belcorp.salesforce.modules.postulants.features.indicador.mappers

import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.modules.postulants.features.indicador.entities.SeccionModel
import java.util.*

class SeccionModelDataMapper {

    fun transformList(usersCollection: Collection<Seccion>?): List<SeccionModel> {
        return if (usersCollection != null && !usersCollection.isEmpty()) {
            usersCollection.mapTo(ArrayList()) { transform(it) }
        } else {
            emptyList()
        }
    }

    private fun transform(download: Seccion?): SeccionModel {
        requireNotNull(download) { "Cannot parseCampaniaActual a null value" }
        val downloadModel = SeccionModel()

        downloadModel.seccionId = download.seccionId
        downloadModel.sociaEmpresaria = download.sociaEmpresaria
        downloadModel.descripcion = download.descripcion
        downloadModel.codigo = download.codigo

        return downloadModel
    }

}
