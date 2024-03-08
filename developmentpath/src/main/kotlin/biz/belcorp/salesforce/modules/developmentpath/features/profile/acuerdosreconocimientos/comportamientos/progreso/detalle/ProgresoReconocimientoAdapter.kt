package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.detalle

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.detalle.ProgresoReconocimientoFragment.ProgresoModel
import kotlinx.android.synthetic.main.item_card_progres.view.*

class ProgresoReconocimientoAdapter(
    var mListProgreso: List<ProgresoModel>,
    var rol: Rol
) : RecyclerView.Adapter<ProgresoReconocimientoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_card_progres)
        return ViewHolder(view, parent.context, rol)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binTipsVisitas(mListProgreso[position])
    }

    override fun getItemCount(): Int {
        return mListProgreso.size
    }

    class ViewHolder(
        val view: View,
        mContext: Context,
        var rol: Rol
    ) : RecyclerView.ViewHolder(view) {
        private val ctx = mContext
        fun binTipsVisitas(mProgresoModel: ProgresoModel) {
            with(mProgresoModel) {
                view.pgb_porcentaje.progress = mPorsentage.toFloat()
                val subPercentage = "$mPorsentage%"
                view.txtPercentage.text = subPercentage
                when (rol) {
                    Rol.CONSULTORA -> {
                        view.img_comportamiento.setImageDrawable(getImageProgreso(iconProgreso))
                    }
                    Rol.SOCIA_EMPRESARIA -> {
                        view.img_comportamiento.setImageDrawable(getImageProgresoSE(iconProgreso))
                    }
                    else -> Unit
                }
                view.txtTitleComportamiento.text = mDescripcion
                view.imgCamp1.setImageDrawable(getImageCheking(mCapania1))
                view.imgCamp2.setImageDrawable(getImageCheking(mCapania2))
                view.imgCamp3.setImageDrawable(getImageCheking(mCapania3))
                view.imgCamp4.setImageDrawable(getImageCheking(mCapania4))
                view.imgCamp5.setImageDrawable(getImageCheking(mCapania5))
                view.imgCamp6.setImageDrawable(getImageCheking(mCapania6))
            }
        }

        fun getImageCheking(mPosition: Boolean): Drawable? {
            return if (mPosition) {
                ActivityCompat.getDrawable(ctx, R.drawable.ic_oval_check)
            } else {
                ActivityCompat.getDrawable(ctx, R.drawable.ic_close_circle_outline)
            }
        }

        fun getImageProgreso(mPosition: Int?): Drawable? {
            return when (mPosition) {
                1 -> ActivityCompat.getDrawable(ctx, R.drawable.ic_avance_comp_dos)
                2 -> ActivityCompat.getDrawable(ctx, R.drawable.ic_avance_comp_uno)
                3 -> ActivityCompat.getDrawable(ctx, R.drawable.ic_avance_comp_tres)
                4 -> ActivityCompat.getDrawable(ctx, R.drawable.ic_avance_comp_cuatro)
                5 -> ActivityCompat.getDrawable(ctx, R.drawable.ic_avance_comp_cinco)
                else -> ActivityCompat.getDrawable(ctx, R.drawable.ic_avance_comp_seis)
            }
        }

        fun getImageProgresoSE(mPosition: Int?): Drawable? {
            return when (mPosition) {
                1 -> ActivityCompat.getDrawable(ctx, R.drawable.ic_avance_comp_uno_se)
                2 -> ActivityCompat.getDrawable(ctx, R.drawable.ic_avance_comp_dos_se)
                3 -> ActivityCompat.getDrawable(ctx, R.drawable.ic_avance_comp_tres_se)
                4 -> ActivityCompat.getDrawable(ctx, R.drawable.ic_avance_comp_cuatro_se)
                5 -> ActivityCompat.getDrawable(ctx, R.drawable.ic_avance_comp_cinco_se)
                else -> ActivityCompat.getDrawable(ctx, R.drawable.ic_avance_comp_seis_se)
            }
        }
    }
}
