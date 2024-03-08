package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.util.ProveedorIconoComportamiento
import kotlinx.android.synthetic.main.item_reconocimiento_comportamiento.view.*

class ReconocimientosAdapter : RecyclerView.Adapter<ReconocimientosAdapter.ReconocimientoViewHolder>() {

    private var reconocimientos = emptyList<ReconocimientoModel>()
    private var onClickListener: OnClickListener? = null
    private val proveedorIconos by lazy { ProveedorIconoComportamiento() }

    fun setClickListener(invocable: (posicion: Int) -> Unit) {
        onClickListener = object : OnClickListener {
            override fun alClickearComportamiento(posicion: Int) {
                invocable.invoke(posicion)
            }
        }
    }

    fun actualizar(reconocimientos: List<ReconocimientoModel>) {
        this.reconocimientos = reconocimientos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReconocimientoViewHolder {
        val view = parent.inflate(R.layout.item_reconocimiento_comportamiento)
        return ReconocimientoViewHolder(view)
    }

    override fun getItemCount() = reconocimientos.size

    override fun onBindViewHolder(holder: ReconocimientoViewHolder, position: Int) {
        val modelo = reconocimientos[position]

        holder.bind(modelo)
        holder.itemView.setOnClickListener { onClickListener?.alClickearComportamiento(position) }
    }

    inner class ReconocimientoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(modelo: ReconocimientoModel) = with(itemView) {
            textViewTitulo?.text = modelo.descripcion
            imageViewIcono?.setImageResource(obtenerIcono(modelo.iconoId) ?: return)
            imageViewIcono?.isEnabled = modelo.seleccionado
        }

        private fun obtenerIcono(id: Int): Int? {
            return proveedorIconos.recuperarIconoSeleccionable(id)
        }
    }

    interface OnClickListener {
        fun alClickearComportamiento(posicion: Int)
    }
}
