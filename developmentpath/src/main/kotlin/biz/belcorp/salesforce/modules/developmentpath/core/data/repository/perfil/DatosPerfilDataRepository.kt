package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.perfil

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.perfil.cloud.DatosPerfilCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.perfil.data.DatosPerfilLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.DatosPerfilRepository

class DatosPerfilDataRepository(
    private val database: DatosPerfilLocalDataStore,
    private val cloud: DatosPerfilCloudDataStore
) : DatosPerfilRepository {

    override suspend fun sync(person: PersonaRdd) {
        when (person) {
            is ConsultoraRdd -> syncConsultantInfo(person)
            else -> syncOtherPersonInfo(person)
        }
    }

    private suspend fun syncConsultantInfo(person: ConsultoraRdd) {
        val data = cloud.obtenerConsultora(person.id, requireNotNull(person.llaveUA.codigoRegion))
        database.actualizarEntidades(data.response ?: return)
    }

    private suspend fun syncOtherPersonInfo(person: PersonaRdd) {
        val data = cloud.obtenerOtrosRoles(person.id, person.rol)
        database.actualizarEntidades(data.response ?: return)
    }

}
