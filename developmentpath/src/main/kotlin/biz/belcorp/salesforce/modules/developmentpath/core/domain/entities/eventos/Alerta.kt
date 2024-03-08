package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos

class Alerta(
    val valor: Int,
    val unidad: UnidadTiempoAlerta
) {

    enum class UnidadTiempoAlerta(val simbolo: String) {
        MINUTOS(Builder.COD_MINUTOS),
        HORAS(Builder.COD_HORAS),
        DIAS(Builder.COD_DIAS),
        SEMANAS(Builder.COD_SEMANAS),
        NINGUNO("");

        class Builder {

            companion object {
                const val COD_MINUTOS = "mm"
                const val COD_HORAS = "hh"
                const val COD_DIAS = "dd"
                const val COD_SEMANAS = "ww"

                fun construir(codigoUnidad: String?): UnidadTiempoAlerta {
                    return when (codigoUnidad) {
                        COD_MINUTOS -> MINUTOS
                        COD_HORAS -> HORAS
                        COD_DIAS -> DIAS
                        COD_SEMANAS -> SEMANAS
                        else -> NINGUNO
                    }
                }
            }
        }
    }
}
