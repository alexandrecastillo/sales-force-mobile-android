package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.se.model.ReconocimientoModel
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_rdd_reconocimiento_pendiente.view.*

class ReconocimientosAdapter :
    RecyclerView.Adapter<ReconocimientosAdapter.ReconocimientoHolder>() {

    private var reconocimientos: List<ReconocimientoModel> = emptyList()

    val clickObservable: PublishSubject<ReconocimientoModel> =
        PublishSubject.create<ReconocimientoModel>()

    fun actualizar(reconocimientos: List<ReconocimientoModel>) {
        this.reconocimientos = reconocimientos
        notifyDataSetChanged()
    }

    override fun getItemCount() = reconocimientos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReconocimientoHolder {
        return ReconocimientoHolder(parent.inflate(R.layout.item_rdd_reconocimiento_pendiente))
    }

    override fun onBindViewHolder(holder: ReconocimientoHolder, position: Int) {
        holder.bind(reconocimientos[position])
    }

    private fun habilitarACostaDe(vistaAMostrar: View?, vistaAOcultar: View?) {
        vistaAMostrar?.visibility = View.VISIBLE
        vistaAMostrar?.isEnabled = true
        vistaAOcultar?.visibility = View.INVISIBLE
        vistaAOcultar?.isEnabled = false
    }

    inner class ReconocimientoHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(reconocimiento: ReconocimientoModel) = with(itemView) {

            text_campania_reconocimiento?.text = reconocimiento.campania.maskCampaignWithPrefix()
            rating_reconocimiento_item?.rating = reconocimiento.valoracion.toFloat()

            if (reconocimiento.estaReconocida)
                habilitarACostaDe(group_reconocida, group_no_reconocida)
            else
                habilitarACostaDe(group_no_reconocida, group_reconocida)

            button_reconocer_detalle?.setOnClickListener { clickObservable.onNext(reconocimiento) }

            text_ver_mas_reconocida?.setOnClickListener { clickObservable.onNext(reconocimiento) }
        }
    }
}
