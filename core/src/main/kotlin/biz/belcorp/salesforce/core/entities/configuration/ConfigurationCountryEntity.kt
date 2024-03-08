package biz.belcorp.salesforce.core.entities.configuration

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.db.objectbox.converters.NullToEmptyStringConverter
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class ConfigurationCountryEntity constructor(
    @Id var code: Long = 0,
    var country: String = Constant.EMPTY_STRING,
    var countryName: String = Constant.EMPTY_STRING,
    var currencySymbol: String = Constant.EMPTY_STRING,
    var phoneCode: String = Constant.EMPTY_STRING,
    var minimalOrderAmount: Long = Constant.NUMERO_CERO.toLong(),
    var tippingPoint: Long = Constant.NUMERO_CERO.toLong(),
    var flagShowProactive: Boolean = false,
    var isPdv: Boolean = false,
    var flagMto: Int = Constant.UNO_NEGATIVO,
    @Convert(dbType = String::class, converter = NullToEmptyStringConverter::class)
    var mainBrand: String = Constant.EMPTY_STRING,
    var isGanaMas: Boolean = false
) {

    @Backlink(to = "configParent")
    var zones: ToMany<ConfigurationZoneEntity> = ToMany(this, ConfigurationCountryEntity_.zones)
}
