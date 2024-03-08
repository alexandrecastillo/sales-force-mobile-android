package biz.belcorp.salesforce.modules.postulants.core.domain.enums

enum class UneteListado(val tipo: Int, val nombre: String) {

    TODOS(4, "TODOS"),
    EVALUACION(0, "En Evaluación"),
    PRE_APROBADOS(1, "Pre-aprobadas"),
    APROBADOS(2, "Aprobadas"),
    RECHAZADOS(3, "Rechazadas"),
    INGRESOS_ANTICIPADOS(11, "Ing. Anticipados"),
    PRE_INSCRITAS(13, "Pre-inscritas"),
    REGULARIZAR_DOCUMENTO(15, "Regularizar Documento"),
    PROACTIVO_POR_FINALIZAR(17, "Proactivos por finalizar"),
    PROACTIVO_FINALIZADO(18, "Proactivos finalizados"),
    PROACTIVO_PRE_APROBADOS(19, "Proactivos pre-aprobados");

    companion object {
        fun find(tipo: Int): UneteListado? {
            values().forEach {
                if (it.tipo == tipo)
                    return it
            }
            return null
        }
    }
}
