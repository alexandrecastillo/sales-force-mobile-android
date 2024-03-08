package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.kinesis

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.kinesis.KinesisManagerCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.kinesis.KinesisManagerRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.kinesis.KinesisManagerUseCase

class KinesisManagerDataRepository(private val kinesisManangerCloud: KinesisManagerCloudDataStore)
    : KinesisManagerRepository {

    override fun enviarDatosGrabados(logKinesis: KinesisManagerUseCase.LogKinesis): String {
        return kinesisManangerCloud.enviarDatosGrabados(logKinesis)
    }

}
