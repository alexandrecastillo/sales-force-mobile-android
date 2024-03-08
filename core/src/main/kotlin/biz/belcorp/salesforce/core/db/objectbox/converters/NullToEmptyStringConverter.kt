package biz.belcorp.salesforce.core.db.objectbox.converters

import io.objectbox.converter.PropertyConverter

class NullToEmptyStringConverter : PropertyConverter<String, String?> {
    override fun convertToDatabaseValue(entityProperty: String): String? {
        return entityProperty
    }

    override fun convertToEntityProperty(databaseValue: String?): String {
        return databaseValue.orEmpty()
    }
}
