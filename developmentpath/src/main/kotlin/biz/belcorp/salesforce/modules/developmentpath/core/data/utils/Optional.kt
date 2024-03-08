package biz.belcorp.salesforce.modules.developmentpath.core.data.utils

sealed class Optional<out T> {

    class Some<out T>(val element: T) : Optional<T>()
    object None : Optional<Nothing>()

    fun element(): T? {
        return when (this) {
            is Optional.None -> null
            is Optional.Some -> element
        }
    }

    companion object {
        fun <T> create(element: T?): Optional<T> {
            return if (element != null) {
                Optional.Some(element)
            } else {
                Optional.None
            }
        }
    }

}
