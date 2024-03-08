package biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model

import androidx.annotation.StringRes

class Etiqueta(
    var tipo: Tipo,
    valor: String = "",
    @StringRes var idRecurso: Int = -1,
    @StringRes var idRecursoVacio: Int = -1
) {

    val recursoValido get() = idRecurso > -1

    val recursoVacioValido get() = valor.isBlank() && idRecursoVacio > -1

    val seMuestraAlgo get() = (valor.isNotBlank() || recursoValido || recursoVacioValido)

    var valor: String = valor
        set(value) {
            if (recursoValido) idRecurso = -1
            field = value
        }

    enum class Tipo { LIGERO, FUERTE, NEGATIVO }

    companion object {
        fun inicializarEnBlanco(): Etiqueta {
            return Etiqueta(Tipo.LIGERO)
        }
    }
}
