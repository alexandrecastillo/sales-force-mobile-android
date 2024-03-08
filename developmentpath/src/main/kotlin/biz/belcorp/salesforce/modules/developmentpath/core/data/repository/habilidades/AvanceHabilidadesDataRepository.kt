package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.habilidades

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data.AvanceHabilidadesDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data.HabilidadesDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.AvanceHabilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.avance.AvanceHabilidadesRepository
import io.reactivex.Single

class AvanceHabilidadesDataRepository(private val avanceHabilidadesDataStore: AvanceHabilidadesDBDataStore,
                                      private val habilidadesDataStore: HabilidadesDBDataStore
)
    : AvanceHabilidadesRepository {

    override fun getAvanceHabilidades(request: AvanceHabilidadesRepository.RequestAvanceHabilidades)
        : Single<Pair<List<String>, List<AvanceHabilidad>>> {

        return Single.create {

            require(request.campanias.size >= 7) { "Campañas insuficientes" }

            val campaniasPrevias = request.campanias.sortedByDescending { it }.takeLast(6)

            val habilidades = habilidadesDataStore.obtener(request.rol)

            val habilidadesCampania = campaniasPrevias.map {
                avanceHabilidadesDataStore.getAvanceHabilidades(request.zona, request.region, it)
            }

            val avanceHabilidades = habilidades.map { habilidad ->
                AvanceHabilidad(
                    iconoId = habilidad.tipoIcono,
                    titulo = habilidad.descripcion ?: Constant.EMPTY_STRING,
                    habilidadId = habilidad.id,
                    cumplimientoCampaniaX = cumplimientoCampania(habilidadesCampania, campaniasPrevias[0], habilidad.id),
                    cumplimientoCampaniaXMenos1 = cumplimientoCampania(habilidadesCampania, campaniasPrevias[1], habilidad.id),
                    cumplimientoCampaniaXMenos2 = cumplimientoCampania(habilidadesCampania, campaniasPrevias[2], habilidad.id),
                    cumplimientoCampaniaXMenos3 = cumplimientoCampania(habilidadesCampania, campaniasPrevias[3], habilidad.id),
                    cumplimientoCampaniaXMenos4 = cumplimientoCampania(habilidadesCampania, campaniasPrevias[4], habilidad.id),
                    cumplimientoCampaniaXMenos5 = cumplimientoCampania(habilidadesCampania, campaniasPrevias[5], habilidad.id)
                )
            }

            it.onSuccess(Pair(campaniasPrevias, avanceHabilidades))
        }

    }

    private fun cumplimientoCampania(habilidadesCampania: List<Pair<String, Array<Long>>>, campania: String, habilidadId: Long): Boolean {
        return habilidadesCampania.any { it.first == campania && it.second.contains(habilidadId) }
    }

}
