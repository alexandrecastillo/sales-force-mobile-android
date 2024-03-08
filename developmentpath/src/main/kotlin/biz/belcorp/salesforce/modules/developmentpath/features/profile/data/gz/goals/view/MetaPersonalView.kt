package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.view

import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.model.MetaPersonalModel

interface MetaPersonalView {

    fun mostrarMetas(list: List<MetaPersonalModel>): Unit?

    fun agregarMeta(modelo: MetaPersonalModel)

    fun eliminarMeta(modelo: MetaPersonalModel): Unit?

    fun habilitarNueva()

    fun deshabilitarNueva()

    fun pintarContador(cantidad: Int, limite: Int)

    fun ocultarCampoDeEdicion()

    fun mostrarCampoDeEdicion()

    fun mostrarMensaje(mensaje: String)
}
