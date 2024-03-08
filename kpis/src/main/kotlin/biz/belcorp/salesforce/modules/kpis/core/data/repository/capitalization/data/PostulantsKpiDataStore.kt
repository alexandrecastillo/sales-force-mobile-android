package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.capitalization.PostulantKpiEntity
import biz.belcorp.salesforce.core.entities.capitalization.PostulantKpiEntity_
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor

class PostulantsKpiDataStore {

    fun savePostulantsKpi(items: List<PostulantKpiEntity>) {
        boxStore
            .boxFor<PostulantKpiEntity>()
            .deleteAndInsert(items)
    }

    fun updatePostulant(entity: PostulantKpiEntity) {
        boxStore
            .boxFor<PostulantKpiEntity>()
            .put(entity)
    }

    fun getPostulantsKpi(ua: LlaveUA): List<PostulantKpiEntity> = with(ua) {
        return boxStore.boxFor<PostulantKpiEntity>().query()
            .equal(PostulantKpiEntity_.region, codigoRegion.orEmpty().deleteHyphen())
            .and().equal(PostulantKpiEntity_.zone, codigoZona.orEmpty().deleteHyphen())
            .and().equal(PostulantKpiEntity_.section, codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .find()
    }

}
