package biz.belcorp.salesforce.modules.postulants.core.domain.exception

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse

class PostulanteBloqueadaException(val buroResponse: BuroResponse) : Exception()
