package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.mobile.components.design.counter.Counter
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.model.SocialBonusModel
import kotlinx.android.synthetic.main.item_calculator_current_level_extra_bonus.view.*

class CalculatorBonusAdapter : RecyclerView.Adapter<CalculatorBonusAdapter.ViewHolder>() {

    private val attachToRoot: Boolean = false
    private val resource: Int = R.layout.item_calculator_current_level_extra_bonus
    private var onItemEventListener: OnItemEventListener? = null
    var list: MutableList<SocialBonusModel> = arrayListOf()

    fun getItem(position: Int): SocialBonusModel = list[position]
    fun isEmpty(): Boolean = list.isEmpty()
    fun addModel(model: SocialBonusModel) {
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

        fun bind(model: SocialBonusModel) {
            itemView.txvBonusSelected?.text = model.descripcionTipoBono
            setIniButtonBonus(model)
            setIniTextAmount(model)
            setIniCountBonus(model)
            setInitextMessage(model)

        }

        private fun setInitextMessage(model: SocialBonusModel) {
            itemView.txvBonusMessage?.text = model.mensaje
            itemView.txvBonusMessageAlt?.text = model.mensajeAlt
            itemView.txvBonusMessageAlt?.visible(model.mensajeAlt != null)
        }

        private fun setIniCountBonus(model: SocialBonusModel) {
            itemView.couBonusAmountAlt?.apply {
                quantity = model.cantidadIngresadaAlt
                visible(model.cantidadAlt != null)
                quantityListener = object : Counter.OnChangeQuantityListener {
                    override fun onChange(quantity: Int) {
                        model.cantidadIngresadaAlt = quantity
                    }
                }
            }
            itemView.couBonusAmount?.apply {
                quantity = model.cantidadIngresada
                visible(model.ingresaCantidad)
                quantityListener = object : Counter.OnChangeQuantityListener {
                    override fun onChange(quantity: Int) {
                        model.cantidadIngresada = quantity
                    }
                }
            }
            model.cantidad?.let { max -> itemView.couBonusAmount?.setMaxQuantity(max) }
            model.cantidadAlt?.let { max -> itemView.couBonusAmountAlt?.setMaxQuantity(max) }
        }

        private fun setIniTextAmount(model: SocialBonusModel) {
            with(itemView) {
                model.ingresaCantidad?.let {
                    if (it) {
                        txvBonusAmountLabel?.visible()
                        imvBonusAmountLabel?.visible()
                    }
                }
                model.cantidadAlt?.let {
                    txvBonusAmountLabelAlt?.visible()
                    imvBonusAmountLabelAlt?.visible()
                }
            }
        }

        private fun setIniButtonBonus(model: SocialBonusModel) {
            itemView.btnBonusDelete?.apply {
                tag = model.codigoTipoBono
                setOnClickListener {
                    itemView.couBonusAmount?.resetQuantity()
                    itemView.couBonusAmountAlt?.resetQuantity()
                    list.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    onItemEventListener?.onClick(adapterPosition, itemCount)
                }
            }

        }
    }

    interface OnItemEventListener {
        fun onClick(position: Int, size: Int)
    }
}
