package biz.belcorp.salesforce.modules.consultants.features.list

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import biz.belcorp.salesforce.base.utils.isGz
import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.BeautyConsultantConstants
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.BuityConsultantViewType
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.Consultora
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.consultora.ConsultoraUseCase
import biz.belcorp.salesforce.modules.consultants.features.list.mappers.ConsultoraModelDataMapper
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultoraModel
import biz.belcorp.salesforce.modules.consultants.features.list.models.Indicator
import biz.belcorp.salesforce.modules.consultants.features.list.utils.GridBuilder
import biz.belcorp.salesforce.modules.consultants.features.search.mappers.mapToConsutlantFilter
import biz.belcorp.salesforce.modules.consultants.features.search.models.FiltroConsultoraModel
import java.util.*

class ConsultantsListPresenter(
    private val view: ConsultantsListView,
    private val gridBuilder: GridBuilder,
    private val useCase: ConsultoraUseCase,
    private val sessionManager: ObtenerSesionUseCase,
    private val mapper: ConsultoraModelDataMapper,
    private val configurationUseCase: ConfigurationUseCase
) : BasePresenter() {

    private val session get() = sessionManager.obtener()

    private var prevUASelected = ""
    private var prevCampaign = ""

    private var indicatorId = -1
    private var consultantListId = -1
    private var consultantTypeItem = 0

    private var filtroBusqueda: FiltroConsultoraModel? = null

    private val items = mutableListOf<ConsultoraModel>()

    fun setConsultantTypeItem(consultantListId: Int) {
        this.consultantTypeItem = consultantListId
    }

    override fun onDestroy() {
        super.onDestroy()
        useCase.dispose()
    }

    fun getSymbol() {
        doAsync {
            val moneda = configurationUseCase.getConfiguration().currencySymbol
            uiThread {
                handleCurrencySymbol(moneda)
                iniciarBusqueda()
            }
        }
    }

    fun setUaIdSelected(uaId: String) {
        this.prevUASelected = uaId
    }

    fun setConsultantListId(consultantListId: Int) {
        this.consultantListId = consultantListId
        handleConsultantTypeView(this.consultantListId)
    }

    fun setFiltroBusqueda(filtroBusqueda: FiltroConsultoraModel?) {
        this.filtroBusqueda = filtroBusqueda
    }

    fun iniciarBusqueda() {
        if (filtroBusqueda != null) {
            view.setHightlightName(filtroBusqueda?.name)
            view.activeFiltroBusqueda()
            obtenerResultados(filtroBusqueda!!)
        } else {
            handleConsultantsTypeList()
        }
    }

    private fun handleConsultantTypeView(consultantListId: Int) {
        this.consultantTypeItem = when (consultantListId) {
            gridBuilder.posibleCambiosDeNivelId -> BuityConsultantViewType.POSIBLES_LEVEL_CHANGES_CONSULTANT_VIEW
            gridBuilder.cambiosDeNivelId -> BuityConsultantViewType.POSIBLES_LEVEL_CHANGES_CONSULTANT_VIEW
            gridBuilder.finPeriodolId -> BuityConsultantViewType.END_PERIOD_CONSULTANT_VIEW
            else -> BuityConsultantViewType.REGULAR_BEAUTY_CONSULTANT_VIEW
        }
    }

    private fun handleConsultantsTypeList() {
        when (consultantTypeItem) {
            BuityConsultantViewType.POSIBLES_LEVEL_CHANGES_CONSULTANT_VIEW -> getPossibleLevelChanges()
            BuityConsultantViewType.GROWN_BEAUTY_CONSULTANT_VIEW -> getGrownBeautyConsultants()
            BuityConsultantViewType.END_PERIOD_CONSULTANT_VIEW -> getEndPeriodConsultants()
            else -> getAllBeautyConsultants()
        }
    }

    private fun obtenerResultados(filtroBusqueda: FiltroConsultoraModel) {
        useCase.find(filtroBusqueda.mapToConsutlantFilter(), ConsultantListSubscriber())
    }

    private fun getGrownBeautyConsultants() {
        useCase.getBeautyConsultants(prevUASelected, ConsultantListSubscriber())
    }

    private fun getPossibleLevelChanges() {
        when {
            session.rol.isGz() -> useCase.getPossibleLevelChanges(
                ConsultantListSubscriber(),
                prevUASelected
            )
            else -> useCase.getPossibleLevelChanges(ConsultantListSubscriber())
        }
    }

    fun getEndPeriodConsultants(nivel: String = "%", section: String = "%") {
        useCase.getEndperiod(
            level = nivel,
            section = section,
            observer = ConsultantListSubscriber()
        )
    }

    private fun getAllBeautyConsultants() {
        useCase.consultoras(
            prevUASelected,
            indicatorId,
            consultantListId,
            ConsultantListSubscriber()
        )
    }

    private fun handleConsultantsCollection(consultants: List<Consultora>) = with(view) {
        val collection: List<ConsultoraModel> =
            if (indicatorId == Indicator.CambioNivel.indicatorId) {
                orderListForIndicatorLvl(consultants)
            } else {
                consultants.map { parseToConsultantModel(it) }
            }
        items.clear()
        items.addAll(collection)

        if (items.isNotEmpty()) {
            hideNonDataAvailable()
            setBeautyConsultantTypeView(consultantTypeItem)
            showConsultantList(items.sortedBy { it.nombre })
        }

        handleConsultantViews()
    }

    private fun orderListForIndicatorLvl(items: List<Consultora>): List<ConsultoraModel> {
        val itemList = mutableListOf<ConsultoraModel>()
        val consultantsWithIntLevel = items.map {
            it.nivelInt =
                BeautyConsultantConstants.LEVEL_MAP[it.nivel?.toUpperCase(Locale.getDefault())]
            it
        }

        val sortedConsultants = consultantsWithIntLevel
            .asSequence()
            .sortedBy { it.nombre }
            .sortedBy { it.monto }
            .sortedBy { it.nivelInt }
            .sortedByDescending { it.constancia }
            .map { parseToConsultantModel(it) }

        itemList.addAll(sortedConsultants)
        return itemList
    }

    private fun parseToConsultantModel(c: Consultora) =
        mapper.parseConsultant(c)

    private fun handleConsultantViews() {
        when (consultantTypeItem) {
            BuityConsultantViewType.POSIBLES_LEVEL_CHANGES_CONSULTANT_VIEW -> handlePosiblesGrownView()
            BuityConsultantViewType.GROWN_BEAUTY_CONSULTANT_VIEW -> handleGrownConsultantView()
            else -> handleRegularView()
        }
    }

    private fun handlePosiblesGrownView() = with(view) {
        if (items.isNotEmpty()) {
            showPossibleChangeConsultantsTitle()
        } else {
            hidePossibleChangeConsultantsTitle()
            showNonGrownConsultantDataAvailable()
        }
    }

    private fun handleRegularView() = with(view) {
        if (items.isNotEmpty()) {
            hideNonDataAvailable()
        } else {
            showNonDataAvailable()
        }
    }

    private fun handleGrownConsultantView() {
        if (items.isNotEmpty()) {
            view.showPossibleChangeConsultantsTitle()
            handleConsultantsAmount(items.size)
        } else {
            view.hidePossibleChangeConsultantsTitle()
            view.showNonGrownConsultantDataAvailable()
        }
    }

    private fun handleConsultantsAmount(amount: Int) {
        val beautyConsultantAmount = "$amount Consultoras cambiando de Nivel"
        val beautyConsultantsInfo = "$beautyConsultantAmount en $prevCampaign"

        val txtSpannable = SpannableString(beautyConsultantsInfo)
        val boldSpan = StyleSpan(Typeface.BOLD)

        txtSpannable.setSpan(
            boldSpan,
            0,
            beautyConsultantAmount.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        view.showBeautyConsultantAmountByConstancyInfo(txtSpannable)
    }


    private fun handleCurrencySymbol(currencySymbol: String?) {
        view.setCurrencySymbol(currencySymbol ?: "")
    }

    fun getBeautyConsultantsByNivel(nivel: String, section: String, typeSelection: Int) {
        if (typeSelection == BuityConsultantViewType.END_PERIOD_CONSULTANT_VIEW) {
            val endLevel = if (nivel.trim().isEmpty()) {
                "%"
            } else {
                nivel
            }

            getEndPeriodConsultants(nivel = endLevel, section = section)
            return
        }

        if (nivel.isBlank()) {
            useCase.getBeautyConsultantsByLevel(
                section = section,
                observer = ConsultantListSubscriber()
            )
        } else {
            useCase.getBeautyConsultantsByLevel(nivel, section, ConsultantListSubscriber())
        }
    }

    inner class ConsultantListSubscriber : BaseObserver<List<Consultora>>() {
        override fun onNext(t: List<Consultora>) {
            handleConsultantsCollection(t)
        }
    }

}
