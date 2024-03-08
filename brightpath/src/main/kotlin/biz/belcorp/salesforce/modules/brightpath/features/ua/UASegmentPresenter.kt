package biz.belcorp.salesforce.modules.brightpath.features.ua

import biz.belcorp.salesforce.base.utils.isDv
import biz.belcorp.salesforce.base.utils.isGr
import biz.belcorp.salesforce.base.utils.isGz
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMERO_CERO
import biz.belcorp.salesforce.core.constants.Constant.UNO_NEGATIVO
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.brightpath.R
import biz.belcorp.salesforce.modules.brightpath.core.domain.entities.UASegment
import biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.ua.UASegmentUseCase

class UASegmentPresenter(
    private val useCase: UASegmentUseCase,
    sessionManager: ObtenerSesionUseCase
) {

    private val rol = sessionManager.obtener().rol

    private var uaSegmentView: UASegmentView? = null

    private val currentUserLogged = UNO_NEGATIVO
    private val selectableItems = mutableMapOf<Int, Boolean>()
    private val uaSegmentList = mutableListOf<UASegmentModel>()

    private var currentUASelected = EMPTY_STRING
    private var currentPosSelected = currentUserLogged
    private var hasUserUaViewBeShown = false


    fun setView(view: UASegmentView) {
        uaSegmentView = view
    }

    fun destroy() {
        uaSegmentView = null
        useCase.dispose()
    }

    fun getSegments() {
        useCase.getSegmentsByRol(SegmentsIndicatorSubscriber())
    }

    fun getSegmentList() = uaSegmentList

    fun isItemSelected(pos: Int) = selectableItems[pos]

    fun getSegmentsListSize() = uaSegmentList.size

    private fun doOnUaPrevSelected() {
        if (!hasUserUaViewBeShown && currentUASelected.isEmpty()) {
            changeSegmentToSelected(0)
        } else {
            uaSegmentList.forEachIndexed { i, uaSegmentModel ->
                if (uaSegmentModel.segmentID.equals(currentUASelected, ignoreCase = true)) {
                    changeSegmentToSelected(i)
                    return
                }
            }
        }

    }

    fun setUserUaSelected(bool: Boolean) {
        hasUserUaViewBeShown = bool
    }

    fun hasUserUaViewBeShown() = hasUserUaViewBeShown

    fun setCurrentUASelected(segmentId: String) {
        currentUASelected = segmentId
    }

    fun setCurrentUaPosSelected(pos: Int) {
        currentPosSelected = pos
    }

    fun changeSegmentToSelected(pos: Int) {
        selectableItems[currentPosSelected] = false
        selectableItems[pos] = true
        setCurrentUaPosSelected(pos)
        if (pos > currentUserLogged) {
            setCurrentUASelected(uaSegmentList[pos].segmentID)
        } else {
            setCurrentUASelected(EMPTY_STRING)
        }
    }

    fun getSegmentId(pos: Int) = uaSegmentList[pos].segmentID

    fun getSegment(pos: Int) = uaSegmentList[pos]

    fun getCurrentPosSelected() = currentPosSelected

    fun getCurrentUASelected() = currentUASelected

    fun showUserSegment() {
        with(rol) {
            when {
                isDv() -> uaSegmentView?.showUserSegment(R.string.my_country)
                isGr() -> uaSegmentView?.showUserSegment(R.string.my_region)
                isGz() -> uaSegmentView?.showUserSegment(R.string.my_zone)
                else -> uaSegmentView?.showUserSegment(NUMERO_CERO)
            }
        }
    }

    private fun prepareSegmentsToShow(list: List<UASegment>) {
        uaSegmentList.addAll(parseSegmentList(list))
        selectableItems[currentUserLogged] = true

        for ((index, _) in uaSegmentList.withIndex()) {
            selectableItems[index] = false
        }

        uaSegmentView?.setUpRecyclerView()

        doOnUaPrevSelected()

        if (list.isNotEmpty() && currentPosSelected > currentUserLogged) {
            uaSegmentView?.showUaItemAsSelected(currentPosSelected)
        }
    }

    private fun parseSegmentList(list: List<UASegment>) = list.map { parseUASegmentToModel(it) }

    private fun parseUASegmentToModel(item: UASegment): UASegmentModel {
        return UASegmentModel(
            segmentID = item.segmentID,
            level = item.level,
            fullname = item.fullName,
            itsUA = getUASegmentLabelName(),
            defValue = getDefaultValue()
        )
    }

    private fun getUASegmentLabelName() = with(rol) {
        when {
            isDv() -> R.string.region_manager
            isGr() -> R.string.zone_manager
            isGz() -> R.string.enterprise_partner
            else -> UNO_NEGATIVO
        }
    }


    private fun getDefaultValue() = with(rol) {
        when {
            isDv() -> R.string.no_cover
            isGr() -> R.string.no_responsible
            isGz() -> R.string.no_cover
            else -> UNO_NEGATIVO
        }
    }

    private inner class SegmentsIndicatorSubscriber : BaseSingleObserver<List<UASegment>>() {
        override fun onSuccess(t: List<UASegment>) {
            super.onSuccess(t)
            prepareSegmentsToShow(t)
        }
    }
}
