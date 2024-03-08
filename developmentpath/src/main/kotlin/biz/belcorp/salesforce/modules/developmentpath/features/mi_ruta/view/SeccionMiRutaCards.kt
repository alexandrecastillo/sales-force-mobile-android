package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.setSafeOnClickListener
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.card.MiRutaCardModel
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection

class SeccionMiRutaCards(val context: Context,
                         private val tituloHeader: String?,
                         private val iconoHeaderResourceId: Int?,
                         private val alClickearPersonaListener: AlClikearPersonaListener?,
                         private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter?) :
        StatelessSection(
                SectionParameters.Builder(R.layout.item_mi_ruta)
                        .headerResourceId(R.layout.header_seccion_rdd)
                        .build()) {

    var cards = emptyList<MiRutaCardModel>()
    private val personaBinder = PersonaBinder()

    fun actualizar(cards: List<MiRutaCardModel>) {
        this.cards = cards
    }

    override fun getContentItemsTotal() = cards.size

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return SeccionConsultoraViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder = holder as SeccionConsultoraViewHolder
        val card = cards[position]
        val request = ItemMiRutaBinder.Request(card, itemHolder, context)
        val rolBinder = obtenerBinder(request.card.datosRol)

        personaBinder.bind(request)
        rolBinder.bind(request)

        itemHolder.btnMostrarMenu?.setOnClickListener {
            firebaseAnalyticsPresenter?.enviarEvento(TagAnalytics.EVENTO_VER_MAS)
            alClickearPersonaListener?.alClickearMenu(card)
        }

        itemHolder.imgMap?.setOnClickListener {
            alClickearPersonaListener?.alClickearGeorefencia(card)
        }

        itemHolder.tvPlanificar?.setOnClickListener {
            alClickearPersonaListener?.alClickearPlanificar(card)
        }

        itemHolder.card?.setSafeOnClickListener {
            alClickearPersonaListener?.alClickearCard(card)
        }
    }

    private fun obtenerBinder(datosRol: MiRutaCardModel.DatosRol): ItemMiRutaBinder {
        return when (datosRol) {
            is MiRutaCardModel.DatosRolPosibleConsultora -> PosibleConsultoraBinder()
            is MiRutaCardModel.DatosRolConsultora -> ConsultoraBinder()
            is MiRutaCardModel.DatosRolSociaEmpresaria -> SociaEmpresariaBinder()
            is MiRutaCardModel.DatosRolGerenteZona -> GerenteZonaRegionBinder()
            is MiRutaCardModel.DatosRolGerenteRegion -> GerenteZonaRegionBinder()
            else -> throw Exception("Rol no soportado")
        }
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return SeccionConsultoraHeaderViewHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        val headerHolder = holder as SeccionConsultoraHeaderViewHolder

        headerHolder.tvHeader.text = tituloHeader

        if (iconoHeaderResourceId == null) {
            headerHolder.ivHeader.visibility = View.GONE
        } else {
            headerHolder.ivHeader.setImageResource(iconoHeaderResourceId)
        }
    }
}
