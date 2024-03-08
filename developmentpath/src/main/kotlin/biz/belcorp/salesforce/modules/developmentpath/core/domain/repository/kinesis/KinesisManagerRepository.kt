package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.kinesis

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.kinesis.KinesisManagerUseCase

interface KinesisManagerRepository {
    fun enviarDatosGrabados(logKinesis: KinesisManagerUseCase.LogKinesis): String
}
