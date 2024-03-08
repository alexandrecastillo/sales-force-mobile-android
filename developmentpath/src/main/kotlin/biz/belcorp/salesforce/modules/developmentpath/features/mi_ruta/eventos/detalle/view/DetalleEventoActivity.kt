package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.view

import android.os.Bundle
import biz.belcorp.salesforce.core.base.BaseActivity
import biz.belcorp.salesforce.modules.developmentpath.R

class DetalleEventoActivity : BaseActivity(), DetalleEventoFragment.AlCerrarListener {

    override fun getLayout() = R.layout.activity_evento_detalle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val eventoUaId = intent!!.extras!!.getLong(ID_PARAM)

        DetalleEventoFragment
            .newInstance(eventoUaId)
            .conListener(this)
            .show(supportFragmentManager, null)
    }

    override fun alCerrarDialogoDetalleEvento() {
        finish()
    }

    companion object {
        const val ID_PARAM = "ID_DETALLE_EVENTO_ALARMA"
    }
}
