package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.calendario

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.LinearLayout
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.CalendarioViewModel
import kotlinx.android.synthetic.main.control_calendar.view.*

class MiRutaCalendarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    var seleccionarDiaListener: SeleccionarDiaListener? = null

    private val adapter = CalendarAdapter(context)

    init {
        inicializarControl(context)
    }

    private fun obtenerOnItemClickListener() =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                seleccionarDiaListener?.alSeleccionar(position)
            }

    private fun inicializarControl(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        inflater.inflate(R.layout.control_calendar, this)
        calendar_grid?.agregarBotonExpandir(btn_expandir)

        calendar_grid?.onItemClickListener = obtenerOnItemClickListener()
        calendar_grid?.adapter = adapter
    }

    fun cargar(modelo: CalendarioViewModel) {
        adapter.actualizar(modelo.dias)
        calendar_grid?.indiceSeleccionado = modelo.seleccionado
        calendar_grid?.scrollearADiaActual()
    }

    interface SeleccionarDiaListener {
        fun alSeleccionar(indice: Int)
    }
}
