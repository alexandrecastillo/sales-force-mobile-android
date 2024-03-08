package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.setOnOneClickListener
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.AcuerdoModel
import kotlinx.android.synthetic.main.item_acuerdo_historico_detalle.view.*

class AcuerdosHistoricosDetalleAdapter : RecyclerView.Adapter<AcuerdosHistoricosDetalleAdapter.AcuerdoViewHolder>() {

    var acuerdos = emptyList<AcuerdoModel>()
    var clickEnCumplimientoListener: ClickEnCumplimientoListener? = null
    private val diffCallback = AcuerdosDiffCallback()

    fun actualizar(acuerdos: List<AcuerdoModel>) {
        diffCallback.establecer(this.acuerdos, acuerdos)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.acuerdos = acuerdos
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcuerdoViewHolder {
        val view = parent.inflate(R.layout.item_acuerdo_historico_detalle)
        return AcuerdoViewHolder(view)
    }

    override fun getItemCount() = acuerdos.size

    override fun onBindViewHolder(holder: AcuerdoViewHolder, position: Int) {
        val acuerdo = acuerdos[position]
        holder.bind(acuerdo)
    }

    interface ClickEnCumplimientoListener {
        fun alHacerClickEnCumplimiento(acuerdoId: Long)
    }

    inner class AcuerdoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(acuerdo: AcuerdoModel) = with(itemView) {
            textViewContenido?.text = acuerdo.contenido
            textViewFecha.text = acuerdo.fecha

            botonCumplir.visibility = definirVisibilidadBotonCumplir(acuerdo.cumplido)
            botonQuitarCumplimiento.visibility = definirVisibilidadBotonQuitarCumplimiento(acuerdo.cumplido)
            transparencia.visibility = definirVisibilidadTransparencia(acuerdo.cumplido)

            botonCumplir.setOnOneClickListener {
                clickEnCumplimientoListener?.alHacerClickEnCumplimiento(acuerdo.id)
            }
            botonQuitarCumplimiento.setOnOneClickListener {
                clickEnCumplimientoListener?.alHacerClickEnCumplimiento(acuerdo.id)
            }
        }

        private fun definirVisibilidadBotonCumplir(cumplido: Boolean): Int {
            return if (cumplido) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        private fun definirVisibilidadBotonQuitarCumplimiento(cumplido: Boolean): Int {
            return if (cumplido) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        private fun definirVisibilidadTransparencia(cumplido: Boolean): Int {
            return if (cumplido) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
