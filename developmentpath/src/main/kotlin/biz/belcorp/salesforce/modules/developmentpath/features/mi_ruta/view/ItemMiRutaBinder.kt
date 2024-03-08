package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.core.GlideApp
import biz.belcorp.salesforce.components.widgets.TextDrawable
import biz.belcorp.salesforce.core.domain.entities.color.Color
import biz.belcorp.salesforce.core.utils.backgroundResource
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.sp
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.card.MiRutaCardModel
import biz.belcorp.salesforce.core.R as coreR

interface ItemMiRutaBinder {
    fun bind(request: Request)

    class Request(
        val card: MiRutaCardModel,
        val itemHolder: SeccionConsultoraViewHolder,
        val context: Context
    )
}

@Deprecated("Commented to Build MiRuta")
class PersonaBinder : ItemMiRutaBinder {

    override fun bind(request: ItemMiRutaBinder.Request) {
        with(request) {
            itemHolder.tvTituloVisita?.text = card.datosBasicos.nombreApellido
            itemHolder.franja?.setBackgroundColor(obtenerColorFranja(request))
            itemHolder.tvHora?.text = card.datosVisita.hora
            itemHolder.tvHora?.visibility = obtenerVisibilidadDeHora(card)
            itemHolder.btnMostrarMenu?.visible()
            itemHolder.imgMap?.gone()

            establecerVisibilidadSeparador(itemHolder)

            itemHolder.card?.setCardBackgroundColor(obtenerColorCard(request))
            itemHolder.card?.cardElevation = obtenerElevacion(card)
            itemHolder.llRealizada?.visibility = obtenerVisibilidadDeRealizada(card)
            itemHolder.ivRealizada?.visibility = obtenerVisibilidadDeRealizada(card)

            itemHolder.llPlanifica?.visibility = obtenerVisibilidadDePlanificar(card)
            itemHolder.tvPlanificar?.let {
                it.paintFlags = it.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            }
            itemHolder.imgMap?.visibility = obtenerVisibilidadGeorefenciacion(card)

            val placeholderCircular = TextDrawable.builder()
                .beginConfig()
                .fontSize(context.sp(16))
                .endConfig()
                .buildRound(
                    card.datosBasicos.iniciales,
                    ContextCompat.getColor(context, R.color.rdd_accent)
                )

            itemHolder.ivAvatar?.let {
                GlideApp.with(context)
                    .load(card.datosBasicos.url)
                    .placeholder(placeholderCircular)
                    .circleCrop()
                    .into(it)
            }
        }
    }

    private fun obtenerVisibilidadGeorefenciacion(card: MiRutaCardModel) =
        if (!card.datosMenu.enMapaModel.ubicacion.poseeCoordenadas) View.VISIBLE
        else View.GONE


    private fun obtenerColorFranja(request: ItemMiRutaBinder.Request): Int {
        with(request) {
            return when (card.datosRol.color) {
                MiRutaCardModel.Color.ROJO ->
                    ContextCompat.getColor(context, R.color.estado_negativo)
                MiRutaCardModel.Color.VERDE ->
                    ContextCompat.getColor(context, R.color.estado_positivo)
                MiRutaCardModel.Color.SANDIA ->
                    ContextCompat.getColor(context, R.color.rdd_nueva)
                MiRutaCardModel.Color.AMARILLO ->
                    ContextCompat.getColor(context, R.color.rdd_establecida)
                MiRutaCardModel.Color.AZUL_POSTULANTE ->
                    ContextCompat.getColor(context, R.color.rdd_postulante)
                MiRutaCardModel.Color.AMARILLO_ESTABLE ->
                    ContextCompat.getColor(context, R.color.rdd_amarillo_estable)
                MiRutaCardModel.Color.ROJO_CRITICA ->
                    ContextCompat.getColor(context, R.color.mi_ruta_eliminar_red)
                MiRutaCardModel.Color.NINGUNO ->
                    ContextCompat.getColor(context, coreR.color.white)
            }
        }
    }

