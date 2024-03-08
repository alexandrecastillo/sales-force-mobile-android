package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.kinesis

enum class KinesisTag(val nombre:String) {
    PANTALLA_HOME(Builder.HOME),
    PANTALLA_RDD(Builder.RDD),
    PANTALLA_EVENTO(Builder.EVENTO),
    EVENTO_FINALIZAR_VISITA(Builder.EVENTO_FINALIZAR_VISITA);
    class Builder {
        companion object {
            const val HOME = "HOME"
            const val RDD = "RUTA_DESARROLLO"
            const val EVENTO = "RUTA_DESARROLLO"
            const val EVENTO_FINALIZAR_VISITA = "REGISTRAR_VISITA"
        }
    }
}
