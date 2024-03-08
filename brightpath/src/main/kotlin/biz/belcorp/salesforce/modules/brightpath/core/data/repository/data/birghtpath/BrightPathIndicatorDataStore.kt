package biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.birghtpath

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.consultora.IndicadorCambioNivelEntity
import biz.belcorp.salesforce.core.entities.sql.consultora.IndicadorCambioNivelEntity_Table
import biz.belcorp.salesforce.core.utils.eqNullable
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.IndicatorDBDataStore
import com.raizlabs.android.dbflow.kotlinextensions.and
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.where

class BrightPathIndicatorDataStore : IndicatorDBDataStore<IndicadorCambioNivelEntity>() {

    override fun query(uaKey: LlaveUA) = (select from IndicadorCambioNivelEntity::class
        where (IndicadorCambioNivelEntity_Table.region eqNullable uaKey.codigoRegion)
        and (IndicadorCambioNivelEntity_Table.zona eqNullable uaKey.codigoZona)
        and (IndicadorCambioNivelEntity_Table.seccion eqNullable uaKey.codigoSeccion))

}
