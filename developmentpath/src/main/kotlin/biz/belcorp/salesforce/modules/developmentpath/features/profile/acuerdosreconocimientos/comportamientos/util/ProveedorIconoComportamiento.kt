package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.util

import biz.belcorp.salesforce.modules.developmentpath.R

class ProveedorIconoComportamiento {

    fun recuperarIcono(id: Int) = when (id) {
        1 -> R.drawable.ic_comportamiento_1
        2 -> R.drawable.ic_comportamiento_2
        3 -> R.drawable.ic_comportamiento_3
        4 -> R.drawable.ic_comportamiento_4
        5 -> R.drawable.ic_comportamiento_5
        6 -> R.drawable.ic_comportamiento_6
        7 -> R.drawable.ic_superar_meta
        8 -> R.drawable.ic_habla_claridad_seguridad
        9 -> R.drawable.ic_crea_experiencias_producto
        10 -> R.drawable.ic_muestra_interes_consultoras
        11 -> R.drawable.ic_comportamiento_5
        12 -> R.drawable.ic_ensenia_gestionar
        else -> R.drawable.ic_comportamiento_1
    }

    fun recuperarIconoSeleccionable(id: Int) = when (id) {
        1 -> R.drawable.selector_comportamiento_co_1
        2 -> R.drawable.selector_comportamiento_co_2
        3 -> R.drawable.selector_comportamiento_co_3
        4 -> R.drawable.selector_comportamiento_co_4
        5 -> R.drawable.selector_comportamiento_co_5
        6 -> R.drawable.selector_comportamiento_co_6
        7 -> R.drawable.selector_comportamiento_se_1
        8 -> R.drawable.selector_comportamiento_se_2
        9 -> R.drawable.selector_comportamiento_se_3
        10 -> R.drawable.selector_comportamiento_se_4
        11 -> R.drawable.selector_comportamiento_se_5
        12 -> R.drawable.selector_comportamiento_se_6
        else -> R.drawable.selector_comportamiento_co_1
    }
}
