package biz.belcorp.salesforce.modules.consultants.features.search.mappers

import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.modules.consultants.features.search.models.SeccionModel

class SeccionModelDataMapper {

    private fun transform(download: Seccion?): SeccionModel {
        if (download == null) {
            throw IllegalArgumentException("Cannot parseCampaniaActual a null value")
        }
        val downloadModel = SeccionModel()
        downloadModel.codigo = download.codigo

        return downloadModel
    }

    fun transform(usersCollection: Collection<Seccion>?): Collection<SeccionModel> {
        return if (usersCollection != null && !usersCollection.isEmpty()) {
            usersCollection.mapTo(ArrayList()) { transform(it) }
        } else {
            emptyList()
        }
    }

}
