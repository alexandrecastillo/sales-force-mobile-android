package biz.belcorp.salesforce.modules.postulants.utils

import biz.belcorp.salesforce.core.data.exceptions.ErrorException
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.ResponseAPI
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.ResultadoAPI
import io.reactivex.Single

internal fun <T> Single<ResponseAPI<T>>.capturarErrorApi(): Single<ResultadoAPI<T>> {
    return map {
        if (it.resultado?.codigo == "55555" || it.resultado == null) {
            Logger.d("UNETE: " + it.name, it.resultado.toString())
            throw ErrorException(it.resultado?.mensaje)
        } else {
            it.resultado!!
        }
    }
}
