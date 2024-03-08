package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.se.model

/** Created by Soporte on 5/06/2018. */


data class ReconocimientoModel(val idReconocimiento: Long,
                               val estaReconocida: Boolean,
                               val campania: String,
                               val valoracion: Int,
                               val nombreReconocida: String)
