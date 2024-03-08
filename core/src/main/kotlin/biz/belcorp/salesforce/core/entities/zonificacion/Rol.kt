package biz.belcorp.salesforce.core.entities.zonificacion


enum class Rol(val codigoRol: String) {

    SOCIA_EMPRESARIA(Builder.COD_SOCIA_EMPRESARIA),
    GERENTE_ZONA(Builder.COD_GERENTE_ZONA),
    GERENTE_REGION(Builder.COD_GERENTE_REGION),
    DIRECTOR_VENTAS(Builder.COD_DIRECTOR_VENTAS),
    CONSULTORA(Builder.COD_CONSULTORA),
    POSIBLE_CONSULTORA(Builder.COD_POSIBLE_CONSULTORA),
    NINGUNO("");

    class Builder {
        companion object {
            const val COD_SOCIA_EMPRESARIA = "SE"
            const val COD_GERENTE_ZONA = "GZ"
            const val COD_GERENTE_REGION = "GR"
            const val COD_CONSULTORA = "CO"
            const val COD_POSIBLE_CONSULTORA = "PC"
            const val COD_DIRECTOR_VENTAS = "DV"

            fun construir(codigoRol: String?): Rol {
                return when (codigoRol) {
                    COD_SOCIA_EMPRESARIA -> SOCIA_EMPRESARIA
                    COD_GERENTE_ZONA -> GERENTE_ZONA
                    COD_GERENTE_REGION -> GERENTE_REGION
                    COD_DIRECTOR_VENTAS -> DIRECTOR_VENTAS
                    COD_CONSULTORA -> CONSULTORA
                    COD_POSIBLE_CONSULTORA -> POSIBLE_CONSULTORA
                    else -> NINGUNO
                }
            }
        }
    }

    fun uaBajoCargo(): String {
        return when (this) {
            SOCIA_EMPRESARIA -> "SECCIÓN"
            GERENTE_ZONA -> "ZONA"
            GERENTE_REGION -> "REGIÓN"
            DIRECTOR_VENTAS -> "PAÍS"
            else -> ""
        }
    }

    fun nameAsCode(): String {
        return when (this) {
            SOCIA_EMPRESARIA -> "Sección"
            GERENTE_ZONA -> "Zona"
            GERENTE_REGION -> "Región"
            DIRECTOR_VENTAS -> "País"
            else -> ""
        }
    }

    fun comoTexto(): String {
        return when (this) {
            GERENTE_REGION -> "Gerente de Región"
            GERENTE_ZONA -> "Gerente de Zona"
            SOCIA_EMPRESARIA -> "Socia Empresaria"
            CONSULTORA -> "Consultora"
            POSIBLE_CONSULTORA -> "Posible Consultora"
            DIRECTOR_VENTAS -> "Director de Ventas"
            else -> ""
        }
    }

    fun childRol(): Rol {
        return when (this) {
            DIRECTOR_VENTAS -> GERENTE_REGION
            GERENTE_REGION -> GERENTE_ZONA
            GERENTE_ZONA -> SOCIA_EMPRESARIA
            SOCIA_EMPRESARIA -> CONSULTORA
            else -> NINGUNO
        }
    }

    fun comoTextoTag(): String {
        return when (this) {
            GERENTE_REGION -> "GR"
            GERENTE_ZONA -> "GZ"
            SOCIA_EMPRESARIA -> "SE"
            CONSULTORA -> "Consultora"
            POSIBLE_CONSULTORA -> "Posible Consultora"
            else -> ""
        }
    }

    fun childAsText(): String {
        return when (this) {
            GERENTE_REGION -> "Gerentes"
            GERENTE_ZONA -> "Socias empresarias"
            SOCIA_EMPRESARIA -> "Consultoras"
            else -> ""
        }
    }

    fun id(): Int {
        return when (this) {
            DIRECTOR_VENTAS -> 7
            GERENTE_REGION -> 6
            GERENTE_ZONA -> 5
            SOCIA_EMPRESARIA -> 4
            CONSULTORA -> 3
            else -> 0
        }
    }

}
