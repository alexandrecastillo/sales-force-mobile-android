package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion

import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.navegacion.BarraNavegacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd

class BarraNavegacionMapper {

    fun parse(barra: BarraNavegacion): BarraNavegacionModel {
        return when (barra.tipoBarra) {
            BarraNavegacion.TipoBarra.SIN_BARRA ->
                BarraNavegacionModel(niveles = emptyList())
            BarraNavegacion.TipoBarra.NIVEL_UNICO ->
                BarraNavegacionModel(listOf(obtenerNivelUnico(barra)))
            BarraNavegacion.TipoBarra.MULTINIVEL ->
                BarraNavegacionModel(obtenerNivelesMultiples(barra))
        }
    }

    private fun obtenerNivelUnico(barra: BarraNavegacion): NivelModel {
        val nivel = requireNotNull(barra.niveles.firstOrNull()) { "Error al parsear barra de navegación" }

        return NivelModel.Ua(nombreCortoCampania = nivel.ua.campania.nombreCorto,
                             codigoUa = nivel.ua.codigo,
                             tipoUa = obtenerDescripcionUa(nivel.ua),
                             nombreResponsable = obtenerNombreResponsable(nivel.ua),
                             rolLiderAsociado = nivel.ua.llave.rolLiderAsociado,
                             visible = true)
    }

    private fun obtenerNivelesMultiples(barra: BarraNavegacion): List<NivelModel> {
        return mutableListOf<NivelModel>()
                .apply {
                    add(obtenerPrimerNivel())
                    addAll(obtenerNivelesIntermedios(barra))
                    add(obtenerUltimoNivel(barra))
                }
    }

    private fun obtenerPrimerNivel(): NivelModel {
        return NivelModel.RegresarADashboard(visible = false)
    }

    private fun obtenerNivelesIntermedios(barra: BarraNavegacion): List<NivelModel> {
        return barra.niveles
                .take(barra.niveles.size - 1)
                .mapIndexed { indice, nivel ->
                    val cantidadPantallasRegreso = barra.niveles.size - (indice + 1)

                    return@mapIndexed nivelUaRegresable(cantidadPantallasRegreso, nivel)
                }
    }

    private fun nivelUaRegresable(cantidadPantallasRegreso: Int,
                                  nivel: BarraNavegacion.Nivel): NivelModel.UaRegresable {
        return NivelModel.UaRegresable(cantidadPantallasRegreso = cantidadPantallasRegreso,
                                       rolLiderAsociado = nivel.ua.llave.rolLiderAsociado,
                                       tipoUa = obtenerDescripcionUa(nivel.ua),
                                       codigoUa = nivel.ua.codigo,
                                       nombreResponsable = obtenerNombreResponsable(nivel.ua),
                                       codigoCampania = nivel.ua.campania.nombreCorto,
                                       visible = false)
    }

    private fun obtenerUltimoNivel(barra: BarraNavegacion): NivelModel {
        val nivel = requireNotNull(barra.niveles.lastOrNull()) { "Error al parsear barra de navegación" }

        return NivelModel.UaExpandible(cantidadVerMas = barra.niveles.size - 1,
                                       tipoUa = obtenerDescripcionUa(nivel.ua),
                                       codigoUa = nivel.ua.codigo,
                                       nombreResponsable = obtenerNombreResponsable(nivel.ua),
                                       codigoCampania = nivel.ua.campania.nombreCorto,
                                       visible = true,
                                       rolLiderAsociado = nivel.ua.llave.rolLiderAsociado,
                                       expandido = false)
    }

    private fun obtenerNombreResponsable(ua: UnidadAdministrativa): String {
        return if (ua.coberturada) {
            val nombres = (obtenerNombres(ua))
                    .trim()
                    .replace("\\s+".toRegex(), " ")

            WordUtils.capitalizeFully(nombres)
        } else {
            "Descoberturada"
        }
    }

    private fun obtenerNombres(ua: UnidadAdministrativa): String {
        return "${ua.responsableUA?.primerNombre?.trim()
                  ?: ""} ${ua.responsableUA?.primerApellido?.trim() ?: ""}"
    }


    private fun obtenerDescripcionUa(unidadAdministrativa: UnidadAdministrativa): String {
        return when (unidadAdministrativa) {
            is SeccionRdd -> "Sección"
            is ZonaRdd -> "Zona"
            is RegionRdd -> "Región"
            else -> throw Exception("Tipo ua no soportado")
        }
    }
}
