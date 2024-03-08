package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultoras

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.entities.sql.consultora.*
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity_Table.*
import biz.belcorp.salesforce.core.utils.appendIf
import biz.belcorp.salesforce.core.utils.notEquals
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.Constants
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.FilterSearchConsultant
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.OrderBy
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*


class ConsultoraDBDataStore {

    fun find(filterParams: FilterSearchConsultant): Observable<List<ConsultoraEntity>> {
        return Observable.create { subscriber ->

            var list = findQuery(filterParams)

            if (list.isNotEmpty()) {
                subscriber.onNext(list)
                subscriber.onComplete()
            } else {
                list = ArrayList()
                subscriber.onNext(list)
                subscriber.onComplete()
            }

        }
    }

    fun findSize(filterParams: FilterSearchConsultant): Observable<Int> {
        return Observable.create { subscriber ->

            val list = findQuery(filterParams)

            subscriber.onNext(list.size)
            subscriber.onComplete()

        }
    }


    fun getConsultant(id: Int): ConsultoraEntity? =
        (select from ConsultoraEntity::class where (ConsultorasId eq id)).querySingle()


    private fun findQuery(filterParams: FilterSearchConsultant): List<ConsultoraEntity> {
        var where = (select from ConsultoraEntity::class where (Codigo eq Codigo))

        filterParams.code.takeIf { !it.isNullOrEmpty() }?.let {
            where = (where and (Codigo like it))
        }

        if (filterParams.name?.isNotEmpty() == true) {
            val words = filterParams.name.split("\\s".toRegex()).dropLastWhile {
                it.isEmpty()
            }.toTypedArray()

            words.forEach { s -> where = (where and (Nombre like "%$s%")) }

        }

        filterParams.document.takeIf { !it.isNullOrEmpty() }?.let {
            where = (where and (DocumentoIdentidad like it))
        }

        filterParams.section.validate(Constants.SEGMENTO_TODOS)?.let {
            where = (where and (Seccion like it))
        }

        filterParams.status.validate(Constant.NUMBER_ONE)?.let {
            where = (where and (Estado eq it))
        }

        filterParams.segment.validate(Constants.SEGMENTO_TODOS)?.let {
            where = if (it == Constants.SEGMENTO_ESTABLECIDA)
                (where and ((Segmento like Constants.SEGMENTO_BRILLA)
                    or (Segmento like Constants.SEGMENTO_EXPERTA)
                    or (Segmento like Constants.SEGMENTO_EMPRESARIA)
                    or (Segmento like Constants.SEGMENTO_ESPECIAL)
                    or (Segmento like Constants.SEGMENTO_ASESORA)))
            else
                (where and (Segmento like "$it%"))
        }

        filterParams.level.validate(Constants.SEGMENTO_TODOS)?.let {
            where = (where and (SegmentoInternoFFVV like "$it%"))
        }

        filterParams.requested.validate(Constants.NUMERO_DOS)?.let {
            where = (where and (Pedido eq it))
        }

        filterParams.authorized.validate(Constants.AUTORIZADO_TODO)?.let {
            where = when (it) {
                Constants.AUTORIZADO_SI -> (where and (Autoriza like Constants.YES_SIN_TILDE))
                else -> (where and (Autoriza like Constants.NO))
            }
        }

        filterParams.residue.validate(Constants.NUMERO_DOS)?.let {
            where = when (it) {
                Constants.NUMERO_CERO -> (where and (FlagDeuda lessThanOrEq Constants.NUMERO_CERO))
                else -> (where and (FlagDeuda greaterThan Constants.NUMERO_CERO))
            }
        }

        filterParams.type.validate(Constants.TYPE_TODO)?.let {
            where = when (it) {
                Constants.TYPE_HAS_CASH_PAYMENT -> where and (HasCashPayment eq true)
                else -> where and (HasCashPayment eq false)
            }
        }

        return (where orderBy Nombre.asc()).queryList()
    }

    fun consultoras(
        sectionCode: String?,
        idIndicator: Int,
        idList: Int,
        order: Int
    ): Observable<List<ConsultoraEntity>> {
        return Observable.create { subscriber ->

            try {
                var list: List<ConsultoraEntity>? = null

                if (isStringValid(sectionCode) && idIndicator > 0 && idList > 0) {
                    val query = (select from ConsultoraEntity::class).let {
                        if (order == 0) {
                            it.orderBy(PrimerApellido.asc())
                            it.orderBy(SegundoApellido.asc())
                            it.orderBy(PrimerNombre.asc())
                            it.orderBy(SegundoNombre.asc())
                        } else {
                            it.orderBy(PrimerNombre.asc())
                            it.orderBy(SegundoNombre.asc())
                            it.orderBy(PrimerApellido.asc())
                            it.orderBy(SegundoApellido.asc())
                        }
                    }
                    list = query.queryList()
                }

                if (list?.isEmpty() == false) {
                    subscriber.onNext(list)
                    subscriber.onComplete()
                } else {
                    list = ArrayList()
                    subscriber.onNext(list)
                    subscriber.onComplete()
                }

            } catch (e: Exception) {
                subscriber.onError(Exception(e.cause ?: Throwable(e.message ?: "")))
            }

        }
    }

