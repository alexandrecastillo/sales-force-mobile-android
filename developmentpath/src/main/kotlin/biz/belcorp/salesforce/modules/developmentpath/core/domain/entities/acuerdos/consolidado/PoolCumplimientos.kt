package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado

import biz.belcorp.salesforce.core.domain.entities.campania.Campania

class PoolCumplimientos(private val cumplimientos: List<CumplimientoPrecalculado>) {

    fun buscar(codigoCampania: String): CumplimientoPrecalculado {
        return filtrar(codigoCampania) ?: crearCumplimientoSinEstado(codigoCampania)
    }

    private fun filtrar(codigoCampania: String) =
        cumplimientos.firstOrNull { it.campania.codigo == codigoCampania }

    private fun crearCumplimientoSinEstado(codigoCampania: String): CumplimientoPrecalculado {
        val campania = Campania.construirDummy(codigoCampania)
        return CumplimientoPrecalculado(campania, CumplimientoCampania.Estado.NINGUNO)
    }
}
