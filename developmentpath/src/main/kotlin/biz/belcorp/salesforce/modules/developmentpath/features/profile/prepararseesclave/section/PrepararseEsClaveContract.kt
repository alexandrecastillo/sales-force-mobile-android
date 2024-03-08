package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

interface PrepararseEsClaveContract {
    interface View {
        fun mostrarData(data: List<PrepararseEsClaveModel>)
        fun mostrartDescripcion(descripcion: String)
    }

    interface Presenter {
        fun pintarDescripcionPorRol(rol: Rol)
        fun obtener(personaId: Long, rol: Rol)
    }
}
