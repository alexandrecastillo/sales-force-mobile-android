package biz.belcorp.salesforce.core.features.search.models

import biz.belcorp.salesforce.core.domain.entities.search.TipoEstado

class TipoEstadoModel {

    var idEstadoActividad: Int? = null

    var descripcion: String? = null

    var estadoActivo: Int = 0

    fun parse(pojo: TipoEstado): TipoEstadoModel {
        this.idEstadoActividad = pojo.idEstadoActividad
        this.descripcion = pojo.descripcion
        this.estadoActivo = pojo.estadoActivo
        return this
    }

}
