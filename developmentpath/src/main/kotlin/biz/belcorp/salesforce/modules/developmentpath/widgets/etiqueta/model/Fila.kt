package biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model

data class Fila(val grupos: List<Grupo>) {

    lateinit var contenedor: ContenedorInfoBasica

    init {
        if (grupos.isEmpty()) throw FilaInvalida()

        grupos.forEach { it.filaMadre = this }
    }

    val cantidadColumnas get() = grupos.size

    val esPrimeraFila get() = contenedor.posicionDeFila(this) == 0

    fun posicionDeGrupo(grupo: Grupo) = grupos.indexOf(grupo)

    class FilaInvalida : Exception()
}
