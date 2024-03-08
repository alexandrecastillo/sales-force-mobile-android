package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.calendario

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.DiaCalendarioViewModel
import kotlinx.android.synthetic.main.control_calendar_day.view.*
import biz.belcorp.salesforce.core.R as coreR

class CalendarAdapter(val context: Context) : BaseAdapter() {
    private var modelos: List<DiaCalendarioViewModel> = emptyList()

    override fun getItem(position: Int): Any = modelos[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = modelos.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val modelo = modelos[position]
        val view = convertView ?: inflarVista(context, parent)

        view.tv_day.text = modelo.dia
        aplicarEstiloSegunModelo(view, modelo)

        return view
    }

    private fun aplicarEstiloSegunModelo(view: View, model: DiaCalendarioViewModel) {
        establecerFondo(view, model)
        establecerSeleccion(view, model)
        establecerPunto(view, model)
        view.tv_day.setTextColor(obtenerColorTexto(model))
    }

    private fun establecerFondo(view: View, model: DiaCalendarioViewModel) {
        when (model.tipoFondo) {

            DiaCalendarioViewModel.TipoFondo.RECTANGULO -> {
                view.fondo_derecha.visibility = View.VISIBLE
                view.fondo_izquierda.visibility = View.VISIBLE
                view.fondo_derecha_redondeado.visibility = View.INVISIBLE
                view.fondo_izquierda_redondeado.visibility = View.INVISIBLE
                view.circulo_limite.visibility = View.INVISIBLE
            }

            DiaCalendarioViewModel.TipoFondo.RECTANGULO_REDONDEADO_IZQUIERDA -> {
                view.fondo_izquierda_redondeado.visibility = View.VISIBLE
                view.fondo_derecha.visibility = View.VISIBLE
                view.fondo_izquierda.visibility = View.INVISIBLE
                view.fondo_derecha_redondeado.visibility = View.INVISIBLE
                view.circulo_limite.visibility = View.INVISIBLE
            }

            DiaCalendarioViewModel.TipoFondo.RECTANGULO_REDONDEADO_DERECHA -> {
                view.fondo_derecha_redondeado.visibility = View.VISIBLE
                view.fondo_izquierda.visibility = View.VISIBLE
                view.fondo_izquierda_redondeado.visibility = View.INVISIBLE
                view.fondo_derecha.visibility = View.INVISIBLE
                view.circulo_limite.visibility = View.INVISIBLE
            }

            DiaCalendarioViewModel.TipoFondo.CIRCULO_RECTANGULO_DERECHA -> {
                view.circulo_limite.visibility = View.VISIBLE
                view.fondo_derecha.visibility = View.VISIBLE
                view.fondo_izquierda.visibility = View.INVISIBLE
                view.fondo_izquierda_redondeado.visibility = View.INVISIBLE
                view.fondo_derecha_redondeado.visibility = View.INVISIBLE
            }

            DiaCalendarioViewModel.TipoFondo.CIRCULO_RECTANGULO_IZQUIERDA -> {
                view.circulo_limite.visibility = View.VISIBLE
                view.fondo_izquierda.visibility = View.VISIBLE
                view.fondo_derecha.visibility = View.INVISIBLE
                view.fondo_izquierda_redondeado.visibility = View.INVISIBLE
                view.fondo_derecha_redondeado.visibility = View.INVISIBLE
            }

            DiaCalendarioViewModel.TipoFondo.NINGUNO -> {
                view.fondo_derecha_redondeado.visibility = View.INVISIBLE
                view.fondo_izquierda.visibility = View.INVISIBLE
                view.fondo_izquierda_redondeado.visibility = View.INVISIBLE
                view.fondo_derecha.visibility = View.INVISIBLE
                view.circulo_limite.visibility = View.INVISIBLE
            }
        }
    }

    private fun establecerSeleccion(view: View, model: DiaCalendarioViewModel) {
        when (model.tipoSeleccion) {
            DiaCalendarioViewModel.TipoSeleccion.CIRCULO -> {
                view.circulo_seleccion.visibility = View.VISIBLE
                view.circunferencia_hoy.visibility = View.GONE
            }

            DiaCalendarioViewModel.TipoSeleccion.CIRCUNFERENCIA -> {
                view.circunferencia_hoy.visibility = View.VISIBLE
                view.circulo_seleccion.visibility = View.GONE
            }

            DiaCalendarioViewModel.TipoSeleccion.NINGUNO -> {
                view.circunferencia_hoy.visibility = View.GONE
                view.circulo_seleccion.visibility = View.GONE
            }
        }
    }

    private fun establecerPunto(view: View, model: DiaCalendarioViewModel) {
        return when (model.tipoPunto) {
            DiaCalendarioViewModel.TipoPunto.COLOR -> {
                view.punto_color.visibility = View.VISIBLE
                view.punto_blanco.visibility = View.GONE
            }
            DiaCalendarioViewModel.TipoPunto.BLANCO -> {
                view.punto_blanco.visibility = View.VISIBLE
                view.punto_color.visibility = View.GONE
            }
            DiaCalendarioViewModel.TipoPunto.NINGUNO -> {
                view.punto_color.visibility = View.INVISIBLE
                view.punto_blanco.visibility = View.GONE
            }
        }
    }

    private fun obtenerColorTexto(model: DiaCalendarioViewModel): Int {
        return when (model.tipoTexto) {
            DiaCalendarioViewModel.TipoTexto.SELECCIONADO ->
                ContextCompat.getColor(context, coreR.color.white)
            DiaCalendarioViewModel.TipoTexto.HABILITADO ->
                ContextCompat.getColor(context, R.color.black)
            DiaCalendarioViewModel.TipoTexto.DESHABILITADO ->
                ContextCompat.getColor(context, R.color.black_20)
        }
    }

    private fun inflarVista(context: Context, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        return inflater.inflate(R.layout.control_calendar_day, parent, false)
    }

    fun actualizar(modelos: List<DiaCalendarioViewModel>) {
        this.modelos = modelos
        notifyDataSetChanged()
    }
}
