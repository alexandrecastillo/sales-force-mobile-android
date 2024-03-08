package biz.belcorp.salesforce.modules.brightpath.features.container.detail.filter

class ItemSelectableHandleUtil(currentPosSelected:Int, myCollection: List<Any>) {

    private var collection = mutableListOf<Any>()
    private var currentPosSelected = 0
    private val mapOfItemsSelectable = mutableMapOf<Int, Boolean>()


    init {
        this.collection.addAll(myCollection)
        this.currentPosSelected = currentPosSelected

        for ((index, _) in collection.withIndex()) {
            mapOfItemsSelectable[index] = false
        }

        this.mapOfItemsSelectable[currentPosSelected] = true
    }

    fun changeItemToSelected(newItemPositionSelected: Int) {
        mapOfItemsSelectable[currentPosSelected] = false
        mapOfItemsSelectable[newItemPositionSelected] = true
        currentPosSelected = newItemPositionSelected
    }

    fun isThisPosSelected(itemPosition: Int) = mapOfItemsSelectable[itemPosition]

    fun getItemSelectedPos() = currentPosSelected

    companion object {
        fun init(currentPosSelected: Int, collections: List<Any>): ItemSelectableHandleUtil {
            return ItemSelectableHandleUtil(currentPosSelected, collections)
        }
    }
}
