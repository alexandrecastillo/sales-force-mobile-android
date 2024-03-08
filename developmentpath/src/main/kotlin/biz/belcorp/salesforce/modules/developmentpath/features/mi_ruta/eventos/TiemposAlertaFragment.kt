package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_tiempos_alerta.*

class TiemposAlertaFragment: BottomSheetDialogFragment() {

    var listener: OnItemClickListener? = null
    lateinit var adapter: TiemposAlertaAdapter

    fun configurar(context: Context): TiemposAlertaFragment {
        adapter = crearAdapter(context)

        return this
    }

    fun agregarListener(listener: OnItemClickListener): TiemposAlertaFragment {
        this.listener = listener

        return this
    }

    fun actualizar(alertas: List<AgregarEventoViewModel.Alerta>) {
        adapter.actualizar(alertas)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tiempos_alerta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainRecycler?.layoutManager = LinearLayoutManager(context)
        mainRecycler?.adapter = adapter
    }

    private fun crearAdapter(context: Context): TiemposAlertaAdapter {
        val adapter = TiemposAlertaAdapter(context)
        adapter.setOnItemClickListener { pos ->
            listener?.alClickearItemTiempoAlerta(pos)
            Handler().postDelayed({ dismiss() }, 200)
        }

        return adapter
    }

    interface OnItemClickListener {
        fun alClickearItemTiempoAlerta(pos: Int)
    }
}
