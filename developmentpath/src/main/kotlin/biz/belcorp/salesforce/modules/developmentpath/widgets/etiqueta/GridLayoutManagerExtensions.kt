package biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica

object GridManagerBuilder {
    fun buildForContainer(context: Context?, contenedor: ContenedorInfoBasica): GridLayoutManager {
        return GridLayoutManager(context, lcm(contenedor.cantidadColumnasPorFila))
            .apply { recalcularSpan(contenedor) }
    }
}

fun GridLayoutManager.recalcularSpan(contenedor: ContenedorInfoBasica) {
    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

        override fun getSpanSize(position: Int): Int {
            return calcularColumnasPorPosicion(position)
        }

        private fun calcularColumnasPorPosicion(position: Int): Int {
            val grupo = contenedor.grupoEnPosicionLinear(position)
            return spanCount / grupo.filaMadre.cantidadColumnas
        }
    }
}

fun lcm(numbers: List<Int>): Int {
    if (numbers.isEmpty()) return 1
    var resultado = numbers[0]
    numbers.forEachIndexed { i, _ ->
        resultado = lcm(resultado, numbers[i])
    }
    return resultado
}

fun lcm(a: Int, b: Int): Int {
    return a * (b / gcd(a, b))
}

inline fun <T> Iterable<T>.multiplyBy(selector: (T) -> Int): Int {
    var result = 1
    for (element in this) {
        result *= selector(element)
    }
    return result
}

private fun gcd(a: Int, b: Int): Int {
    return if (b == 0) a
    else (gcd(b, a % b))
}
