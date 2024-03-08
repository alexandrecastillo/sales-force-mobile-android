package biz.belcorp.salesforce.modules.postulants.core.data.entities.unete

import com.google.gson.annotations.SerializedName

class DevueltoSacEntity {

    @SerializedName("solicitudPostulanteID")
    var solicitudPostulanteID: Int? = null

    @SerializedName("observacion")
    var observacion: String? = null

}
