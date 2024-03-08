package biz.belcorp.salesforce.modules.orders.features.results.utils

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import biz.belcorp.salesforce.core.constants.Constant.HYPHEN
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.addIfNotNull
import biz.belcorp.salesforce.modules.orders.R
import biz.belcorp.salesforce.modules.orders.core.domain.contants.Constants.ACTIVAR_BLOQUEO_PEDIDO
import biz.belcorp.salesforce.modules.orders.features.results.model.OpcionesItemModel
import biz.belcorp.salesforce.modules.orders.features.results.model.ResultadoItemPedidosWebModel
import kotlinx.android.synthetic.main.item_resultados_pedidos_web.view.*


object PedidosMasOpciones {

    interface AccionesListener {

        fun bloquear(model: ResultadoItemPedidosWebModel)

        fun desbloquear(model: ResultadoItemPedidosWebModel)

        fun llamar(telefono: String)

        fun irAPerfil(personIdentifier: PersonIdentifier)

        fun cerrarOpciones()

        fun abrirOpciones(consultoraId: Int)

    }

    class Builder(private val itemView: View, val model: ResultadoItemPedidosWebModel) {

        private var bloqueoActivado = ACTIVAR_BLOQUEO_PEDIDO
        private var campaniaActual: String = ""
        private var listener: AccionesListener? = null

        fun setListener(listener: AccionesListener): Builder {
            this.listener = listener
            return this
        }

        fun setCampaniaActual(campaniaActual: String): Builder {
            this.campaniaActual = campaniaActual
            return this
        }

        fun setBloqueoActivado(activado: Int): Builder {
            this.bloqueoActivado = activado
            return this
        }

        fun build() {
            val opciones = obtener(model, campaniaActual, bloqueoActivado)
            mostrarOpcionesLayout(opciones.size)
            mostrarOpcionesItems(opciones)
            configurarEventoCerrar()
        }

        private fun mostrarOpcionesLayout(size: Int) = with(itemView) {
            val layout = when (size) {
                1 -> R.layout.layout_opciones_1
                2 -> R.layout.layout_opciones_2
                else -> R.layout.layout_opciones_3
            }
            LayoutInflater.from(itemView.context)
                    .inflate(layout, layoutOpciones)
        }

        private fun configurarEventoCerrar() = with(itemView) {
            val ivCerrar = layoutOpciones.findViewById<ImageView>(R.id.ivCerrar)
            ivCerrar.setOnClickListener { listener?.cerrarOpciones() }
        }

        private fun mostrarOpcionesItems(opciones: List<OpcionesItemModel>) = with(itemView) {
            opciones.getOrNull(0)?.also { opcion ->
                llenarVista(R.id.ivOpcion1, R.id.tvOpcion1, opcion)
            }
            opciones.getOrNull(1)?.also { opcion ->
                llenarVista(R.id.ivOpcion2, R.id.tvOpcion2, opcion)
            }
            opciones.getOrNull(2)?.also { opcion ->
                llenarVista(R.id.ivOpcion3, R.id.tvOpcion3, opcion)
            }
        }

        private fun llenarVista(ivOpcionId: Int, tvOpcionId: Int, opcion: OpcionesItemModel) = with(itemView) {
            val ivOpcion = layoutOpciones.findViewById<ImageView>(ivOpcionId)
            val tvOpcion = layoutOpciones.findViewById<com.google.android.material.textview.MaterialTextView>(tvOpcionId)
            ivOpcion.setImageResource(opcion.icon)
            ivOpcion.setOnClickListener { gestionarAcciones(opcion.action) }
            tvOpcion.setText(opcion.nameResId)
        }

        private fun gestionarAcciones(action: String?) {
            when (action) {
                OpcionesItemModel.CALL -> listener?.llamar(model.defaultPhone.orEmpty())
                OpcionesItemModel.PROFILE -> irAPerfil()
                OpcionesItemModel.LOCK -> listener?.bloquear(model)
                OpcionesItemModel.UNLOCK -> listener?.desbloquear(model)
                OpcionesItemModel.CLOSE -> listener?.cerrarOpciones()
            }
        }

        private fun irAPerfil() {
            val personIdentifier = PersonIdentifier(
                model.consultorasId.toLong(),
                getConsultingCode(model.code) ?: return,
                Rol.CONSULTORA
            )
            listener?.irAPerfil(personIdentifier)
        }

    }

    private fun getConsultingCode(code: String?): String? {
        return code?.split(HYPHEN)?.firstOrNull()?.trim()
    }

    private fun obtener(model: ResultadoItemPedidosWebModel, campaniaActual: String, bloqueoActivado: Int): List<OpcionesItemModel> {
        return mutableListOf<OpcionesItemModel>().apply {
            addIfNotNull(obtenerOpcionTelefono(model.defaultPhone))
            addIfNotNull(obtenerOpcionBloqueo(model.locked, model.source, model.campania, campaniaActual, bloqueoActivado))
            addIfNotNull(obtenerOpcionProfile())
        }
    }

    private fun obtenerOpcionTelefono(telefono: String?): OpcionesItemModel? {
        val value = telefono?.trim()?.takeIf { it.isNotBlank() && it.length >= 7 }
        return value?.let {
            OpcionesItemModel().apply {
                nameResId = R.string.pedido_llamar
                icon = R.drawable.ic_order_web_phone
                action = OpcionesItemModel.CALL
            }
        }
    }

    private fun obtenerOpcionBloqueo(bloqueado: Int, origen: String?, campania: String?,
                                     campaniaActual: String, bloqueoActivado: Int): OpcionesItemModel? {
        val value = bloqueado.takeIf {
            origen != ResultadoItemPedidosWebModel.ORIGEN_DIGITACION
                    && campania == campaniaActual && bloqueoActivado == ACTIVAR_BLOQUEO_PEDIDO
        }
        return value?.let {
            OpcionesItemModel().apply {
                nameResId = if (it == 1) R.string.pedido_desbloquear else R.string.pedido_bloquear
                icon = if (it == 1) R.drawable.ic_order_web_unlock else R.drawable.ic_order_web_lock
                action = if (it == 1) OpcionesItemModel.UNLOCK else OpcionesItemModel.LOCK
            }
        }
    }

    private fun obtenerOpcionProfile(): OpcionesItemModel {
        return OpcionesItemModel().apply {
            nameResId = R.string.pedido_ver_ficha
            icon = R.drawable.ic_order_web_profile
            action = OpcionesItemModel.PROFILE
        }
    }

}
