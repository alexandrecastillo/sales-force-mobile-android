package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.UsuarioMadre

class Reconocimientos(
    val madreReconocida: UsuarioMadre,
    val reconocimientos: List<ReconocimientoASuperior>
)
