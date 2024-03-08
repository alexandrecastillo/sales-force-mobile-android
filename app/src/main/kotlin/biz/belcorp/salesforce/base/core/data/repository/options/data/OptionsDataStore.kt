package biz.belcorp.salesforce.base.core.data.repository.options.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.entities.OptionEntity
import biz.belcorp.salesforce.core.entities.OptionEntity_
import biz.belcorp.salesforce.core.entities.SubOptionEntity
import biz.belcorp.salesforce.core.entities.SubOptionEntity_
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor


class OptionsDataStore {

    fun saveOptions(options: List<OptionEntity>) {
        boxStore.boxFor<OptionEntity>()
            .deleteAndInsert(options)
    }

    fun saveSubOptions(subOptions: List<SubOptionEntity>) {
        boxStore.boxFor<SubOptionEntity>()
            .deleteAndInsert(subOptions)
    }

    fun getOptions(type: String): List<OptionEntity> {
        return boxStore.boxFor<OptionEntity>().query()
            .equal(OptionEntity_.type, type)
            .and()
            .equal(OptionEntity_.active, true)
            .order(OptionEntity_.position)
            .build()
            .find()
    }

    fun getSubOptions(parentCode: Int, type: String): List<SubOptionEntity> {
        return boxStore.boxFor<SubOptionEntity>().query()
            .equal(SubOptionEntity_.type, type)
            .and()
            .equal(SubOptionEntity_.active, true)
            .and()
            .equal(SubOptionEntity_.optionParentCode, parentCode.toLong())
            .order(SubOptionEntity_.position)
            .build()
            .find()
    }

}
