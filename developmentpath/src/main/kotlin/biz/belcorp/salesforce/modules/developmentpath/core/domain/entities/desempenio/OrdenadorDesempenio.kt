package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.desempenio

class OrdenadorDesempenio<T> {

    fun reordenar(lista: List<T>): List<T> {
        val cantidad = lista.size / 2
        val nuevaLista: MutableList<T> = mutableListOf()
        val primeros = lista.take(cantidad)
        val ultimos = lista.takeLast(lista.size - cantidad)
        for (i in 0 until cantidad) {
            nuevaLista.add(primeros[i])
            if (ultimos.size >= cantidad) {
                nuevaLista.add(ultimos[i])
            }
        }
        return nuevaLista
    }
}
