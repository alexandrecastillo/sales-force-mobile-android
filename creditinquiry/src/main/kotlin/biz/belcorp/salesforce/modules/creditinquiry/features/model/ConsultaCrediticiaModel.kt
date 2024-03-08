package biz.belcorp.salesforce.modules.creditinquiry.features.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class ConsultaCrediticiaModel : Parcelable {

    var campanaIngreso: String? = null

    var codigoConsultora: String? = null

    var deudaBelcorp: String? = null

    var deudaEntidadCrediticia: String? = null

    var docIdentidad: String? = null

    var moneda: String? = null

    var region: String? = null

    var seccion: String? = null

    var tipoDocumento: String? = null

    var ultimaCampanaFacturada: String? = null

    var valoracionInterna: String? = null

    var estadoCliente: String? = null

    var primerApellido: String? = null

    var segundoApellido: String? = null

    var primerNombre: String? = null

    var segundoNombre: String? = null

    var tipoMoneda: String? = null

    var valoracionExterna: String? = null

    var motivoBloqueoDeudaInternaList: List<MotivoBloqueoDeudaInternaModel>? = null

    var deudasExternasList: List<DeudaExternaModel>? = null

    constructor()

    constructor(parcel: Parcel) {
        campanaIngreso = parcel.readString()
        codigoConsultora = parcel.readString()
        deudaBelcorp = parcel.readString()
        deudaEntidadCrediticia = parcel.readString()
        docIdentidad = parcel.readString()
        moneda = parcel.readString()
        region = parcel.readString()
        seccion = parcel.readString()
        tipoDocumento = parcel.readString()
        ultimaCampanaFacturada = parcel.readString()
        valoracionInterna = parcel.readString()
        estadoCliente = parcel.readString()
        primerApellido = parcel.readString()
        segundoApellido = parcel.readString()
        primerNombre = parcel.readString()
        segundoNombre = parcel.readString()
        tipoMoneda = parcel.readString()
        valoracionExterna = parcel.readString()

        motivoBloqueoDeudaInternaList = ArrayList()
        parcel.readList(
            motivoBloqueoDeudaInternaList as ArrayList<MotivoBloqueoDeudaInternaModel>,
            MotivoBloqueoDeudaInternaModel::class.java.classLoader
        )

        deudasExternasList = ArrayList()
        parcel.readList(deudasExternasList as ArrayList<DeudaExternaModel>, DeudaExternaModel::class.java.classLoader)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(campanaIngreso)
        parcel.writeString(codigoConsultora)
        parcel.writeString(deudaBelcorp)
        parcel.writeString(deudaEntidadCrediticia)
        parcel.writeString(docIdentidad)
        parcel.writeString(moneda)
        parcel.writeString(region)
        parcel.writeString(seccion)
        parcel.writeString(tipoDocumento)
        parcel.writeString(ultimaCampanaFacturada)
        parcel.writeString(valoracionInterna)
        parcel.writeString(estadoCliente)
        parcel.writeString(primerApellido)
        parcel.writeString(segundoApellido)
        parcel.writeString(primerNombre)
        parcel.writeString(segundoNombre)
        parcel.writeString(tipoMoneda)
        parcel.writeString(valoracionExterna)
        parcel.writeList(motivoBloqueoDeudaInternaList)
        parcel.writeList(deudasExternasList)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ConsultaCrediticiaModel> =
            object : Parcelable.Creator<ConsultaCrediticiaModel> {
                override fun createFromParcel(parcel: Parcel): ConsultaCrediticiaModel {
                    return ConsultaCrediticiaModel(parcel)
                }

                override fun newArray(size: Int): Array<ConsultaCrediticiaModel?> {
                    return arrayOfNulls(size)
                }
            }
    }
}

