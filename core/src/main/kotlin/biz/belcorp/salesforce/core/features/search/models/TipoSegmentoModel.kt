package biz.belcorp.salesforce.core.features.search.models

import biz.belcorp.salesforce.core.domain.entities.search.TipoSegmento

class TipoSegmentoModel {

    var segmentoInternoId: Int? = null

    var descripcion: String? = null

    var abreviatura: String? = null

    var estadoActivo: String? = null

    fun parse(pojo: TipoSegmento): TipoSegmentoModel {
        this.segmentoInternoId = pojo.segmentoInternoId
        this.descripcion = pojo.descripcion
        this.abreviatura = pojo.abreviatura
        this.estadoActivo = pojo.estadoActivo
        return this
    }
}
