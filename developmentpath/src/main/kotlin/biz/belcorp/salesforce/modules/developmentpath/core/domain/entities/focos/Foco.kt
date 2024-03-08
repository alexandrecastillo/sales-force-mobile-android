package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos

class Foco(val id: Long, val descripcion: String?) {
    val focoCodigo = calcularCodigo()

    private fun calcularCodigo(): String {
        var name = ""
        if (descripcion == null) return name
        name = descripcion.replace(" ", "")
        if (descripcion.length > 3) {
            name = name.substring(0, 3).toUpperCase()
        }
        return name
    }
}

