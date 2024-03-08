package biz.belcorp.salesforce.modules.developmentpath.core.domain.utils

import java.util.*

open class Seleccionador<T>(
    elementos: Iterable<T> = emptyList(),
    private val limiteSeleccionados: Int = Int.MAX_VALUE
) {

    val seleccionables = elementos.map { Seleccionable(it) }

    val seleccionados = LinkedList(seleccionables.filter { it.seleccionado })

    val primerSeleccionado: Seleccionable<T>? get() = seleccionados.firstOrNull()

    fun invertirSeleccion(indice: Int) {
        val seleccionable = seleccionables[indice]

        if (seleccionable.seleccionado) {
            deseleccionar(indice)
        } else {
            seleccionar(indice)
        }
    }

    fun seleccionar(seleccionable: Seleccionable<T>) {
        seleccionable.seleccionar()
        seleccionados.add(seleccionable)

        if (seleccionados.size > limiteSeleccionados) {
            val antesSeleccionado = seleccionados.pop()
            antesSeleccionado.deseleccionar()
        }
    }

    fun seleccionar(indice: Int) {
        if (indice >= seleccionables.size) return
        seleccionar(seleccionables[indice])
    }

    fun seleccionarSolo(seleccionable: Seleccionable<T>) {
        seleccionables.forEach { it.deseleccionar() }

        seleccionable.seleccionar()

        seleccionados.clear()
        seleccionados.add(seleccionable)
    }

    fun seleccionarSoloPrimerElemento() {
        if (seleccionables.isEmpty()) return
        seleccionarSolo(0)
    }

    fun seleccionarSolo(indice: Int) {
        seleccionarSolo(seleccionables[indice])
    }

    private fun deseleccionar(indice: Int) {
        val nuevoDeseleccionado = seleccionables[indice]
        nuevoDeseleccionado.deseleccionar()
        seleccionados.remove(nuevoDeseleccionado)
    }

    fun habilitarSolo(indice: Int) {
        seleccionables.forEach { it.inhabilitar() }
        seleccionables[indice].habilitar()
    }
}

class Seleccionable<T>(
    val elemento: T,
    var seleccionado: Boolean = false,
    var habilitado: Boolean = true
) {

    fun seleccionar() {
        seleccionado = true
    }

    fun deseleccionar() {
        seleccionado = false
    }

    fun habilitar() {
        habilitado = true
    }

    fun inhabilitar() {
        habilitado = false
    }
}
