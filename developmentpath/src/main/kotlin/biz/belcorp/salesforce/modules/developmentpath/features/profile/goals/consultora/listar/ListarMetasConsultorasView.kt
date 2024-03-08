package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.listar

interface ListarMetasConsultorasView {
    fun pintarMetas(modelos: List<MetaConsultoraEnListaModel>)
    fun pintarMetasVacio()
    fun mostrarBotonAgregarMeta()
    fun ocultarBotonAgregarMeta()
}
