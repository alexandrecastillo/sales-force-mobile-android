package biz.belcorp.salesforce.modules.developmentpath.utils

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.SeccionMiRutaCards
import com.google.android.gms.maps.model.LatLng
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

fun SectionedRecyclerViewAdapter.onLocationUpdated(id: Int, location: LatLng) {
    val sections = sectionsMap.keys.toList()
    var found = false

    for (section in sections) {
        if (found) break

        getSection(section)?.let {
            if (it is SeccionMiRutaCards) {
                val cards = it.cards

                for (card in cards) {
                    if (card.datosBasicos.personaId == id.toLong()) {
                        card.datosMenu.enMapaModel.ubicacion.apply {
                            latitud = location.latitude
                            longitud = location.longitude
                        }
                        this.notifyDataSetChanged()
                        found = true
                    }
                }
            }
        }
    }
}
