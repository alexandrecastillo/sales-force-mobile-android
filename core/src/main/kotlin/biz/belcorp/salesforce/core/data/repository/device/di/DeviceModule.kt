package biz.belcorp.salesforce.core.data.repository.device.di

import biz.belcorp.salesforce.core.data.repository.device.DeviceDataRepository
import biz.belcorp.salesforce.core.data.repository.device.cloud.DeviceCloudStore
import biz.belcorp.salesforce.core.di.DEVICE_UUID
import biz.belcorp.salesforce.core.domain.repository.device.DeviceRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val deviceModule = module {
    factory { DeviceCloudStore(get()) }
    factory<DeviceRepository> { DeviceDataRepository(get(), get(), get(named(DEVICE_UUID))) }
}
