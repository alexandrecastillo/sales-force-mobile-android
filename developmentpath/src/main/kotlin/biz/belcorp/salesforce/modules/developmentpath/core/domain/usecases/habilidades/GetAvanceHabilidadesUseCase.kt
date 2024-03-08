package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.AvanceHabilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.avance.AvanceHabilidadesRepository

class GetAvanceHabilidadesUseCase(
    private val campaniasRepository: CampaniasRepository,
    private val avanceHabilidadesRepository: AvanceHabilidadesRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun getAvanceHabilidades(
        zona: String?,
        region: String,
        rol: Rol,
        subscriber: BaseSingleObserver<Pair<List<String>, List<AvanceHabilidad>>>
    ) {

        val llave = LlaveUA(
            codigoRegion = region,
            codigoZona = zona,
            codigoSeccion = null,
            consultoraId = null
        )

        val single = campaniasRepository
            .obtenerCampanias(llave)
            .map { campanias -> campanias.map { it.codigo } }
            .map { codigosCampania -> crearRequestAvance(zona, region, rol, codigosCampania) }
            .flatMap { request -> avanceHabilidadesRepository.getAvanceHabilidades(request) }

        execute(single, subscriber)
    }

    private fun crearRequestAvance(
        zona: String?,
        region: String,
        rol: Rol,
        codigosCampania: List<String>
    ) =

        AvanceHabilidadesRepository.RequestAvanceHabilidades(
            zona = zona,
            region = region,
            rol = rol,
            campanias = codigosCampania
        )
}
