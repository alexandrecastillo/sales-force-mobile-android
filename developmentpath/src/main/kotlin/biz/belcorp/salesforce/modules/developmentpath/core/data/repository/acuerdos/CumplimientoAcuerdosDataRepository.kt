package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.acuerdos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data.CumplimientoAcuerdosLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado.CumplimientoPrecalculado
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.CumplimientoAcuerdosRepository

class CumplimientoAcuerdosDataRepository(
    private val dataStore: CumplimientoAcuerdosLocalDataStore
) : CumplimientoAcuerdosRepository {

    override fun obtener(llaveUA: LlaveUA): List<CumplimientoPrecalculado> {
        return dataStore.obtener(llaveUA)
    }
}
