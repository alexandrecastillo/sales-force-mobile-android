package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo

import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipsDesarrolloEntity
import biz.belcorp.salesforce.core.utils.ColorUtils
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.TipDesarrollo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipData
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipDataDetalle
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

abstract class TipsDesarrolloMapperBase(private val gson: Gson) {

    abstract fun parse(entidad: TipsDesarrolloEntity): TipDesarrollo
    abstract fun parse(entidades: List<TipsDesarrolloEntity>): List<TipDesarrollo>

    fun crearData(data: String): ArrayList<TipData> {
        val tipDesarrolloData = crearTipDataCustom(data)
        return ArrayList(tipDesarrolloData.map { parseTipData(it) })
    }

    private fun crearTipDataCustom(data: String): List<TipDataEntity> {
        val customType = object : TypeToken<List<TipDataEntity>>() {}.type
        return gson.fromJson<List<TipDataEntity>>(data, customType)
    }

    private fun parseTipData(tipDesarrollo: TipDataEntity): TipData {
        return TipData(
            id = tipDesarrollo.id,
            titulo = tipDesarrollo.titulo,
            imagen = tipDesarrollo.tipoImagen,
            orden = tipDesarrollo.orden,
            tipo = tipDesarrollo.tipoVista,
            opciones = tipDesarrollo.opciones.toList(),
            detalles = tipDesarrollo.detalles.map { parseDetalle(it) }
        )
    }

    private fun parseDetalle(detalle: TipDataEntity.TipDataDetalleEntity): TipDataDetalle {
        return TipDataDetalle(
            texto = detalle.texto,
            colores = detalle.tipoColores)
    }

    class TipDataEntity(@SerializedName(TITULO)
                        val titulo: String,
                        @SerializedName(IMAGENID)
                        val imagen: String,
                        @SerializedName(ORDEN)
                        val orden: Int,
                        @SerializedName(ID)
                        val id: Int,
                        @SerializedName(TIPO)
                        val tipo: Int,
                        @SerializedName(OPCIONES)
                        val opciones: Array<String> = arrayOf(),
                        @SerializedName(DETALLES)
                        val detalles: List<TipDataDetalleEntity> = emptyList()) {

        val tipoImagen get() = obtenerTipoImagen()

        val tipoVista get() = crearTipoVista(tipo)

        private fun obtenerTipoImagen(): TipData.TipoImagen {
            return when (imagen) {
                TipData.TipoImagen.VENTAGANANCIA.valor -> TipData.TipoImagen.VENTAGANANCIA
                TipData.TipoImagen.DIGITAL.valor -> TipData.TipoImagen.DIGITAL
                TipData.TipoImagen.CONCURSOS.valor -> TipData.TipoImagen.CONCURSOS
                TipData.TipoImagen.NOVEDADES.valor -> TipData.TipoImagen.NOVEDADES
                TipData.TipoImagen.PROGRAMANUEVAS.valor -> TipData.TipoImagen.PROGRAMANUEVAS
                TipData.TipoImagen.TIPSESTABLECIDAS.valor -> TipData.TipoImagen.TIPSESTABLECIDAS
                TipData.TipoImagen.CAMINOBRILLANTE.valor -> TipData.TipoImagen.CAMINOBRILLANTE
                else -> TipData.TipoImagen.OTHER
            }
        }

        private fun crearTipoVista(tipo: Int): TipData.Tipo {
            return when (tipo) {
                TipData.Tipo.PARRAFO.valor -> TipData.Tipo.PARRAFO
                TipData.Tipo.ETIQUETA.valor -> TipData.Tipo.ETIQUETA
                TipData.Tipo.ITEM.valor -> TipData.Tipo.ITEM
                else -> TipData.Tipo.NINGUNO
            }
        }

        companion object {
            private const val TITULO = "titulo"
            private const val IMAGENID = "imagenId"
            private const val ORDEN = "orden"
            private const val DETALLES = "detalles"
            private const val TIPO = "tipo"
            private const val OPCIONES = "opciones"
            private const val ID = "idSeccion"
        }

        class TipDataDetalleEntity(@SerializedName(TEXTO)
                                   val texto: String,
                                   @SerializedName(COLORES)
                                   val colores: List<Int> = emptyList()) {

            val tipoColores get() = colores.map { color -> ColorUtils.crearTipoColor(color) }

            companion object {
                private const val TEXTO = "texto"
                private const val COLORES = "colores"
            }
        }

    }

}
