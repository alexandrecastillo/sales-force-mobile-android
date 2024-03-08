package biz.belcorp.salesforce.modules.consultants.features.mypartners


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.capitalizeAll
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.setOnOneClickListener
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.consultants.R
import java.lang.String.format
import kotlinx.android.synthetic.main.view_item_partner.view.address
import kotlinx.android.synthetic.main.view_item_partner.view.consultantName
import kotlinx.android.synthetic.main.view_item_partner.view.containerCover
import kotlinx.android.synthetic.main.view_item_partner.view.containerNoCover
import kotlinx.android.synthetic.main.view_item_partner.view.lastTransaction
import kotlinx.android.synthetic.main.view_item_partner.view.partnerChangeLevel
import kotlinx.android.synthetic.main.view_item_partner.view.partnerLevel
import kotlinx.android.synthetic.main.view_item_partner.view.partnerProfile
import kotlinx.android.synthetic.main.view_item_partner.view.partnerProjection
import kotlinx.android.synthetic.main.view_item_partner.view.partnerProjectionNoCover
import kotlinx.android.synthetic.main.view_item_partner.view.phone
import kotlinx.android.synthetic.main.view_item_partner.view.productive
import kotlinx.android.synthetic.main.view_item_partner.view.projection
import kotlinx.android.synthetic.main.view_item_partner.view.section
import kotlinx.android.synthetic.main.view_item_partner.view.sectionNoC


class MyPartnersAdapter : RecyclerView.Adapter<MyPartnersAdapter.PartnersViewHolder>() {

    private val items = mutableListOf<MyPartnerEntity>()
    var clickListener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_partner, parent, false)
        return PartnersViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartnersViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


    fun setList(items: List<MyPartnerEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class PartnersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: MyPartnerEntity) = with(itemView) {


            if (model.personalInfo.isNotEmpty() && model.personalInfo[0].fullName.isNotEmpty()) {

                containerCover.visible()
                containerNoCover.gone()

                section.text = context.getString(R.string.partner_section, model.section)
                productive.text = model.productivity.capitalizeAll()

                consultantName.text = model.personalInfo[0].fullName.capitalizeAll()
                address.text = model.personalInfo[0].address.capitalizeAll()
                phone.text = model.personalInfo[0].cellphoneNumber
                phone.setOnClickListener {
                    clickListener?.callWhatsApp(model.personalInfo[0].cellphoneNumber)
                }

                partnerProfile.setOnOneClickListener {
                    clickListener?.clickDetailConsultant(model.consultantId, model.consultantCode, Rol.SOCIA_EMPRESARIA)
                }

                partnerProjection.setOnClickListener {
                    clickListener?.clickProjection(model.section)
                }

                partnerChangeLevel.setOnClickListener {
                    clickListener?.clickChangeLevel(model)
                }

                if (model.level.isNotEmpty()) {
                    partnerLevel.text = model.level[0].name
                }

                lastTransaction.text = if (model.isSuccessful) {
                    context.getString(R.string.partner_successfull)
                } else {
                    context.getString(R.string.partners_unsuccessfull)
                }


                if (model.nextLevel.isNotEmpty()) {
                    partnerLevel.text = model.level[0].name
                }

                val projectionString = format(resources.getString(R.string.partner_projection), model.nextLevel[0].campaignsAccomplished)
                projection.text = projectionString

            } else if (model.section.isNotEmpty()) {

                containerCover.gone()
                containerNoCover.visible()
                sectionNoC.text = context.getString(R.string.partner_section, model.section)
                partnerProjectionNoCover.setOnClickListener {
                    clickListener?.clickProjection(model.section)
                }

            }


        }
    }

    interface ClickListener {
        fun callWhatsApp(numero: String?)
        fun clickDetailConsultant(personaId: Int, codigo: String, rol: Rol)

        fun clickProjection(section: String)

        fun clickChangeLevel(model: MyPartnerEntity)
    }

}
