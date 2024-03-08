package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.UsuarioMadre

class ReconocimientoConMadre(
    val madreReconocida: UsuarioMadre,
    val reconocimiento: ReconocimientoASuperior
)
