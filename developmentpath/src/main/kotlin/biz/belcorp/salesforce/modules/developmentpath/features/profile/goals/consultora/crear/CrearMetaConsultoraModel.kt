package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.crear

data class CrearMetaConsultoraModel(
    var tiposMeta: List<TipoMetaModel>,
    var indiceTipoMetaSeleccionado: Int,
    var campaniasInicio: List<String>,
    var indiceCampaniaInicioSeleccionada: Int,
    var campaniasFin: List<String>,
    var indiceCampaniaFinSeleccionada: Int,
    var monto: String,
    var comentario: String
) {

    val descripcionesTiposMeta get() = tiposMeta.map { it.descripcion }
    val tipoMetaSeleccionado get() = tiposMeta[indiceTipoMetaSeleccionado]
    val campaniaInicioSeleccionada get() = campaniasInicio[indiceCampaniaInicioSeleccionada]
    val campaniaFinSeleccionada get() = campaniasFin[indiceCampaniaFinSeleccionada]
    val validez get() = validar()
    val esValido get() = validez.esValido

    data class Validez(
        val esValido: Boolean,
        val errores: List<TipoError>
    )

    enum class TipoError {
        TIPO_META_NO_SELECCIONADO,
        CAMPANIA_INICIO_NO_SELECCIONADO,
        CAMPANIA_FIN_NO_SELECCIONADO,
        MONTO_INVALIDO
    }

    private fun validar(): Validez {
        var esValido = true
        val errores = mutableListOf<TipoError>()

        if (monto.isBlank()) {
            esValido = false
            errores.add(TipoError.MONTO_INVALIDO)
        }

        if (indiceTipoMetaSeleccionado < 0) {
            esValido = false
            errores.add(TipoError.TIPO_META_NO_SELECCIONADO)
        }

        if (indiceCampaniaInicioSeleccionada < 0) {
            esValido = false
            errores.add(TipoError.CAMPANIA_INICIO_NO_SELECCIONADO)
        }

        if (indiceCampaniaFinSeleccionada < 0) {
            esValido = false
            errores.add(TipoError.CAMPANIA_FIN_NO_SELECCIONADO)
        }

        return Validez(
            esValido = esValido,
            errores = errores
        )
    }

    companion object {
        fun inicializar() = CrearMetaConsultoraModel(
            tiposMeta = emptyList(),
            indiceTipoMetaSeleccionado = -1,
            campaniasInicio = emptyList(),
            indiceCampaniaInicioSeleccionada = -1,
            campaniasFin = emptyList(),
            indiceCampaniaFinSeleccionada = -1,
            monto = "",
            comentario = ""
        )
    }
}
