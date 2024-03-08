package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.planificadas

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.setOnOneClickListener
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.EventoModel
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import kotlinx.android.synthetic.main.header_seccion_derecha_rdd.view.*
import kotlinx.android.synthetic.main.item_seccion_eventos.view.*
import java.util.*

class SeccionEventos(val context: Context) : StatelessSection(
        SectionParameters.Builder(R.layout.item_seccion_eventos)
                .headerResourceId(R.layout.header_seccion_derecha_rdd)
                .build()) {

    var itemClickListener: ClickListener? = null

    private var eventos = emptyList<EventoModel>()
    private var mostrarCrearEvento: Boolean = false
    private var fechaInicialCrearEvento: Date = Date()

    fun configurarVisibilidadCrearEvento(mostrarCrearEvento: Boolean) {
        this.mostrarCrearEvento = mostrarCrearEvento
    }

    fun configurarFechaInicialCrearEvento(date: Date) {
        this.fechaInicialCrearEvento = date
    }

    fun actualizarEventos(eventos: List<EventoModel>) {
        this.eventos = eventos
    }

    override fun getContentItemsTotal() = eventos.size

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return SeccionHeaderEventosViewHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        val headerHolder = (holder as? SeccionHeaderEventosViewHolder) ?: return

        headerHolder.tvHeaderEvento.setOnClickListener {
            itemClickListener?.onHeaderEventosClick(fechaInicialCrearEvento)
        }

        headerHolder.icHeaderEvento.setOnClickListener {
            itemClickListener?.onHeaderEventosClick(fechaInicialCrearEvento)
        }

        headerHolder.grupoAgregarEvento.visibility =
                obtenerVisibilidadGrupoAgregarEvento()
    }

    private fun obtenerVisibilidadGrupoAgregarEvento(): Int {
        return if (mostrarCrearEvento) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return SeccionEventosViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as SeccionEventosViewHolder
        val evento = eventos[position]

        establecerTituloEvento(evento, itemHolder)
        establecerSubtituloEvento(evento, itemHolder)
        establecerVisibilidadSubtituloEvento(evento, itemHolder)
        establecerVisibilidadSubtituloTodoElDia(evento, itemHolder)
        configurarClickListener(evento, itemHolder)
        mostrarItemRegistrado(evento, itemHolder)
    }

    private fun establecerTituloEvento(evento: EventoModel,
                                       itemHolder: SeccionEventosViewHolder) {
        if (evento.esFeriado) {
            itemHolder.tvTituloEvento.text = context.getString(R.string.mi_ruta_feriado)
        } else {
            itemHolder.tvTituloEvento.text = evento.titulo
        }
    }

    private fun establecerVisibilidadSubtituloEvento(evento: EventoModel,
                                                     itemHolder: SeccionEventosViewHolder) {
        if (evento is EventoModel.Rdd && evento.mostrarSubtituloHoras) {
            itemHolder.tvSubtituloEventoHoras.visibility = View.VISIBLE
        } else {
            itemHolder.tvSubtituloEventoHoras.visibility = View.GONE
        }
    }

    private fun establecerVisibilidadSubtituloTodoElDia(evento: EventoModel,
                                                        itemHolder: SeccionEventosViewHolder) {
        if (evento is EventoModel.Rdd && evento.mostrarSubtituloTodoElDia) {
            itemHolder.tvSubtituloEventoTodoElDia.visibility = View.VISIBLE
        } else {
            itemHolder.tvSubtituloEventoTodoElDia.visibility = View.GONE
        }
    }

    private fun establecerSubtituloEvento(evento: EventoModel,
                                          itemHolder: SeccionEventosViewHolder) {
        if (evento is EventoModel.Rdd) {
            itemHolder.tvSubtituloEventoHoras.text = evento.subtituloHoras
        }
    }

    private fun configurarClickListener(evento: EventoModel,
                                        itemHolder: SeccionEventosViewHolder) {
        if (evento is EventoModel.Rdd) {
            itemHolder.rootLayout.setOnOneClickListener {
                itemClickListener?.onItemEventosClick(evento.id)
            }
        } else {
            itemHolder.rootLayout.setOnClickListener(null)
        }
    }

    private fun mostrarItemRegistrado(evento: EventoModel, itemHolder: SeccionEventosViewHolder){
        if(evento is EventoModel.Rdd && evento.registrar)
            itemHolder.itemLayoutRegistrado.visibility = View.VISIBLE
        else
            itemHolder.itemLayoutRegistrado.visibility = View.GONE
    }

    interface ClickListener {
        fun onHeaderEventosClick(fecha: Date)
        fun onItemEventosClick(eventoXUaId: Long)
    }

    inner class SeccionEventosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootLayout: ViewGroup = view.root
        val tvTituloEvento: TextView = view.tv_titulo_evento
        val tvSubtituloEventoHoras: TextView = view.tv_subtitulo_evento
        val tvSubtituloEventoTodoElDia: TextView = view.tv_subtitulo_evento_todo_el_dia
        val itemLayoutRegistrado: LinearLayout = view.itemLayoutRegistrado
    }

    inner class SeccionHeaderEventosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val grupoAgregarEvento: Group = view.grupoAgregar
        val tvHeaderEvento: TextView = view.tv_header
        val icHeaderEvento: ImageView = view.iv_header
    }
}
