package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.ingresosextra

import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaDetalleEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.ingresosextra.data.IngresosExtraLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ingresosextra.OtraMarca
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Constants

class OtraMarcaEntityMapper(private val localDataStore: IngresosExtraLocalDataStore) :
    Mapper<OtraMarcaDetalleEntity, OtraMarca>() {

    override fun map(value: OtraMarcaDetalleEntity): OtraMarca {
        val marca = localDataStore.obtenerMarcaPorId(value.marcaId)
        return OtraMarca(
            marcaDetalleId = value.marcaDetalleId,
            marcaId = value.marcaId,
            consultoraId = value.consultoraId,
            codigoRegion = value.region,
            codigoZona = value.zona,
            codigoSeccion = value.seccion,
            name = marca?.name,
            iconoId = marca?.iconoId ?: Constants.MENOS_UNO,
            orden = marca?.orden ?: Constants.CERO,
            showFront = marca?.showFront ?: false,
            checked = value.checked,
            categoria = value.categoria,
            campania = value.campania
        )
    }

    override fun reverseMap(value: OtraMarca): OtraMarcaDetalleEntity {
        val entity = OtraMarcaDetalleEntity()
        entity.marcaDetalleId = value.marcaDetalleId
        entity.marcaId = value.marcaId
        entity.consultoraId = value.consultoraId
        entity.region = value.codigoRegion
        entity.zona = value.codigoZona
        entity.seccion = value.codigoSeccion
        entity.checked = value.checked
        entity.enviado = false
        entity.categoria = value.categoria
        entity.campania = value.campania
        return entity
    }
}
