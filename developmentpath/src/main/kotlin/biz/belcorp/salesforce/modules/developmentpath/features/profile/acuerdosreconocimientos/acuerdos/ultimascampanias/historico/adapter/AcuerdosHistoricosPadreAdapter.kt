package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.AcuerdosPorCampaniaModel
import kotlinx.android.synthetic.main.item_acuerdo_historico_padre.view.*

class AcuerdosHistoricosPadreAdapter(
    private val recycledViewPoolHijos: RecyclerView.RecycledViewPool
) : RecyclerView.Adapter<AcuerdosHistoricosPadreAdapter.AcuerdosPorCampaniaViewHolder>() {

    var acuerdosPorCampanias = emptyList<AcuerdosPorCampaniaModel>()
    var clickEnCumplimientoListener: AcuerdosHistoricosDetalleAdapter.ClickEnCumplimientoListener? = null

    private val diffCallback = AcuerdosPorCampaniaDiffCallback()

    fun actualizar(acuerdosPorCampanias: List<AcuerdosPorCampaniaModel>) {
        diffCallback.establecer(
            this@AcuerdosHistoricosPadreAdapter.acuerdosPorCampanias,
            acuerdosPorCampanias
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this@AcuerdosHistoricosPadreAdapter.acuerdosPorCampanias = acuerdosPorCampanias
        diffResult.dispatchUpdatesTo(this@AcuerdosHistoricosPadreAdapter)
    }

    fun setClickEnCumplimientoListener(invocable: (acuerdoId: Long) -> Unit) {
        clickEnCumplimientoListener =
            object : AcuerdosHistoricosDetalleAdapter.ClickEnCumplimientoListener {
                override fun alHacerClickEnCumplimiento(acuerdoId: Long) {
                    invocable.invoke(acuerdoId)
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AcuerdosPorCampaniaViewHolder {
        val view = parent.inflate(R.layout.item_acuerdo_historico_padre)
        return AcuerdosPorCampaniaViewHolder(view)
    }

    override fun getItemCount() = acuerdosPorCampanias.size

    override fun onBindViewHolder(holder: AcuerdosPorCampaniaViewHolder, position: Int) {
        val acuerdo = acuerdosPorCampanias[position]
        holder.bind(acuerdo)
    }

    inner class AcuerdosPorCampaniaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val adapter by lazy {
            AcuerdosHistoricosDetalleAdapter().apply {
                clickEnCumplimientoListener =
                    this@AcuerdosHistoricosPadreAdapter.clickEnCumplimientoListener
            }
        }

        fun bind(acuerdosPorCampania: AcuerdosPorCampaniaModel) = with(itemView){
            textViewCampania?.text = recuperarStringCampania(acuerdosPorCampania.numeroCampania)
            recyclerAcuerdosPorCampania?.apply {
                layoutManager = NonScrollableLayoutManager()
                    .withContext(itemView.context)
                    .linearVertical()
                adapter = this@AcuerdosPorCampaniaViewHolder.adapter
                setRecycledViewPool(recycledViewPoolHijos)
            }
            adapter.actualizar(acuerdosPorCampania.acuerdos)
        }

        private fun recuperarStringCampania(numeroCampania: String) =
            itemView.context.getString(
                R.string.acuerdos_historicos_item_titulo, numeroCampania
            )
    }
}
