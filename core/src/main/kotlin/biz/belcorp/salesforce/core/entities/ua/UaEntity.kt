package biz.belcorp.salesforce.core.entities.ua

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class UaEntity constructor(
    var id: Long = 0L,
    @Id(assignable = true)
    var code: Long = 0L,
    var description: String? = null,
    var country: String? = null,
    var consultantId: String? = Constant.EMPTY_STRING,
    var consultantName: String? = null,
    var region: String? = null,
    var zone: String? = null,
    var section: String? = null,
    var isCovered: Boolean = true,
    var rol: String = Rol.NINGUNO.codigoRol
) {

    @Backlink(to = "uaParent")
    var uaChildren: ToMany<UaEntity> = ToMany(this, UaEntity_.uaChildren)

    @TargetIdProperty("uaParentCode")
    var uaParent: ToOne<UaEntity> = ToOne(this, UaEntity_.uaParent)
}
