package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.eventos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Alerta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.AlertasRepository

class AlertasDataRepository : AlertasRepository {
    override fun recuperar(): List<Alerta?> {
        return listOf(null,
            Alerta(valor = 0, unidad = Alerta.UnidadTiempoAlerta.NINGUNO),
            Alerta(valor = 5, unidad = Alerta.UnidadTiempoAlerta.MINUTOS),
            Alerta(valor = 15, unidad = Alerta.UnidadTiempoAlerta.MINUTOS),
            Alerta(valor = 30, unidad = Alerta.UnidadTiempoAlerta.MINUTOS),
            Alerta(valor = 1, unidad = Alerta.UnidadTiempoAlerta.HORAS),
            Alerta(valor = 1, unidad = Alerta.UnidadTiempoAlerta.DIAS)
        )
    }
}
