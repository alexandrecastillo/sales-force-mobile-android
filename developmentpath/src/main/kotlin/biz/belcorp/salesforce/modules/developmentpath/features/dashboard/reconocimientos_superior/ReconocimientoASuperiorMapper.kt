package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoASuperior
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.Reconocimientos
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.se.model.ListaReconocimientosModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.se.model.ReconocimientoModel

class ReconocimientoASuperiorMapper {

    fun parse(reconocimientos: Reconocimientos) = ListaReconocimientosModel(
            nombreReconocida = reconocimientos.madreReconocida.nombre
                    .trim()
                    .replace("\\s+".toRegex(), " "),
            codigoRolReconocida = reconocimientos.madreReconocida.rol.codigoRol,
            codigoUnidadAdministrativa = reconocimientos.madreReconocida.codigoUA,
            reconocimientos = reconocimientos.reconocimientos.map { parse(it) })

    private fun parse(reconocimiento: ReconocimientoASuperior) = ReconocimientoModel(
            idReconocimiento = reconocimiento.idReconocimiento,
            estaReconocida = !reconocimiento.pendienteReconocimiento,
            campania = reconocimiento.campania,
            valoracion = reconocimiento.valoracion,
            nombreReconocida = reconocimiento.nombreReconocida)
}
