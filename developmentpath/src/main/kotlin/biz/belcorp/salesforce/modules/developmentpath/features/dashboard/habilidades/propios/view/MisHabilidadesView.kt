package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.view

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.model.MiHabilidadModel

interface MisHabilidadesView {

    fun permitirAsignacion()

    fun pintarHabilidades(habilidades: List<MiHabilidadModel>)
}
