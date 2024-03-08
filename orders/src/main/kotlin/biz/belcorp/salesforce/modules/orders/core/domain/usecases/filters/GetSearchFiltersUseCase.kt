package biz.belcorp.salesforce.modules.orders.core.domain.usecases.filters

import biz.belcorp.salesforce.base.utils.isSe
import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.orders.core.domain.entities.filters.FiltrosBusqueda
import biz.belcorp.salesforce.modules.orders.core.domain.repository.FiltersRepository


class GetSearchFiltersUseCase(
    private val filtersRepository: FiltersRepository,
    private val campaniasRepository: CampaniasRepository,
    private val sesionRepository: SessionRepository
) {

    suspend fun getFilters(): FiltrosBusqueda {

        val sesion = sesionRepository.getSession()

        val tipoEstadoList = filtersRepository.getTipoEstadoFilter()
        val tipoSaldoList = filtersRepository.getTipoSaldoFilter()
        val tipoSegmentoList = filtersRepository.getTipoSegmentoFilter()

        val tipoSeccionesList = mutableListOf<Seccion>()

        if (sesion?.rol?.isSe() == false) {
            tipoSeccionesList.addAll(filtersRepository.getSeccionesFilter())
        }

        val campanias = campaniasRepository.obtenerCampaniasSincrono(requireNotNull(sesion?.llaveUA))

        return FiltrosBusqueda(tipoEstadoList, tipoSaldoList, tipoSegmentoList, tipoSeccionesList, campanias)
    }

}
