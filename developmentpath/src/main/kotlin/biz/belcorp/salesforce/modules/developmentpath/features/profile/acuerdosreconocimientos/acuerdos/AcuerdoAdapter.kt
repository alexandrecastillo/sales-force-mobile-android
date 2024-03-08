package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_rdd_acuerdos.view.*

class AcuerdoAdapter : RecyclerView.Adapter<AcuerdoAdapter.AcuerdoViewHolder>() {

    var guardarAcuerdoListener: GuardarAcuerdoListener? = null
    var eliminarAcuerdoListener: EliminarAcuerdoListener? = null
    private var acuerdos: MutableList<AcuerdoModel> = mutableListOf()

    fun actualizar(acuerdos: List<AcuerdoModel>) {
        this.acuerdos = acuerdos.toMutableList()
        notifyDataSetChanged()
    }

    fun setGuardarAcuerdoListener(invocable: (acuerdo: AcuerdoModel) -> Unit) {
        guardarAcuerdoListener = object : GuardarAcuerdoListener {
            override fun alHacerClickGuardar(acuerdo: AcuerdoModel) {
                invocable.invoke(acuerdo)
            }
        }
    }

    fun setEliminarAcuerdoListener(invocable: (acuerdo: AcuerdoModel) -> Unit) {
        eliminarAcuerdoListener = object : EliminarAcuerdoListener {
            override fun alHacerClickEliminar(acuerdo: AcuerdoModel) {
                invocable.invoke(acuerdo)
            }
        }
    }

    override fun onBindViewHolder(holder: AcuerdoViewHolder, position: Int) {
        val acuerdo = acuerdos[position]
        holder.tvDescripcion.text = acuerdo.descripcion
        holder.tvFecha.text = acuerdo.fecha

        holder.tvEditar.visibility = obtenerVisibilidad(acuerdo.mostrarEditarAcuerdo)
        holder.tvEliminar.visibility = obtenerVisibilidad(acuerdo.mostrarEliminarAcuerdo)
        holder.tvDescripcion.visibility = obtenerVisibilidad(acuerdo.mostrarTextoAcuerdo)
        holder.tvFecha.visibility = obtenerVisibilidad(acuerdo.mostrarTextoAcuerdo)
        holder.tvEditar.setOnClickListener {
            acuerdo.mostrarEditarAcuerdo = false
            acuerdo.mostrarEliminarAcuerdo = false
            acuerdo.mostrarTextoAcuerdo = false
            acuerdo.mostrarFechaAcuerdo = false
            acuerdo.mostrarGuardarAcuerdo = true
            acuerdo.mostrarCancelar = true
            acuerdo.mostrarEdicionTextoAcuerdo = true
            notifyItemChanged(position)
        }

        holder.etDescripcion.setText(acuerdo.descripcion)
        holder.tvGuardar.visibility = obtenerVisibilidad(acuerdo.mostrarGuardarAcuerdo)
        holder.tvCancelar.visibility = obtenerVisibilidad(acuerdo.mostrarCancelar)
        holder.etDescripcion.visibility = obtenerVisibilidad(acuerdo.mostrarEdicionTextoAcuerdo)
        holder.tvCancelar.setOnClickListener {
            acuerdo.mostrarEditarAcuerdo = true
            acuerdo.mostrarEliminarAcuerdo = true
            acuerdo.mostrarTextoAcuerdo = true
            acuerdo.mostrarFechaAcuerdo = true
            acuerdo.mostrarGuardarAcuerdo = false
            acuerdo.mostrarCancelar = false
            acuerdo.mostrarEdicionTextoAcuerdo = false
            notifyItemChanged(position)
        }

        holder.tvGuardar.setOnClickListener {
            acuerdo.mostrarEditarAcuerdo = true
            acuerdo.mostrarEliminarAcuerdo = true
            acuerdo.mostrarTextoAcuerdo = true
            acuerdo.mostrarFechaAcuerdo = true
            acuerdo.mostrarGuardarAcuerdo = false
            acuerdo.mostrarCancelar = false
            acuerdo.mostrarEdicionTextoAcuerdo = false
            acuerdo.descripcion = holder.etDescripcion.text.toString()
            guardarAcuerdoListener?.alHacerClickGuardar(acuerdo)
            notifyItemChanged(position)
        }
        holder.tvEliminar.setOnClickListener {
            eliminarAcuerdoListener?.alHacerClickEliminar(acuerdo)
            acuerdos.remove(acuerdo)
            acuerdos.lastOrNull()?.mostrarLineaInferior = false
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcuerdoViewHolder {
        val view = parent.inflate(R.layout.item_rdd_acuerdos)
        return AcuerdoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return acuerdos.size
    }

    private fun obtenerVisibilidad(mostrar: Boolean): Int {
        return if (mostrar) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    interface GuardarAcuerdoListener {
        fun alHacerClickGuardar(acuerdo: AcuerdoModel)
    }

    interface EliminarAcuerdoListener {
        fun alHacerClickEliminar(acuerdo: AcuerdoModel)
    }

    class AcuerdoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDescripcion = view.tv_descripcion_acuerdo
        val tvFecha = view.tv_fecha_acuerdo
        val tvEditar = view.txt_editar_acuerdo
        val tvEliminar = view.txt_eliminar_acuerdo
        val tvGuardar = view.txt_guardar_acuerdo
        val tvCancelar = view.txt_cancelar_acuerdo
        val etDescripcion = view.et_descripcion_acuerdo
    }
}
