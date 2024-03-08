package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado

import biz.belcorp.salesforce.core.domain.entities.campania.Campania

class ProcesadorCumplimientosCampania(
    private val campanias: List<Campania>,
    private val poolCumplimientos: PoolCumplimientos,
    private val poolAcuerdos: PoolAcuerdos
) {

    fun procesar(): List<CumplimientoCampania> {
        return campanias.map { campania ->
            val precalculado = poolCumplimientos.buscar(campania.codigo)
            val acuerdos = poolAcuerdos.buscar(campania.codigo)
            CumplimientoCampania(campania, precalculado, acuerdos)
        }
    }
}
