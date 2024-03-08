package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.concursos

import biz.belcorp.salesforce.core.domain.entities.color.Color
import biz.belcorp.salesforce.core.entities.sql.concursos.ConcursosEntity
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.concursos.ConcursoData
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.concursos.Concurso
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.concursos.ConcursoDetalle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.concursos.TipoConcurso
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipData
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipDataDetalle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class ConcursosEntityDataMapperBase {

    private val gson: Gson = Gson()

    abstract fun parse(entidad: ConcursosEntity): Concurso

    fun crearConcursosDetalle(data: String): List<ConcursoDetalle> {
        val concursosData = crearConcursoDataCustom(data)
        return concursosData.map { crearConcursoDetalle(it) }
    }

    private fun crearConcursoDataCustom(data: String): List<ConcursoData> {
        val customType = object : TypeToken<List<ConcursoData>>() {}.type
        return gson.fromJson<List<ConcursoData>>(data, customType)
    }

    private fun crearConcursoDetalle(concursoData: ConcursoData): ConcursoDetalle {
        return ConcursoDetalle(
            tipo = concursoData.tipo,
            nivel = concursoData.nivel,
            descripcionPremio = concursoData.descripcionPremio,
            descripcionProgreso = concursoData.descripcionProgreso,
            imagenUrl = concursoData.imagenUrl,
            puntosAcumulados = concursoData.puntosAcumulados,
            puntosFaltantes = concursoData.puntosFaltantes,
            puntosNivel = concursoData.puntosNivel)
    }

    fun crearConcursoTipData(concurso: Concurso, persona: Persona): TipData {
        return TipData(id = Concurso.DEFAULT_ID,
            tipo = TipData.Tipo.ITEM,
            imagen = TipData.TipoImagen.CONCURSOS,
            titulo = String.format(
                Concurso.TITULO,
                persona.unidadAdministrativa.campania.nombreCorto.deleteHyphen()
            ),
            detalles = crearDetalles(concurso),
            opciones = emptyList(),
            orden = Concurso.DEFAULT_ORDEN)
    }

    private fun crearDetalles(concurso: Concurso): List<TipDataDetalle> {
        val grupoConcursos = concurso.data.groupBy { it.tipo }
        return obtenerPuntosPorTipoConcurso(grupoConcursos).filter { it.texto.isNotEmpty() }
    }

    private fun obtenerPuntosPorTipoConcurso(grupoConcursos: Map<String, List<ConcursoDetalle>>): List<TipDataDetalle> {
        return grupoConcursos.map { crearTipDataDetalle(it.key, it.value) }
    }

    private fun crearTipDataDetalle(tipo: String, detalles: List<ConcursoDetalle>): TipDataDetalle {
        val texto = if (detalles.isEmpty()) Constant.EMPTY_STRING else crearTextoPorTipo(tipo, detalles.first())
        return TipDataDetalle(texto = texto, colores = crearColores())
    }

    private fun crearTextoPorTipo(tipo: String, detalle: ConcursoDetalle): String {
        return when (tipo) {
            TipoConcurso.PROGRAMA_BRILLANTE -> {
                if (detalle.tieneNivel()) Concurso.DESCRIPCION_ES_BRILLANTE
                else String.format(Concurso.DESCRIPCION_BRILLANTE, detalle.puntosFaltantes.toString())
            }
            TipoConcurso.REGALO_POR_PEDIDO -> {
                if (detalle.completoPuntaje()) Concurso.DESCRIPCION_COMPLETO
                else String.format(Concurso.DESCRIPCION_REGALO, detalle.puntosFaltantes.toString())
            }
            else -> Constant.EMPTY_STRING
        }
    }

    private fun crearColores() = listOf(Color.NEGRO)
}
