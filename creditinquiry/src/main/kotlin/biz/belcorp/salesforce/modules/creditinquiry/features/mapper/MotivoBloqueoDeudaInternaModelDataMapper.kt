package biz.belcorp.salesforce.modules.creditinquiry.features.mapper

import biz.belcorp.salesforce.modules.creditinquiry.features.model.MotivoBloqueoDeudaInterna
import biz.belcorp.salesforce.modules.creditinquiry.features.model.MotivoBloqueoDeudaInternaModel
import java.util.*

class MotivoBloqueoDeudaInternaModelDataMapper {

    fun parseMotivoBloqueoDeudaInterna(entity: MotivoBloqueoDeudaInterna?): MotivoBloqueoDeudaInternaModel? {
        var motivoBloqueoDeudaInterna: MotivoBloqueoDeudaInternaModel? = null

        if (entity != null) {
            motivoBloqueoDeudaInterna = MotivoBloqueoDeudaInternaModel()
            motivoBloqueoDeudaInterna.motivoBloqueo = entity.motivoBloqueo
            motivoBloqueoDeudaInterna.observacion = entity.observacion
            motivoBloqueoDeudaInterna.tipoBloqueo = entity.tipoBloqueo
        }

        return motivoBloqueoDeudaInterna
    }

    fun parseMotivoBloqueoDeudaInternaList(collection: Collection<MotivoBloqueoDeudaInterna>?): List<MotivoBloqueoDeudaInternaModel> {
        val list = ArrayList<MotivoBloqueoDeudaInternaModel>()

        collection?.forEach { entity ->
            val consultaCrediticia = parseMotivoBloqueoDeudaInterna(entity)
            if (consultaCrediticia != null) {
                list.add(consultaCrediticia)
            }
        }

        return list
    }
}
