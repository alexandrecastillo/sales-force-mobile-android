package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.habilidades.asignar.DetalleHabilidadApiRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.habilidades.reconocer.HabilidadReconocidaApiRequest
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.asignar.HabilidadesAsignaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.reconocer.HabilidadesReconoceRepository
import io.reactivex.Completable

class HabilidadesCloudStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun guardarHabilidadesReconocidas(
        reconocidas: List<HabilidadesReconoceRepository.HabilidadReconocida>
    ): Completable {
        val request = reconocidas.map {
            HabilidadReconocidaApiRequest(
                codigoZona = it.codigoZona,
                campania = it.campania,
                codigoRegion = it.codigoRegion,
                codigoSeccion = it.codigoSeccion,
                usuarioReconocida = it.usuarioReconocida,
                usuarioReconoce = it.usuarioReconoce,
                habilidades = it.habilidades
            )
        }

        return apiCallHelper.safeSingleApiCall {
            syncRestApi.habilidadesReconocidas(request)
        }.toCompletable()
    }

    fun guardarHabilidadesAsignadas(
        asignadas: List<HabilidadesAsignaRepository.DetalleHabilidad>
    ): Completable {
        val request = asignadas.map {
            DetalleHabilidadApiRequest(
                habilidades = it.habilidades,
                campania = it.campania,
                zona = it.codigoZona,
                region = it.codigoRegion,
                usuario = it.usuario,
                seccion = it.codigoSeccion
            )
        }

        return apiCallHelper.safeSingleApiCall {
            syncRestApi.habilidadesAsignadas(request)
        }.toCompletable()
    }

}
