package biz.belcorp.salesforce.core.domain.entities.people

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import java.io.Serializable


class PersonIdentifier(
    val id: Long,
    val code: String,
    val role: Rol
) : Serializable
