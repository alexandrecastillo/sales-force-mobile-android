package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.dialogs.list.ListDialog
import biz.belcorp.mobile.components.dialogs.list.adapters.ListDialogAdapter
import biz.belcorp.mobile.components.dialogs.list.model.ListDialogModel
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraDetalleConcursoVariableSociaModel
import kotlinx.android.synthetic.main.item_calculator_current_level_competition.view.*

class CalculatorDetailCompetitionAdapter(private var fragmentManager: FragmentManager?,
                                         private var fragmentActivity: FragmentActivity
) : RecyclerView.Adapter<CalculatorDetailCompetitionAdapter.ViewHolder>() {

    private val attachToRoot: Boolean = false
    private val resource: Int = R.layout.item_calculator_current_level_competition
    private var onItemEventListener: OnItemEventListener? = null
    var list: MutableList<CalculadoraDetalleConcursoVariableSociaModel> = arrayListOf()

    fun getItem(position: Int): CalculadoraDetalleConcursoVariableSociaModel = list[position]
    fun isEmpty(): Boolean = list.isEmpty()
    fun addModel(model: CalculadoraDetalleConcursoVariableSociaModel) {
        this.list.add(model)
        notifyItemInserted(list.indexOf(model))
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(resource, parent, attachToRoot))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: CalculadoraDetalleConcursoVariableSociaModel) {
            val ldDetalleConcursoLevel = ListDialog()
            val listDialogModel = mutableListOf<ListDialogModel>()

            itemView.txvCompetitionSelected?.text = model.variable
            itemView.btnCompetitionBonusDelete?.apply {
                tag = model.variable
                setOnClickListener {
                    model.nivelSeleccionado = null
                    itemView.btnDetailCompetitionLevel?.text = context?.getString(R.string.calculator_default_number_hint)
                    list.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    onItemEventListener?.onClick(adapterPosition, itemCount)
                }
            }

            model.nivelBT?.forEach {
                listDialogModel.add(ListDialogModel(it.toString(), it.toString()))
            }
            itemView.btnDetailCompetitionLevel?.text = model.nivelSeleccionado?.toString()
            ldDetalleConcursoLevel.dialogAdapter?.resetSelection()
            ldDetalleConcursoLevel.setAdapter(ListDialogAdapter(fragmentActivity, listDialogModel))
            itemView.btnDetailCompetitionLevel?.setOnClickListener {
                fragmentManager?.let { fm ->
                    ldDetalleConcursoLevel.dialogAdapter?.resetSelection()
                    ldDetalleConcursoLevel.show(fm, model.variable)
                }
            }

            ldDetalleConcursoLevel.setListener(object : ListDialog.ListItemDialogListener {
                override fun clickItem(position: Int) {
                    model.nivelSeleccionado = listDialogModel[position].name?.toInt()
                    itemView.btnDetailCompetitionLevel?.text = model.nivelSeleccionado?.toString()
                }
            })
        }

    }

    interface OnItemEventListener {
        fun onClick(position: Int, size: Int)
    }
}
