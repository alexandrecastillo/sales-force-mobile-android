package biz.belcorp.salesforce.modules.postulants.core.domain.enums

object PostulantQueue {
    private var queueList: HashMap<String, String> = HashMap()

    fun addServiceQueue(uneteService: UneteServices) {
        queueList[uneteService.id] = uneteService.value
    }

    fun isServiceAdded(uneteService: UneteServices): Boolean {
        return !queueList[uneteService.id].isNullOrEmpty()
    }

    fun removeServiceQueue(uneteService: UneteServices) {
        queueList.remove(uneteService.id)
    }

    fun removeAllQueue() {
        queueList.clear()
    }
}
