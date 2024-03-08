package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.eventos

import biz.belcorp.salesforce.core.entities.sql.eventos.TipoEventoRddEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.TipoEventoRdd

class TiposEventoRddMapper {

    fun map(it: TipoEventoRddEntity): TipoEventoRdd {
        return TipoEventoRdd(id = it.id,
            descripcion = it.descripcion ?: Constant.EMPTY_STRING,
            compartirObligatorio = it.compartirObligatorio,
            rol = Rol.Builder.construir(it.rol),
            aceptaDescripcionPersonalizada = it.aceptaDescripcionPersonalizada)
    }
}
