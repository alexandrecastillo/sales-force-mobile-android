package biz.belcorp.salesforce.modules.developmentpath.features.resultadovisitas

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.setOnOneClickListener
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import kotlinx.android.synthetic.main.item_lista_resultado_visitas.view.*

class ListarResultadoVisitasAdapter(private val context: Context) :
    RecyclerView.Adapter<ListarResultadoVisitasAdapter.PersonaViewHolder>() {

    var personas = emptyList<ConsultoraModel>()
    var clickListener: ClickListener? = null
    var campaniaAnterior: String? = null
    private val diffCallback = ConsultorasDiffCallback()

    fun actualizar(modelos: List<ConsultoraModel>, campaniaAnteriorNombreCorto: String) {
        diffCallback.establecer(personas, modelos)
        campaniaAnterior = campaniaAnteriorNombreCorto

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        personas = modelos

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_lista_resultado_visitas, parent, false)

        return PersonaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return personas.size
    }

    override fun onBindViewHolder(holder: PersonaViewHolder, position: Int) {
        val persona = personas[position]

        holder.layoutSegmento.visibility = obtenerVisibilidadDeSegmento(persona)
        holder.punto.background = obtenerDrawableDePunto(persona)
        holder.segmento.text = persona.segmento

        holder.nombres.text = persona.nombre

        holder.codigoDigitoVerificador.text =
            context.getString(
                R.string.rdd_lista_resultado_visitas_codigo_digitoverificador,
                persona.codigoMasDigito
            )

        holder.grupoPedidoingresado.visibility =
            obtenerVisibilidadGrupoPedidoIngresado(persona)

        holder.montoPedidoIngresado.text =
            context.getString(
                R.string.rdd_lista_resultado_visitas_monto_pedidoingresado,
                persona.montoPedido
            )

        holder.card.setOnOneClickListener {
            clickListener?.alHacerClickEnCard(persona.id, persona.codigo, persona.rol)
        }

        holder.botonWhatsapp.setOnClickListener {
            clickListener?.alHacerClickEnWhatsapp(persona.telefono)
        }

        holder.botonLlamada.setOnClickListener {
            clickListener?.alHacerClickEnLlamada(persona.telefono)
        }

        holder.cantidadPPU.text = obtenerTextoPPU(persona)
        holder.containerPPU.visibility = obtenerVisibilidadPPU(persona)

    }

    private fun obtenerTextoPPU(persona: ConsultoraModel): String {
        val normalStringUno = context.resources.getQuantityString(
            R.plurals.perfil_cantidad_productos_ppu_normal_uno,
            persona.cantidadProductoPPU,
            campaniaAnterior
        )
        val boldStringUno = context.resources.getQuantityString(
            R.plurals.perfil_cantidad_productos_ppu_bold_uno,
            persona.cantidadProductoPPU,
            persona.cantidadProductoPPU
        )
        val normalStringDos =
            context.resources.getString(R.string.perfil_cantidad_productos_ppu_normal_dos_PAV)

        return normalStringUno + boldStringUno + normalStringDos
    }

    private fun obtenerVisibilidadPPU(persona: ConsultoraModel): Int {
        return if (persona.mostrarPPU) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun obtenerVisibilidadDeSegmento(modelo: ConsultoraModel): Int {
        return if (modelo.tipo == ConsultoraRdd.Tipo.NINGUNA) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun obtenerDrawableDePunto(modelo: ConsultoraModel): Drawable? {
        return when (modelo.tipo) {
            ConsultoraRdd.Tipo.NUEVA ->
                ContextCompat.getDrawable(context, R.drawable.circulo_consultora_nueva)
            ConsultoraRdd.Tipo.ESTABLECIDA, ConsultoraRdd.Tipo.C3 ->
                ContextCompat.getDrawable(context, R.drawable.circulo_consultora_establecida)
            else -> null
        }
    }

    private fun obtenerVisibilidadGrupoPedidoIngresado(persona: ConsultoraModel): Int {
        return if (persona.mostrarPedido) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    class PersonaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView = view.card
        val layoutSegmento: ViewGroup = view.layoutSegmento
        val punto: View = view.puntoSegmento
        val segmento: TextView = view.textViewTituloSegmento
        val nombres: TextView = view.textViewNombre
        val codigoDigitoVerificador: TextView = view.textViewCodigoDigitoVerificador
        val grupoPedidoingresado: Group = view.grupoPedidoIngresado
        val montoPedidoIngresado: TextView = view.textViewMontoPedidoIngresado
        val botonWhatsapp: ImageButton = view.botonWhatsapp
        val botonLlamada: ImageButton = view.botonLlamada
        val cantidadPPU: TextView = view.textPPU
        val containerPPU: View = view.container_ppu

    }

    interface ClickListener {
        fun alHacerClickEnWhatsapp(numero: String?)
        fun alHacerClickEnLlamada(numero: String?)
        fun alHacerClickEnCard(personaId: Long, codigo: String, rol: Rol)
    }
}
