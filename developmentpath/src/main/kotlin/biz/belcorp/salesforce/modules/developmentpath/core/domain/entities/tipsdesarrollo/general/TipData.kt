package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general

class TipData(val id: Int,
              val titulo: String,
              val imagen: TipoImagen,
              val tipo: Tipo,
              val orden: Int,
              val opciones: List<String>,
              val detalles: List<TipDataDetalle> = emptyList()) {

    enum class TipoImagen(val valor: String) {
        OTHER("-1"),
        VENTAGANANCIA("0"),
        DIGITAL("1"),
        CONCURSOS("2"),
        NOVEDADES("3"),
        PROGRAMANUEVAS("4"),
        CAMINOBRILLANTE("5"),
        TIPSESTABLECIDAS("6")
    }

    enum class Tipo(val valor: Int) {
        NINGUNO(-1),
        ITEM(0),
        ETIQUETA(1),
        PARRAFO(2)
    }
}
