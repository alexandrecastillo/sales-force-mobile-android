package biz.belcorp.salesforce.modules.postulants.core.domain.usecases


import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.DetalleIndicador
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.DetalleIndicadorUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.IndicadorUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.IndicadorRepository

class IndicadorUseCase(
    private val indicadorRepository: IndicadorRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun getUneteCloud(
        pais: String, zona: String, region: String, rol: String, value: String?,
        observer: BaseSingleObserver<List<IndicadorUnete>>
    ) {
        val observable =
            this.indicadorRepository.indicadorUneteCloud(pais, zona, region, rol, value)
        execute(observable, observer)
    }

    fun detalleIndicadorCloud(
        pais: String, zona: String, region: String, codRol: String,
        observer: BaseSingleObserver<List<DetalleIndicador>>
    ) {
        val single = indicadorRepository.getDetalleIndicadorCloudUnete(pais, zona, region, codRol)
            .map { result ->
                val list = mutableListOf<DetalleIndicador>()

                result.map {
                    list.add(it)
                }

                list.toList()
            }
        execute(single, observer)
    }

    fun saveIndicadorUnete(listaIndicador: List<IndicadorUnete>, observer: BaseObserver<Boolean>) {
        val observable = indicadorRepository.saveIndicadorUnete(listaIndicador)
        execute(observable, observer)
    }

    fun getUneteDB(rol: String, value: String, observer: BaseObserver<IndicadorUnete>) {
        val observable = indicadorRepository.indicadorUneteDB(rol, value)
        execute(observable, observer)
    }

    fun saveDetalleIndicador(
        listaDetalleIndicador: List<DetalleIndicadorUnete>, observer: BaseObserver<Boolean>
    ) {
        val observable = indicadorRepository.saveDetalleIndicadorUnete(listaDetalleIndicador)
        execute(observable, observer)
    }

    fun detalleIndicador(
        pais: String, zona: String, region: String, codRol: String,
        observer: BaseSingleObserver<List<DetalleIndicador>>
    ) {
        val single =
            indicadorRepository.getDetalleIndicadorUnete(pais, zona, region, codRol).map { result ->
                val list = mutableListOf<DetalleIndicador>()

                result.map { i ->
                    list.add(i)
                }

                list.toList()
            }
        execute(single, observer)
    }

    fun getSecciones(observer: BaseObserver<List<Seccion>>) {
        val observable = this.indicadorRepository.sections().toObservable()
        execute(observable, observer)
    }

}
