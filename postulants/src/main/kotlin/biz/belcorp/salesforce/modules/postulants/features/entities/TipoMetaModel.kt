package biz.belcorp.salesforce.modules.postulants.features.entities

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.tipo.TipoMeta

class TipoMetaModel {

    var idTipoMeta: Int = 0
    var codigoMeta: String? = null
    var descripcion: String? = null
    var montoMinimo: String? = null
    var montoMaximo: String? = null

    fun parse(pojo: TipoMeta): TipoMetaModel {
        this.idTipoMeta = pojo.idTipoMeta
        this.codigoMeta = pojo.codigoMeta
        this.descripcion = pojo.descripcion
        this.montoMaximo = pojo.montoMaximo
        this.montoMinimo = pojo.montoMinimo
        return this
    }

    override fun toString() = descripcion.orEmpty()


}
