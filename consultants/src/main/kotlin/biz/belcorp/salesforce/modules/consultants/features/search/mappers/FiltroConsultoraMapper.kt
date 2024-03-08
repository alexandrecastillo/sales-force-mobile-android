package biz.belcorp.salesforce.modules.consultants.features.search.mappers

import biz.belcorp.salesforce.modules.consultants.core.domain.entities.FilterSearchConsultant
import biz.belcorp.salesforce.modules.consultants.features.search.models.FiltroConsultoraModel


fun FiltroConsultoraModel.mapToConsutlantFilter() = FilterSearchConsultant(
    code = this.code,
    name = this.name,
    document = this.document,
    section = this.section,
    status = this.status,
    segment = this.segment,
    requested = this.requested,
    authorized = this.authorized,
    residue = this.residue,
    type = this.type,
    level = this.level,
    limit = this.limit,
    offset = this.offset
)
