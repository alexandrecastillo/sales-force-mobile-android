package biz.belcorp.salesforce.modules.consultants.features.search.models

import android.os.Parcel
import android.os.Parcelable

class FiltroConsultoraModel() : Parcelable {

    var code: String? = null
    var name: String? = null
    var document: String? = null
    var section: String? = null
    var status: Int? = null
    var segment: String? = null
    var requested: Int? = null
    var authorized: Int? = null
    var residue: Int? = null
    var type: Int? = null
    var level: String? = null
    var limit: Int = 0
    var offset: Int = 0

    constructor(parcel: Parcel) : this() {
        code = parcel.readString()
        name = parcel.readString()
        document = parcel.readString()
        section = parcel.readString()
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        segment = parcel.readString()
        requested = parcel.readValue(Int::class.java.classLoader) as? Int
        authorized = parcel.readValue(Int::class.java.classLoader) as? Int
        residue = parcel.readValue(Int::class.java.classLoader) as? Int
        type = parcel.readValue(Int::class.java.classLoader) as? Int
        level = parcel.readString()
        limit = parcel.readInt()
        offset = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(name)
        parcel.writeString(document)
        parcel.writeString(section)
        parcel.writeValue(status)
        parcel.writeString(segment)
        parcel.writeValue(requested)
        parcel.writeValue(authorized)
        parcel.writeValue(residue)
        parcel.writeValue(type)
        parcel.writeString(level)
        parcel.writeInt(limit)
        parcel.writeInt(offset)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FiltroConsultoraModel> {
        override fun createFromParcel(parcel: Parcel): FiltroConsultoraModel {
            return FiltroConsultoraModel(parcel)
        }

        override fun newArray(size: Int): Array<FiltroConsultoraModel?> {
            return arrayOfNulls(size)
        }
    }

}
