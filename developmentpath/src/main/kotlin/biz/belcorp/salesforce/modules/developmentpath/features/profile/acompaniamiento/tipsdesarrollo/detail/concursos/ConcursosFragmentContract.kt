package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

interface ConcursosFragmentContract {
    interface View {
        fun mostarConcursos(concursos: List<ConcursoViewModel>)
        fun mostarSinDatos()
        fun ocultarSinDatos()
    }

    interface Presenter {
        fun obtenerConcursos(personaId: Long, rol: Rol)
        fun obtenerConcursosPorTipo(personaId: Long, rol: Rol, tipo: String)
    }
}
