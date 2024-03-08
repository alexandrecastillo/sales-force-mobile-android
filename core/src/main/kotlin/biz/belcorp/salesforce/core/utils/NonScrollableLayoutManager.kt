package biz.belcorp.salesforce.core.utils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

class NonScrollableLayoutManager {

    private var context: Context? = null

    fun withContext(context: Context?): NonScrollableLayoutManager {
        this.context = context
        return this
    }

    fun linearVertical(): LinearLayoutManager {
        return object : LinearLayoutManager(context) {
            override fun canScrollHorizontally() = false
            override fun canScrollVertically() = false
        }
    }

    fun linearHorizontal(): LinearLayoutManager {
        return object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun canScrollHorizontally() = false
            override fun canScrollVertically() = false
        }
    }

    fun grid(columnas: Int): GridLayoutManager {
        return object : GridLayoutManager(context, columnas) {
            override fun canScrollHorizontally() = false
            override fun canScrollVertically() = false
        }
    }
}
