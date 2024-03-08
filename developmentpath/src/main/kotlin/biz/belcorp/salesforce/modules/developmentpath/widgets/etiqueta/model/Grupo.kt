package biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model

data class Grupo(
    val titulo: Etiqueta = crearEtiquetaVacia(),
    val subtitulo: Etiqueta = crearEtiquetaVacia(),
    val contenido: List<Etiqueta> = emptyList(),
    val placeholderContenidovacio: Etiqueta = crearEtiquetaVacia()
) {

    lateinit var filaMadre: Fila

    val perteneceAPrimeraFila get() = filaMadre.esPrimeraFila
    val posicionEnFila get() = filaMadre.posicionDeGrupo(this)
    val enInicioDeFila get() = posicionEnFila == 0
    val enFinDeFila get() = posicionEnFila == filaMadre.cantidadColumnas - 1
    val esUnicoElemento get() = filaMadre.cantidadColumnas == 1
    val estaRodeado
        get() = filaMadre.cantidadColumnas >= 3 &&
            !enInicioDeFila &&
            !enFinDeFila

    companion object {
        fun crearGrupoVacioPoDefecto() = Grupo(
            titulo = Etiqueta(Etiqueta.Tipo.LIGERO),
            subtitulo = Etiqueta(Etiqueta.Tipo.FUERTE)
        )

        fun crearEtiquetaVacia() = Etiqueta(Etiqueta.Tipo.FUERTE, "")
    }
}
