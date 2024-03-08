package biz.belcorp.salesforce.base.core.data.repository.options.mappers

import biz.belcorp.salesforce.base.core.data.repository.options.cloud.dto.OptionsQuery
import biz.belcorp.salesforce.core.domain.entities.options.Option
import biz.belcorp.salesforce.core.entities.OptionEntity
import biz.belcorp.salesforce.core.entities.SubOptionEntity


class OptionsMapper {

    fun map(data: OptionsQuery.Data): Pair<List<OptionEntity>, List<SubOptionEntity>> {
        val optionsList = mutableListOf<OptionEntity>()
        val subOptionsList = mutableListOf<SubOptionEntity>()
        data.options.forEach { option ->
            val optionEntity = map(option)
            val subOptions = option.subOptions.map { subOption ->
                map(subOption, optionEntity)
            }
            subOptionsList.addAll(subOptions)
            optionsList.add(optionEntity)
        }
        return Pair(optionsList, subOptionsList)
    }

    private fun map(option: OptionsQuery.Data.Option): OptionEntity {
        return OptionEntity(
            code = option.code.toLong(),
            type = option.type,
            description = option.description,
            position = option.position,
            url = option.url,
            active = option.active
        )
    }

    private fun map(
        option: OptionsQuery.Data.Option.SubOption,
        optionParent: OptionEntity? = null
    ): SubOptionEntity {
        val subOptionEntity = SubOptionEntity(
            code = option.code.toLong(),
            type = option.type,
            description = option.description,
            position = option.position,
            url = option.url,
            active = option.active
        )
        subOptionEntity.optionParent.target = optionParent
        return subOptionEntity
    }

    fun map(entities: List<OptionEntity>): List<Option> {
        return entities.map { map(it) }
    }

    private fun map(entity: OptionEntity): Option {
        return Option(
            code = entity.code,
            description = entity.description,
            position = entity.position,
            url = entity.url
        )
    }

    fun mapSubOptions(entities: List<SubOptionEntity>): List<Option> {
        return entities.map { map(it) }
    }

    private fun map(entity: SubOptionEntity): Option {
        return Option(
            code = entity.code,
            description = entity.description,
            position = entity.position,
            url = entity.url
        )
    }

}
