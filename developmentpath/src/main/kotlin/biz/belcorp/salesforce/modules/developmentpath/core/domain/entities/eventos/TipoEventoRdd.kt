package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class TipoEventoRdd(
    val id: Long,
    val descripcion: String,
    val compartirObligatorio: Boolean,
    val rol: Rol,
    val aceptaDescripcionPersonalizada: Boolean
) {
    val permiteCompartir get() = rol != Rol.SOCIA_EMPRESARIA
}
