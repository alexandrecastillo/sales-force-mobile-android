package biz.belcorp.salesforce.modules.postulants.utils.algoritmos

import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class UneteAlgorithms {
    companion object {

        private const val DIRECCION_SEPARATOR = "|"

        fun buildFuenteIngreso(rol: String): String {
            return UneteFuenteIngreso.PREF.value + rol.trim().toUpperCase()
        }

        fun isAprobarEnabled(pais: String, paso: Int, rol: String): Boolean {

            if (pais == Pais.MEXICO.codigoIso) {
                return false
            }

            val config = UneteConfig.find(pais)

            if (paso == config?.pasos && (rol.trim()
                    .toUpperCase() != Rol.SOCIA_EMPRESARIA.codigoRol)
            )
                return true

            return false
        }

        fun isValidarBuroEnabled(validarBuro: Boolean, estadoBuro: String): Boolean {
            return validarBuro && estadoBuro == UneteEstadoCrediticio.SIN_CONSULTAR.value.toString()
        }

        fun createDireccion(lst: List<String>): String {
            var direccion = ""

            lst.forEach {
                direccion += it
                direccion += DIRECCION_SEPARATOR
            }

            return direccion.dropLast(1)
        }

        fun getValueFromDireccion(direccion: String?, pos: Int): String {

            val direcc = direccion?.split("|")

            return if (direcc?.size ?: 0 > pos) direcc!![pos] else ""
        }

        fun decodeRespuestaGeo(respuestaGeo: String): List<String> {

            val lst = mutableListOf<String>()
            if (!respuestaGeo.isNullOrEmpty()) {
                lst.add(respuestaGeo.trim().substring(0, 2))
                lst.add(respuestaGeo.trim().substring(2, 6))
                lst.add(respuestaGeo.trim().substring(6, 7))
                lst.add(respuestaGeo.trim().substring(7))
                lst.add(respuestaGeo)
            }

            return lst
        }

        fun validarCedulaCO(numeroDocumento: String, tipoDocumento: UneteTipoDocumento): Boolean {
            if (numeroDocumento.isNotBlank()) {
                if (numeroDocumento.length == 8 || numeroDocumento.length == 10) {
                    when (numeroDocumento.length) {
                        8 -> return true
                        10 -> return when (tipoDocumento) {
                            UneteTipoDocumento.CO_CONTRASENA -> {
                                numeroDocumento.substring(0, 1) == Constant.INICIO_CONTRASENA_UNO ||
                                numeroDocumento.substring(0, 5) == Constant.INICIO_CONTRASENA_20000
                            }
                            else -> numeroDocumento.substring(0, 1) == "1"
                        }
                    }
                }
                return false
            }
            return false
        }

        fun getDireccion(direccion: String): String {
            if (direccion.contains(DIRECCION_SEPARATOR)) {
                val lstDirs = direccion.split(DIRECCION_SEPARATOR)
                var finalDir = ""
                lstDirs.forEach { finalDir += ("$it ") }
                return finalDir.trim()
            }
            return direccion.trim()
        }


        fun esZonaRiesgo(codigoPais: String, nivelRiesgo: String): Boolean {
            if ((codigoPais == Pais.ECUADOR.codigoIso && nivelRiesgo.trim() ==
                    UneteNivelRiesgo.BAJO.valor.trim()) ||
                (codigoPais != Pais.ECUADOR.codigoIso && nivelRiesgo.trim() ==
                    UneteNivelRiesgo.ALTO.valor.trim())
            ) {
                return true
            }

            if (codigoPais == Pais.BOLIVIA.codigoIso && nivelRiesgo.trim() == UneteNivelRiesgo.ALTO.valor.trim()) {
                return true
            }

            return false
        }
    }

}
