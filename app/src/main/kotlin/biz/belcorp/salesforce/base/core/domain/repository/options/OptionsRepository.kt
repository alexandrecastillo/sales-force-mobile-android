package biz.belcorp.salesforce.base.core.domain.repository.options

import biz.belcorp.salesforce.core.domain.entities.options.Option
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA


interface OptionsRepository {

    suspend fun sync(countryIso: String, rol: String, llaveUA: LlaveUA)

    suspend fun getMenuOptions(): List<Option>

    suspend fun getMenuSubOptions(parentCode: Int): List<Option>

    suspend fun getShortcutOptions(): List<Option>

}
