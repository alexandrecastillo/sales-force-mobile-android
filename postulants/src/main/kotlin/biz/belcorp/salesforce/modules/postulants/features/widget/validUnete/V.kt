package biz.belcorp.salesforce.modules.postulants.features.widget.validUnete

import android.content.Context
import biz.belcorp.salesforce.modules.postulants.R

class V(val context: Context, val tipo: Int, val value: Any? = null, val errorMsg: String) {

    constructor(context: Context, tipo: Int, errorMsg: String) : this(context, tipo, null, errorMsg)

    constructor(context: Context, tipo: Int, errorMsg: Int) : this(
        context, tipo, null, context.getString(errorMsg)
    )

    constructor(context: Context, tipo: Int) : this(
        context, tipo, null, context.getString(R.string.unete_valid_obligatorio)
    )

    constructor(context: Context, tipo: Int, value: Any? = null, errorMsg: Int) : this(
        context, tipo, value, context.getString(errorMsg)
    )
}
