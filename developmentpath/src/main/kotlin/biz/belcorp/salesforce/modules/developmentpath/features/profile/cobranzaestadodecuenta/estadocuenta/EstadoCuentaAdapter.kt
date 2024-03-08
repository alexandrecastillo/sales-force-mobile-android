package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.salesforce.core.utils.numberFormatUS
import biz.belcorp.salesforce.core.utils.redondearDosDecimales
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_datos_estado_de_cuenta.view.*

class EstadoCuentaAdapter : RecyclerView.Adapter<EstadoCuentaViewHolder>() {

    var cuentasCorriente: MutableList<CuentaCorrienteModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadoCuentaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_datos_estado_de_cuenta, parent, false)
        return EstadoCuentaViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstadoCuentaViewHolder, position: Int) {
        val item = cuentasCorriente[position]
        holder.bind(item)
    }

    override fun getItemCount() = cuentasCorriente.size

    fun actualizar(data: List<CuentaCorrienteModel>) {
        this.cuentasCorriente = data.toMutableList()
        notifyDataSetChanged()
    }
}

class EstadoCuentaViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(model: CuentaCorrienteModel) = with(itemView) {
        tvFechaEstadoCuenta?.text = model.fechaOperacion
        tvDescripcionEstadoCuenta?.text = model.descripcionOperacion
        tvTituloEstadoCuenta?.text = model.tituloEstadoCuenta

        val montoOperacion = model.montoOperacion.toDoubleOrNull()
            ?.redondearDosDecimales()
            ?.numberFormatUS()

        tvMontoEstadoCuenta?.text = montoOperacion
        tvMontoEstadoCuenta?.textColor = getColor(obtenerColorRes(model.colorTipoCargo))
    }

    private fun obtenerColorRes(color: CuentaCorrienteModel.ColorTipoCargo): Int {
        return when (color) {
            CuentaCorrienteModel.ColorTipoCargo.ROJO -> R.color.account_state_request
            else -> R.color.gray_label_dark
        }
    }
}
