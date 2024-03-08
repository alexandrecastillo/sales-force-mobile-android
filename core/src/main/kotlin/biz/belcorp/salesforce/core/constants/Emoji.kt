package biz.belcorp.salesforce.core.constants

object Emoji {
    object Country {
        fun mapToCountryEmoji(countryISO: String): String {
            return when (countryISO) {
                CountryISO.BOLIVIA -> EmojiCode.BO
                CountryISO.CHILE -> EmojiCode.CL
                CountryISO.COLOMBIA -> EmojiCode.CO
                CountryISO.COSTA_RICA -> EmojiCode.CR
                CountryISO.ECUADOR -> EmojiCode.EC
                CountryISO.SALVADOR -> EmojiCode.SV
                CountryISO.GUATEMALA -> EmojiCode.GT
                CountryISO.MEXICO -> EmojiCode.MX
                CountryISO.PANAMA -> EmojiCode.PA
                CountryISO.PERU -> EmojiCode.PE
                CountryISO.PUERTO_RICO -> EmojiCode.PR
                CountryISO.DOMINICANA -> EmojiCode.DO
                else -> EmojiCode.UNKNOWN
            }
        }
    }
}
