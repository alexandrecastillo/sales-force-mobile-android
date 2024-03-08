package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model

class CalendarioViewModel(val dias: List<DiaCalendarioViewModel>) {
    val seleccionado: Int
        get() {
            return dias.indexOfFirst { it.seleccionado }
        }
}

class DiaCalendarioViewModel(val dia: String,
                             val seleccionado: Boolean = false,
                             val tipoTexto: TipoTexto,
                             val tipoPunto: TipoPunto,
                             val tipoFondo: TipoFondo,
                             val tipoSeleccion: TipoSeleccion) {

    enum class TipoTexto { HABILITADO, SELECCIONADO, DESHABILITADO }

    enum class TipoPunto { COLOR, BLANCO, NINGUNO }

    enum class TipoFondo {
        CIRCULO_RECTANGULO_IZQUIERDA,
        CIRCULO_RECTANGULO_DERECHA,
        RECTANGULO_REDONDEADO_IZQUIERDA,
        RECTANGULO_REDONDEADO_DERECHA,
        RECTANGULO,
        NINGUNO
    }

    enum class TipoSeleccion {
        CIRCULO,
        CIRCUNFERENCIA,
        NINGUNO
    }
}
