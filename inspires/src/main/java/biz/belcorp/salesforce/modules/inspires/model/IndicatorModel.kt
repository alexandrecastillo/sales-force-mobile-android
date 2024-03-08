package biz.belcorp.salesforce.modules.inspires.model

import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.logging.Logger
import kotlin.math.log

abstract class IndicatorModel : Parcelable, Cloneable {

    var position: Int = 0

    var name: String? = null

    var nameDetail: String? = null

    var region: String? = null

    var zona: String? = null

    var seccion: String? = null

    var campaign: String? = null

    var type: String? = null

    var isInvoice: Boolean = false

    var background: Int = 0

    var backgroundLight: Int = 0

    var campaignFull: String? = null

    var gzConsultantDetailHeaderTitle: String? = null

    var gzConsultantDetailHeaderDetail: String? = null

    var idList: Int = 0

    constructor()

    protected constructor(`in`: Parcel) {
        position = `in`.readInt()
        name = `in`.readString()
        nameDetail = `in`.readString()
        region = `in`.readString()
        zona = `in`.readString()
        seccion = `in`.readString()
        campaign = `in`.readString()
        type = `in`.readString()
        isInvoice = `in`.readByte().toInt() != 0
        background = `in`.readInt()
        backgroundLight = `in`.readInt()
        campaignFull = `in`.readString()
        gzConsultantDetailHeaderTitle = `in`.readString()
        gzConsultantDetailHeaderDetail = `in`.readString()
        idList = `in`.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(position)
        dest.writeString(name)
        dest.writeString(nameDetail)
        dest.writeString(region)
        dest.writeString(zona)
        dest.writeString(seccion)
        dest.writeString(campaign)
        dest.writeString(type)
        dest.writeByte((if (isInvoice) 1 else 0).toByte())
        dest.writeInt(background)
        dest.writeInt(backgroundLight)
        dest.writeString(campaignFull)
        dest.writeString(gzConsultantDetailHeaderTitle)
        dest.writeString(gzConsultantDetailHeaderDetail)
        dest.writeInt(idList)
    }

    override fun describeContents(): Int {
        return 0
    }

    abstract fun loadHeader(fragmentTransaction: FragmentTransaction, fragmentManager: FragmentManager)

    public override fun clone(): Any {
        return try {
            super.clone() as IndicatorModel
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
    }
}