    private fun obtenerVisibilidadDeHora(card: MiRutaCardModel): Int {
        return if (card.datosVisita.mostrarHora) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun establecerVisibilidadSeparador(itemHolder: SeccionConsultoraViewHolder) {
        itemHolder.separadorMapaMenu?.visibility =
            if (itemHolder.btnMostrarMenu?.visibility == View.VISIBLE &&
                itemHolder.imgMap?.visibility == View.VISIBLE
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    private fun obtenerColorCard(request: ItemMiRutaBinder.Request): Int {
        with(request) {
            return if (card.datosVisita.mostrarVisitaRegistrada) {
                ContextCompat.getColor(context, R.color.rdd_bg_registrada)
            } else {
                ContextCompat.getColor(context, coreR.color.white)
            }
        }
    }

    private fun obtenerElevacion(card: MiRutaCardModel): Float {
        return if (card.datosVisita.mostrarVisitaRegistrada) {
            0f
        } else {
            3f
        }
    }

    private fun obtenerVisibilidadDeRealizada(card: MiRutaCardModel): Int {
        return if (card.datosVisita.mostrarVisitaRegistrada) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun obtenerVisibilidadDePlanificar(card: MiRutaCardModel): Int {
        return if (card.datosVisita.mostrarPlanificar) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

class ConsultoraBinder : ItemMiRutaBinder {

    override fun bind(request: ItemMiRutaBinder.Request) {
        with(request) {
            itemHolder.llSegmento?.visible()
            itemHolder.llExito?.gone()
            itemHolder.llSeccion?.gone()
            itemHolder.llSociaNueva?.gone()
            itemHolder.llTips?.gone()

            itemHolder.puntoSegmento?.background = obtenerPuntoSegmento(request)
            itemHolder.tvSegmento?.text =
                (card.datosRol as MiRutaCardModel.DatosRolConsultora).segmento
            itemHolder.includeCantidadProductosPPU?.visibility =
                if (card.datosRol.mostrarCantidadProductosPPU) View.VISIBLE else View.GONE
            itemHolder.tvCantidadProductosPPU?.text = card.datosRol.cantidadProductosPPU.toString()
        }
    }

    private fun obtenerPuntoSegmento(request: ItemMiRutaBinder.Request): Drawable? {
        with(request) {
            return when ((card.datosRol as MiRutaCardModel.DatosRolConsultora).colorSegmento) {
                MiRutaCardModel.Color.SANDIA ->
                    ContextCompat.getDrawable(context, R.drawable.circulo_consultora_nueva)
                MiRutaCardModel.Color.AMARILLO ->
                    ContextCompat.getDrawable(context, R.drawable.circulo_consultora_establecida)
                MiRutaCardModel.Color.AZUL_POSTULANTE ->
                    ContextCompat.getDrawable(context, R.drawable.circulo_consultora_postulante)
                else -> null
            }
        }
    }

    private fun changeColorTipsDesarrollo(
        color: Color,
        itemHolder: SeccionConsultoraViewHolder,
        context: Context
    ) {
        when (color) {
            Color.ROJO -> changeColorRojoTipsDesarrollo(itemHolder, context)
            Color.VERDE -> changeColorVerdeTipsDesarrollo(itemHolder, context)
            else -> Unit
        }
    }

    private fun changeColorRojoTipsDesarrollo(
        itemHolder: SeccionConsultoraViewHolder,
        context: Context
    ) {
        itemHolder.llTips?.backgroundResource = R.drawable.background_rdd_tips_desarrollo_false
        itemHolder.tvTipsDesarrollo?.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.color_text_rdd_tips_desarrollo_false
            )
        )
    }

    private fun changeColorVerdeTipsDesarrollo(
        itemHolder: SeccionConsultoraViewHolder,
        context: Context
    ) {
        itemHolder.llTips?.backgroundResource = R.drawable.background_rdd_tips_desarrollo_true
        itemHolder.tvTipsDesarrollo?.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.color_text_rdd_tips_desarrollo_true
            )
        )
    }
}

class SociaEmpresariaBinder : ItemMiRutaBinder {

    override fun bind(request: ItemMiRutaBinder.Request) {
        with(request) {
            itemHolder.llSegmento?.gone()
            itemHolder.llExito?.visible()
            itemHolder.llSeccion?.visible()

            itemHolder.puntoExito?.background = obtenerPuntoExito(request)
            itemHolder.tvExito?.text = obtenerTextoExito(card)
            itemHolder.tvExito?.setTextColor(obtenerColorExitoSE(request))
            itemHolder.tvSeccion?.text =
                (card.datosRol as? MiRutaCardModel.DatosRolSociaEmpresaria)?.nivelProductividad

            itemHolder.llSociaNueva?.visibility = obtenerVisibilidadDeSociaNueva(card)
            itemHolder.tvNueva?.text =
                (card.datosRol as? MiRutaCardModel.DatosRolSociaEmpresaria)?.textoNueva

            itemHolder.imgMap?.gone()
            itemHolder.llTips?.gone()
        }
    }

    private fun obtenerColorExitoSE(request: ItemMiRutaBinder.Request): Int {
        with(request) {
            if (card.datosRol !is MiRutaCardModel.DatosRolSociaEmpresaria)
                return ContextCompat.getColor(context, coreR.color.white)

            return if (card.datosRol.exitosa) {
                ContextCompat.getColor(context, R.color.estado_positivo)
            } else {
                ContextCompat.getColor(context, R.color.estado_negativo)
            }
        }
    }

    private fun obtenerPuntoExito(request: ItemMiRutaBinder.Request): Drawable? {
        with(request) {
            return if ((card.datosRol as MiRutaCardModel.DatosRolSociaEmpresaria).exitosa) {
                ContextCompat.getDrawable(context, R.drawable.circulo_exitosa)
            } else {
                ContextCompat.getDrawable(context, R.drawable.circulo_no_exitosa)
            }
        }
    }

    private fun obtenerTextoExito(card: MiRutaCardModel): String? {
        return if ((card.datosRol as MiRutaCardModel.DatosRolSociaEmpresaria).exitosa) {
            "Exitosa"
        } else {
            "No exitosa"
        }
    }

    private fun obtenerVisibilidadDeSociaNueva(card: MiRutaCardModel): Int {
        return if ((card.datosRol as MiRutaCardModel.DatosRolSociaEmpresaria).esNueva) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

class PosibleConsultoraBinder : ItemMiRutaBinder {

    override fun bind(request: ItemMiRutaBinder.Request) {
        with(request) {
            itemHolder.llSegmento?.visible()
            itemHolder.llExito?.gone()
            itemHolder.llSeccion?.gone()
            itemHolder.llSociaNueva?.gone()
            itemHolder.imgMap?.gone()

            itemHolder.puntoSegmento?.background =
                ContextCompat.getDrawable(context, R.drawable.circulo_consultora_postulante)
            itemHolder.tvSegmento?.text =
                (card.datosRol as MiRutaCardModel.DatosRolPosibleConsultora).tipo

            if (card.datosTipsDesarrollo == null) {
                itemHolder.llTips?.gone()
            } else {
                val color = card.datosTipsDesarrollo.color

                changeColorTipsDesarrollo(color, itemHolder, context)

                itemHolder.tvTipsDesarrollo?.text = card.datosTipsDesarrollo.texto
            }
        }
    }

    private fun changeColorTipsDesarrollo(
        color: Color,
        itemHolder: SeccionConsultoraViewHolder,
        context: Context
    ) {
        when (color) {
            Color.ROJO -> changeColorRojoTipsDesarrollo(itemHolder, context)
            Color.VERDE -> changeColorVerdeTipsDesarrollo(itemHolder, context)
            else -> Unit
        }
    }

    private fun changeColorRojoTipsDesarrollo(
        itemHolder: SeccionConsultoraViewHolder,
        context: Context
    ) {
        itemHolder.llTips?.backgroundResource = R.drawable.background_rdd_tips_desarrollo_false
        itemHolder.tvTipsDesarrollo?.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.color_text_rdd_tips_desarrollo_false
            )
        )
    }

    private fun changeColorVerdeTipsDesarrollo(
        itemHolder: SeccionConsultoraViewHolder,
        context: Context
    ) {
        itemHolder.llTips?.backgroundResource = R.drawable.background_rdd_tips_desarrollo_true
        itemHolder.tvTipsDesarrollo?.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.color_text_rdd_tips_desarrollo_true
            )
        )
    }
}

class GerenteZonaRegionBinder : ItemMiRutaBinder {

    override fun bind(request: ItemMiRutaBinder.Request) {
        with(request.itemHolder) {

            llSociaNueva?.visibility = obtenerVisibilidadNuevaGZ(request.card)
            tvNueva?.text =
                (request.card.datosRol as? MiRutaCardModel.DatosRolGerenteZona)?.textoNueva

            llSeccion?.gone()
            llSegmento?.visible()
            puntoExito?.gone()
            tvExito?.gone()
            tvSegmento?.text = obtenerTextoSegmento(request.card.datosRol)
            imgMap?.gone()
            llTips?.gone()
        }
    }

    private fun obtenerTextoSegmento(datosRol: MiRutaCardModel.DatosRol): String? {
        return when (datosRol) {
            is MiRutaCardModel.DatosRolGerenteZona -> datosRol.estadoProductividad
            is MiRutaCardModel.DatosRolGerenteRegion -> datosRol.estadoProductividad
            else -> ""
        }
    }

    private fun obtenerVisibilidadNuevaGZ(card: MiRutaCardModel): Int {
        return if ((card.datosRol as? MiRutaCardModel.DatosRolGerenteZona)?.esNueva == true) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
