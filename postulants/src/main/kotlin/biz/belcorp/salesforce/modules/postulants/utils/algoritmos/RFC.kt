package biz.belcorp.salesforce.modules.postulants.utils.algoritmos

import biz.belcorp.salesforce.core.utils.removeExtraWhiteSpaces

class RFC
private constructor(
    private var nombres: String, private var apellidoPaterno: String,
    private var apellidoMaterno: String, private var diaNacimiento: String,
    private var mesNacimiento: String, private var annioNacimiento: String
) {

    companion object {
        private const val caracterEnie = "ñ"
        private const val caracterEquis = "X"
        private const val vocalesSinTilde = "aeiou"
    }

    private fun generate(): String {
        filtrarCaracteres()
        return armarLetras() + armarNumeros()
    }

    private fun filtrarCaracteres() {
        nombres = filtrarNoPermitidos(nombres)
        apellidoPaterno = filtrarNoPermitidos(apellidoPaterno)
        apellidoMaterno = filtrarNoPermitidos(apellidoMaterno)
    }

    private fun filtrarNoPermitidos(texto: String): String {
        return texto.reemplazarTildes().reemplazarCompuestos().reemplazarEspeciales()
    }

    private fun String.reemplazarTildes(): String {

        val vocalesConTilde = "áéíóú"

        var textoResultado = this.toLowerCase()

        for (i in vocalesConTilde.indices)
            textoResultado = textoResultado.replace(vocalesConTilde[i], vocalesSinTilde[i])

        return textoResultado
    }

    private fun String.reemplazarCompuestos(): String {

        val excepciones = listOf("MARIA", "MA.", "MA", "JOSE", "J", "J.")
        val preposicionesCompuestas = listOf("DE LA", "DE LAS", "DE LO", "DE LOS")
        val preposiciones = listOf(
            "DA", "DAS", "DE", "DEL", "DER", "DI", "DIE", "DD", "EL",
            "LA", "LOS", "LAS", "LE", "LES", "MAC", "MC", "VAN", "VON", "Y"
        )
        val caracteresReemplazables = excepciones union preposicionesCompuestas union preposiciones
        val caracterEspacio = " "

        var nombreTemp = caracterEspacio + this.toUpperCase().removeExtraWhiteSpaces().trim()
        caracteresReemplazables.forEach {
            nombreTemp = nombreTemp.replace(" $it ", caracterEspacio)
        }

        return nombreTemp.trim()
    }

    private fun String.reemplazarEspeciales(): String {
        val regx = "[/.@-]"
        return this.replace(regx.toRegex(), caracterEquis)
    }

    private fun reemplazarAltisonante(texto: String): String {

        val textoSinTildes = texto.reemplazarTildes()
        val altisonantes = arrayOf(
            "BATO", "BOFE", "BUEI", "BUEY", "CACA", "CACO",
            "CAGA", "CAGO", "CAKA", "CAKO", "COGE", "COJA", "COJE", "COJI", "COJO",
            "CULO", "FETO", "FOCA", "GATA", "GUEI", "GUEY", "JOTO", "KACA", "KACO",
            "KAGA", "KAGO", "KOGE", "KOJO", "KAKA", "KULO", "LOBA", "LOCA", "LOCO",
            "LOKA", "LOKO", "LORA", "LORO", "MAME", "MAMO", "MEAR", "MEAS", "MEON",
            "MION", "MOCO", "MULA", "PEDA", "PEDO", "PENE", "PUTA", "PUTO", "QULO",
            "RATA", "ROBA", "ROBE", "ROBO", "RUIN", "SAPO", "VACA", "VAGA", "VAGO"
        )

        if (!altisonantes.find { it == textoSinTildes.toUpperCase() }.isNullOrEmpty()) {
            val ultimaPosicion = texto.length - 1
            return texto.replaceRange(ultimaPosicion, texto.length, caracterEquis)
        }

        return texto
    }


    private fun armarLetras(): String {
        val letras = primeraLetra() + segundaLetra() + terceraLetra() + cuartaLetra()
        return reemplazarAltisonante(letras).toUpperCase()
    }

    private fun armarNumeros(): String {
        return annioNacimiento.substring(2, 4) + mesNacimiento + diaNacimiento
    }

    private fun primeraLetra(): String {
        return if (apellidoPaterno.substring(0, 1).toLowerCase() == caracterEnie)
            caracterEquis
        else
            apellidoPaterno.substring(0, 1)
    }

    private fun segundaLetra(): String {

        val subCadenaBusqueda = apellidoPaterno.toLowerCase().substring(1, apellidoPaterno.length)

        subCadenaBusqueda.forEach {
            if (esVocal(it)) {
                return Character.toString(it).toUpperCase()
            }
        }

        return caracterEquis.toUpperCase()
    }

    private fun terceraLetra(): String {
        return when {
            apellidoMaterno.isBlank() -> caracterEquis
            apellidoMaterno.substring(0, 1).toLowerCase() == caracterEnie -> caracterEquis
            else -> apellidoMaterno.substring(0, 1)
        }
    }

    private fun cuartaLetra(): String {
        return nombres.substring(0, 1)
    }

    private fun esVocal(caracter: Char): Boolean {
        return vocalesSinTilde.contains(caracter)
    }

    class Builder {

        private var nombres = ""
        private var apellidoPaterno = ""
        private var apellidoMaterno = ""
        private var diaNacimiento = ""
        private var mesNacimiento = ""
        private var annioNacimiento = ""

        fun nombres(nombres: String): Builder {
            this.nombres = nombres.trim()
            return this
        }

        fun apellidoPaterno(apellidoPaterno: String): Builder {
            this.apellidoPaterno = apellidoPaterno.trim()
            return this
        }

        fun apellidoMaterno(apellidoMaterno: String): Builder {
            this.apellidoMaterno = apellidoMaterno.trim()
            return this
        }

        fun fechaNacimiento(dia: String, mes: String, annio: String): Builder {
            this.diaNacimiento = formatearDigitos(dia.trim())
            this.mesNacimiento = formatearDigitos(mes.trim())
            this.annioNacimiento = annio.trim()
            return this
        }

        private fun formatearDigitos(digito: String): String {
            return if (digito.length < 2) "0$digito" else digito
        }

        fun build(): String {
            return RFC(
                nombres, apellidoPaterno, apellidoMaterno,
                diaNacimiento, mesNacimiento, annioNacimiento
            ).generate()
        }
    }
}

