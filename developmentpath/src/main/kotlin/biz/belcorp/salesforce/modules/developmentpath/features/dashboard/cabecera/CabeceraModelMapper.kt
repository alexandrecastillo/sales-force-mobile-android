package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cabecera.CabeceraUseCase

class CabeceraModelMapper {

    fun map(response: CabeceraUseCase.Response): CabeceraViewModel {
        return CabeceraViewModel(
            periodoCampania = response.campaniaActual.periodo,
            nombreCortoCampania = response.campaniaActual.nombreCorto,
            iniciales = response.iniciales,
            planId = response.planId,
            rol = response.rol,
            titulo = response.titulo,
            esDuenia = response.esDuenia,
            sesion = response.sesion
        )
    }
}

