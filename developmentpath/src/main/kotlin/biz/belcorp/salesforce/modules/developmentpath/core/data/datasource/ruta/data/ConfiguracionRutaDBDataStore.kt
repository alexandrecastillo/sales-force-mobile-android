package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data

import biz.belcorp.salesforce.core.entities.sql.path.ConfiguracionRDDEntity
import biz.belcorp.salesforce.core.entities.sql.path.ConfiguracionRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta.ConfiguracionRutaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.parametros.ParametrosRdd
import com.raizlabs.android.dbflow.kotlinextensions.*

class ConfiguracionRutaDBDataStore(val mapper: ConfiguracionRutaMapper) {

    fun get(rol: Rol): ParametrosRdd? {
        val result = (select
            from ConfiguracionRDDEntity::class
            where
            (ConfiguracionRDDEntity_Table.Rol.withTable() eq rol.codigoRol))

        return mapper.parseToConfiguracion(result.querySingle())
    }

    fun get(planId: Long): ParametrosRdd? {
        val result = (select
            from ConfiguracionRDDEntity::class
            innerJoin PlanRutaRDDEntity::class
            on (ConfiguracionRDDEntity_Table.Rol.withTable() eq
            PlanRutaRDDEntity_Table.Rol.withTable())
            where
            (PlanRutaRDDEntity_Table.ID.withTable() eq planId.toInt()))

        return mapper.parseToConfiguracion(result.querySingle())
    }

}
