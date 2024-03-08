package biz.belcorp.salesforce.base.core.data.repository.options

import biz.belcorp.salesforce.base.core.data.repository.options.cloud.OptionsCloudStore
import biz.belcorp.salesforce.base.core.data.repository.options.cloud.dto.OptionsQuery
import biz.belcorp.salesforce.base.core.data.repository.options.data.OptionsDataStore
import biz.belcorp.salesforce.base.core.data.repository.options.mappers.OptionsMapper
import biz.belcorp.salesforce.base.core.domain.repository.options.OptionsRepository
import biz.belcorp.salesforce.core.domain.entities.options.Option
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA


class OptionsDataRepository(
    private val optionsCloudStore: OptionsCloudStore,
    private val optionsDataStore: OptionsDataStore,
    private val optionsMapper: OptionsMapper
) : OptionsRepository {

    companion object {

        private const val TYPE_MP = "MP"
        private const val TYPE_ML = "ML"
        private const val TYPE_SC = "SC"

        private const val TYPE_MP_LIMIT = 5

    }

    override suspend fun sync(countryIso: String, rol: String,
                              llaveUA: LlaveUA) {
        val query = OptionsQuery(countryIso, rol, llaveUA.codigoRegion.orEmpty(),
            llaveUA.codigoZona.orEmpty(), llaveUA.codigoSeccion.orEmpty())
        val data = optionsCloudStore.getOptions(query)
        val (options, subOptions) = optionsMapper.map(data)
        optionsDataStore.saveOptions(options)
        optionsDataStore.saveSubOptions(subOptions)
    }

    override suspend fun getMenuOptions(): List<Option> {
        val options = optionsDataStore.getOptions(TYPE_MP)
        return optionsMapper.map(options.take(TYPE_MP_LIMIT))
    }

    override suspend fun getMenuSubOptions(parentCode: Int): List<Option> {
        val options = optionsDataStore.getSubOptions(parentCode, TYPE_ML)
        return optionsMapper.mapSubOptions(options)
    }

    override suspend fun getShortcutOptions(): List<Option> {
        val options = optionsDataStore.getOptions(TYPE_SC)
        return optionsMapper.map(options)
    }

}
