package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

interface IngresosExtraContract {
    interface View {
        fun mostrarData(data: List<OtraMarcaModel>)
        fun mostrarDataNoFront(data: List<OtraMarcaModel>)
        fun updateSelectedCategory(selectedCategory: String?)
    }

    interface Presenter {
        fun obtener(personaId: Long, personaRol: Rol)

        fun obtenerMarcasNofront(personaId: Long, personaRol: Rol)

        fun check(item: OtraMarcaModel)
    }
}
