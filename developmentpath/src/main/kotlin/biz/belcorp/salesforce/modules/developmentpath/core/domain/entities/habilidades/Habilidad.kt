package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades

class Habilidad(
    val id: Long,
    val comentario: String?,
    val descripcion: String?,
    val tipoIcono: Int
) {

    val codigo = calcularCodigo()

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
