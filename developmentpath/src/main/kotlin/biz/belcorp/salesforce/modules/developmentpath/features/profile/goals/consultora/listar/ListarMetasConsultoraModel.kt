package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.listar

data class ListarMetasConsultoraModel(
    var metas: List<MetaConsultoraEnListaModel>,
    var sePuedenCrearMetas: Boolean
) {

    val existenMetas get() = metas.isNotEmpty()

    companion object {
        fun inicializar() = ListarMetasConsultoraModel(
            metas = emptyList(),
            sePuedenCrearMetas = true
        )
    }
}
