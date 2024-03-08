package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.BROADCAST_IR_A_PERFIL
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.BROADCAST_IR_A_PERFIL_PARAM

class SenderIrAPerfil(private val context: Context) {

    fun irAPerfil(personIdentifier: PersonIdentifier) {
        val intent = Intent(BROADCAST_IR_A_PERFIL)
        intent.putExtra(BROADCAST_IR_A_PERFIL_PARAM, personIdentifier)
        context.sendBroadcast(intent)
    }
}
