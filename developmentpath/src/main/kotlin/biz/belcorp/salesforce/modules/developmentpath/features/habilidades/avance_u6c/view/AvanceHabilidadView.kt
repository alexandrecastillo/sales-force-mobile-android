package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.view

import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.model.AvanceHabilidadModel

interface AvanceHabilidadView {
    fun pintarHabilidades(avance: List<AvanceHabilidadModel>)
    fun pintarCampanias(campanias: List<String>)
}
