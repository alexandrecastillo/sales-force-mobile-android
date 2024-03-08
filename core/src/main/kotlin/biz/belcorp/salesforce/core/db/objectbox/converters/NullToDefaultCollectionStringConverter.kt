package biz.belcorp.salesforce.core.db.objectbox.converters

import io.objectbox.converter.PropertyConverter

class NullToDefaultCollectionStringConverter : PropertyConverter<String, String?> {

    override fun convertToDatabaseValue(entityProperty: String): String? {
        return entityProperty
    }

    override fun convertToEntityProperty(databaseValue: String?): String {
        return databaseValue ?: DAYS_21
    }

    companion object {

        private const val DAYS_21 = "21"

    }

}
