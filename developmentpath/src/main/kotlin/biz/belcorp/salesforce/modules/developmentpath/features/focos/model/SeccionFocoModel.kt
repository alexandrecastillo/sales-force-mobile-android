package biz.belcorp.salesforce.modules.developmentpath.features.focos.model

import java.io.Serializable

data class SeccionFocoModel(val codigoSeccion: String,
                            val sociaId: Long?,
                            val nombresSocia: String,
                            val coberturada: Boolean,
                            val mostrarEditar: Boolean,
                            val mostrarFocos: Boolean,
                            val focos: List<FocoModel>) : Serializable {

    data class FocoModel(val id: Long, val descripcion: String?) : Serializable
}
