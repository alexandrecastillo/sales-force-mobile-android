package biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.adapters

import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import kotlinx.android.synthetic.main.item_first_column.view.*

class FirstColumnAdapter(
    datosInit: List<String>,
    val name: String,
    private val myClick: ClickListener
) :
    RecyclerView.Adapter<FirstColumnAdapter.DetalleViewHolder>() {

    private var leftArrowVisibility = true
    private var datos = arrayListOf<String>()

    init {
        datos = arrayListOf()
        datos.add(name)
        datos.addAll(datosInit)
        if (datos.size > Constant.NUMERO_UNO) datos[Constant.NUMERO_UNO] = name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_first_column, parent, false)
        setupTreeObserver(itemView)
        return DetalleViewHolder(itemView, myClick)
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    override fun onBindViewHolder(holder: DetalleViewHolder, position: Int) {
        holder.bind(datos[position], position, leftArrowVisibility)
    }

    private fun setupTreeObserver(itemView: View) {
        val viewTreeObserver = itemView.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    itemView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                }
            })
        }
    }

    class DetalleViewHolder(view: View, private val myClick: ClickListener) :
        RecyclerView.ViewHolder(view) {


        fun bind(model: String, position: Int, leftArrowVisibility: Boolean) {
            itemView.tvCodigo.text = model

            itemView.ivArrowLeft.setOnClickListener {
                myClick.onClick()
            }

            itemView.ivArrowLeft.visibility = View.GONE
            itemView.tvCodigo.visibility = View.VISIBLE

            when (position) {
                Constant.NUMERO_CERO -> getPositionZero(leftArrowVisibility)
                Constant.NUMERO_UNO -> getPositionOne()
                else -> getOtherPosition()
            }
        }

        private fun getOtherPosition() {
            itemView.tvCodigo.typeface = ResourcesCompat.getFont(
                itemView.context,
                biz.belcorp.salesforce.base.R.font.mulish_regular
            )
            itemView.tvCodigo.setTextColor(
                ContextCompat.getColor(itemView.context, R.color.light_gray)
            )
        }

        private fun getPositionOne() {
            itemView.tvCodigo.setTextColor(
                ContextCompat.getColor(itemView.context, R.color.titles)
            )

            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                itemView.tvCodigo,
                Constant.NUMERO_UNO,
                Constant.NUMERO_DIECISEIS,
                Constant.NUMERO_CUATRO,
                TypedValue.COMPLEX_UNIT_SP
            )
            itemView.tvCodigo.typeface =
                ResourcesCompat.getFont(
                    itemView.context, biz.belcorp.salesforce.base.R.font.mulish_regular
                )
        }

        private fun getPositionZero(leftArrowVisibility: Boolean) {
            itemView.ivArrowLeft.visibility =
                if (leftArrowVisibility) View.VISIBLE else View.INVISIBLE

            itemView.tvCodigo.visibility = View.GONE
            itemView.tvCodigo.setTextColor(
                ContextCompat.getColor(itemView.context, R.color.indicator_requests)
            )

            itemView.tvCodigo.setTypeface(itemView.tvCodigo.typeface, Typeface.BOLD)

            itemView.tvCodigo.typeface = ResourcesCompat.getFont(
                itemView.context,
                biz.belcorp.salesforce.base.R.font.mulish_bold
            )
        }
    }

    interface ClickListener {
        fun onClick()
    }


    fun setLeftArrowVisibility(leftArrowVisibility: Boolean) {
        this.leftArrowVisibility = leftArrowVisibility
    }
}
