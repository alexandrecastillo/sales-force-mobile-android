package biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model

data class ContenedorInfoBasica(val filas: List<Fila>) {
    val grupos = filas.flatMap { it.grupos }

    init {
        filas.forEach { it.contenedor = this }
    }

    fun posicionDeFila(fila: Fila) = filas.indexOf(fila)

    val cantidadColumnasPorFila get() = filas.map { it.cantidadColumnas }

    fun grupoEnPosicionLinear(posicion: Int): Grupo {
        return filas.flatMap { it.grupos }[posicion]
    }
}
