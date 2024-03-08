package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.crear

interface CrearMetaConsultoraView {
    fun mostrarTipoMetas(tipoMetas: List<String>)
    fun mostrarListaCampaniasInicio(campanias: List<String>)
    fun mostrarListaCampaniasFin(campanias: List<String>)
    fun cerrar()
    fun comunicarExito()
    fun habilitarBotonGuardar()
    fun deshabilitarBotonGuardar()
}
