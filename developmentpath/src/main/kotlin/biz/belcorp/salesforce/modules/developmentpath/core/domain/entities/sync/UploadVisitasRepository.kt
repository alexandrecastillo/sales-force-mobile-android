package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync

import io.reactivex.Completable

interface UploadVisitasRepository {

    fun syncUpRegisterVisits(): Completable

    fun syncUpAdditionalVisits(): Completable

    fun enviarInsercionesPendientes(): Completable

    fun enviarActualizacionesPendientes(): Completable

    fun enviarVisitasPorFecha(): Completable

    fun sendRegisterAdditionalVisits(): Completable
}
