package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsvideo

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import kotlinx.android.synthetic.main.item_tips_visitas.view.*
import java.util.*

class TipsVisitaAdapter : RecyclerView.Adapter<TipsVisitaAdapter.ViewHolder>() {

    var tips = emptyArray<String?>()
    private val random = Random()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_tips_visitas)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(tips[position]) {
            holder.tvDescripcion.text = this
            holder.ivIcono.setImageResource(imgRand())
        }
    }

    private fun imgRand(): Int {
        return when (rand(1, 3)) {
            Constant.NUMBER_ONE -> R.drawable.group_22
            Constant.NUMBER_TWO -> R.drawable.group_26
            Constant.NUMBER_THREE -> R.drawable.group_38
            else -> R.drawable.group_22
        }
    }

    private fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }

    override fun getItemCount() = tips.size

    fun actualizar(tips: Array<String?>) {
        this.tips = tips
        notifyDataSetChanged()
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvDescripcion: TextView = view.txtMesssage
        val ivIcono: ImageView = view.imgIconVariable
    }
}