    fun getBeautyConsultantsByLevel(nivel: String, seccion: String):
        Single<List<ConsultoraEntity>> {
        return Single.create { subscriber ->
            try {
                val list = mutableListOf<ConsultoraEntity>()
                val result = when {
                    nivel.isBlank() && seccion.isNotBlank() ->
                        queryPDVBySection(seccion)
                    nivel.isNotBlank() && seccion.isBlank() ->
                        queryPDVByLevel(nivel)
                    nivel.isNotBlank() && seccion.isNotBlank() ->
                        queryPDVBySectionAndLevel(seccion, nivel)
                    else -> queryPDV().queryList()
                }
                list.addAll(result)
                subscriber.onSuccess(list)
            } catch (e: Exception) {
                subscriber.onError(Exception(e.cause ?: Throwable(e.message ?: "")))
            }
        }
    }

    private fun queryPDV() =
        (select from ConsultoraEntity::class).where(NivelPDV.notEq(EMPTY_STRING))

    private fun queryPDVBySection(section: String) =
        queryPDV().and(Seccion eq section).queryList()

    private fun queryPDVByLevel(level: String) =
        queryPDV().and(NivelPDV eq level).queryList()

    private fun queryPDVBySectionAndLevel(section: String, level: String) =
        (queryPDV().and(NivelPDV eq level) and (Seccion eq section)).queryList()

    fun consultantWithPossibleLevelChange(seccion: String): Single<List<ConsultoraPosibleCambioNivelJoined>> {
        return Single.create { subscriber ->

            try {
                val list = mutableListOf<ConsultoraPosibleCambioNivelJoined>()

                val where = (Select(
                    Nombre.withTable(),
                    TelefonoCasa.withTable(),
                    TelefonoCelular.withTable(),
                    ConsultorasId.withTable(),
                    Codigo.withTable(),
                    DigVerif.withTable(),
                    ConsultorasPdVPosibleCambioNivelEntity_Table.monto.withTable(),
                    ConsultorasPdVPosibleCambioNivelEntity_Table.seccion.withTable(),
                    ConsultorasPdVPosibleCambioNivelEntity_Table.nivelActual.withTable(),
                    ConsultorasPdVPosibleCambioNivelEntity_Table.nivelSiguiente.withTable()
                )
                    from ConsultoraEntity::class innerJoin ConsultorasPdVPosibleCambioNivelEntity::class
                    on (ConsultorasId eq ConsultorasPdVPosibleCambioNivelEntity_Table.consultoraID)
                    where (ConsultorasPdVPosibleCambioNivelEntity_Table.seccion.`as`("ConsultorasPdVPosibleCambioNivel.Seccion") like seccion)
                    orderBy (OrderBy.fromProperty(ConsultorasPdVPosibleCambioNivelEntity_Table.monto)
                    .descending()))

                list.addAll(where.queryCustomList(ConsultoraPosibleCambioNivelJoined::class.java))

                subscriber.onSuccess(list)

            } catch (e: Exception) {
                subscriber.onError(Exception(e.cause ?: Throwable(e.message.orEmpty())))
            }
        }
    }

    fun consultantsEndPeriod(
        level: String,
        seccion: String
    ): Single<List<ConsultoraEndPeriodJoined>> {
        return Single.create { subscriber ->

            try {
                val list = mutableListOf<ConsultoraEndPeriodJoined>()

                val where = (Select(
                    Nombre.withTable(),
                    TelefonoCasa.withTable(),
                    TelefonoCelular.withTable(),
                    ConsultorasId.withTable(),
                    Codigo.withTable(),
                    DigVerif.withTable(),
                    ConsultorasPdVCierrePeriodoEntity_Table.seccion.withTable(),
                    ConsultorasPdVCierrePeriodoEntity_Table.nivel.withTable()
                )
                    from ConsultoraEntity::class innerJoin ConsultorasPdVCierrePeriodoEntity::class
                    on (ConsultorasId eq ConsultorasPdVCierrePeriodoEntity_Table.consultoraID)
                    where (ConsultorasPdVCierrePeriodoEntity_Table.seccion.`as`("ConsultorasPdVCierrePeriodo.seccion") like seccion)
                    and (ConsultorasPdVCierrePeriodoEntity_Table.nivel.`as`("ConsultorasPdVCierrePeriodo.nivel") like level))

                list.addAll(where.queryCustomList(ConsultoraEndPeriodJoined::class.java))

                subscriber.onSuccess(list)

            } catch (e: Exception) {
                subscriber.onError(Exception(e.cause ?: Throwable(e.message.orEmpty())))
            }
        }
    }

    private fun isStringValid(string: String?): Boolean {
        return string != null && string.isNotEmpty()
    }

    private fun Int?.validate(invalidValue: Int?): Int? {
        return takeIf { this != null && invalidValue?.let { this != it } ?: true }
    }

    private fun String?.validate(invalidValue: String? = null): String? {
        return takeIf { !this.isNullOrEmpty() && invalidValue?.notEquals(this) ?: true }
    }

}
