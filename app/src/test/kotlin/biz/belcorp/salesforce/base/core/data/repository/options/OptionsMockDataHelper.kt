package biz.belcorp.salesforce.base.core.data.repository.options

import biz.belcorp.salesforce.base.core.data.repository.options.cloud.dto.OptionsQuery
import biz.belcorp.salesforce.core.entities.OptionEntity
import biz.belcorp.salesforce.core.entities.SubOptionEntity
import biz.belcorp.salesforce.core.utils.readString
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault


object OptionsMockDataHelper {

    fun getOptionsQueryData(): OptionsQuery.Data {
        val jsonString = readString("core/data/options.json")
        return JsonEncodedDefault.decodeFromString(OptionsQuery.Data.serializer(), jsonString)
    }

    fun getMenuOptionsEntities(type: String): List<OptionEntity> {
        return getOptionsQueryData().options
            .filter { it.type == type }
            .map {
                OptionEntity(
                    code = it.code.toLong(),
                    type = it.type,
                    description = it.description,
                    position = it.position,
                    url = it.url,
                    active = it.active
                )
            }
    }

    fun getMenuSubOptionsEntities(parentCode: Int, type: String): List<SubOptionEntity> {
        return getOptionsQueryData()
            .options
            .find { it.code == parentCode }!!
            .subOptions
            .filter { it.type == type }
            .map {
                SubOptionEntity(
                    code = it.code.toLong(),
                    type = it.type,
                    description = it.description,
                    position = it.position,
                    url = it.url,
                    active = it.active
                )
            }
    }

}
