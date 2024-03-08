package biz.belcorp.salesforce.modules.consultants.features.list.adapters.holders

import android.view.View
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.Constants
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.Constants.EMPTY_STRING
import biz.belcorp.salesforce.modules.consultants.features.list.adapters.OnConsultantItemSelected
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultoraModel
import biz.belcorp.salesforce.modules.consultants.features.list.utils.agregarDigitoVerificador
import kotlinx.android.synthetic.main.item_view_consultant_list.view.*
import biz.belcorp.salesforce.base.R as BaseR

class BeautyConsultantViewHolder(var view: View) : BaseViewHolder(view), View.OnClickListener {

    lateinit var model: ConsultoraModel

    private var filtroBusqueda: Boolean = false
    private var currencySymbol = Constant.EMPTY_STRING
    private var highLightName: String? = null
    private var country: String? = null
    private var listener: OnConsultantItemSelected? = null

    fun bind(
        filtroBusqueda: Boolean,
        country: String,
        currencySymbol: String,
        highLightName: String,
        model: ConsultoraModel,
        listener: OnConsultantItemSelected?
    ) {

        this.model = model
        this.filtroBusqueda = filtroBusqueda
        this.country = country
        this.currencySymbol = currencySymbol
        this.highLightName = highLightName
        this.listener = listener

        handleLocationIcons()
        handlePhoneIcon()
        setViewValues()
        setListeners()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cvwItemContainer -> {
                val personIdentifier = PersonIdentifier(
                    model.consultorasId.toLong(),
                    model.codigo ?: return,
                    Rol.CONSULTORA
                )
                listener?.onConsultantItemSelected(personIdentifier)
            }
            R.id.ivConsultantFav, R.id.ivConsultantFavRed ->
                listener?.onLocationIconSelected(model)
            R.id.ivConsultantCall -> {
                if (isPhoneNumberValid()) {
                    listener?.onCallIconSelected(model.seccion, model.telefonoCelular)
                }
            }
        }
    }

    private fun setListeners() {
        itemView.cvwItemContainer?.setOnClickListener(this)
        itemView.ivConsultantFav?.setOnClickListener(this)
        itemView.ivConsultantFavRed?.setOnClickListener(this)
        itemView.ivConsultantCall?.setOnClickListener(this)
    }

    private fun setViewValues() {
        setFullName(getFullName())
        showLevel(model)
        itemView.tvConsultantCode?.text = String
            .format(
                itemView.context.getString(R.string.code),
                model.codigo.agregarDigitoVerificador(model.digitoVerificador)
            )

        if (isBalanceis0orNull(model)) {
            itemView.tvConsultantBalance.invisible()
        } else {
            itemView.tvConsultantBalance.visible()
            itemView.tvConsultantBalance.text = getSaldoPendiente()
        }

        if (filtroBusqueda) {
            itemView.tvConsultantSeccion?.visible()
            itemView.tvConsultantSeccion?.text = String
                .format(
                    itemView.context.getString(R.string.section),
                    model.seccion
                )
        } else {
            itemView.tvConsultantSeccion?.gone()
        }
    }

    private fun setFullName(fullName: String) {
        if (highLightName == null) {
            itemView.tvConsultantBalance?.setFont(BaseR.font.mulish_regular)
            itemView.tvConsultantName?.text = fullName
        } else {
            itemView.tvConsultantBalance?.setFont(BaseR.font.mulish_light)
            itemView.tvConsultantName?.text = fullName.highlight(highLightName!!.toString())
        }
    }

    private fun getSaldoPendiente(): String {
        val balance = itemView.context.getString(R.string.balance)
        return String.format(balance, currencySymbol, model.saldoPendiente)
    }

    private fun getFullName(): String {
        return if (model.primerApellido.isNullOrEmpty()) {
            model.nombre ?: EMPTY_STRING
        } else {
            if (Pais.ECUADOR.codigoIso.equals(country, true)) {
                doForEcuadorCountry()
            } else {
                doForElseCountry()
            }
        }
    }

    private fun doForEcuadorCountry() = model
        .let {
            "${it.primerApellido} ${it.segundoApellido} ${it.primerNombre} ${it.segundoNombre}"
        }.toUpperCase()

    private fun doForElseCountry() = model
        .let {
            "${it.primerNombre} ${it.segundoNombre} ${it.primerApellido} ${it.segundoApellido}"
        }.toUpperCase()

    private fun isRedLocationMarkerVisible() = (model.latitud != 0.0 && model.longitud != 0.0)

    private fun handleLocationIcons() {
        if (isRedLocationMarkerVisible()) showRedLocationIcon() else hideRedLocationIcon()
    }

    private fun showRedLocationIcon() {
        itemView.ivConsultantFav?.visibility = View.VISIBLE
        itemView.ivConsultantFavRed?.visibility = View.VISIBLE
    }

    private fun hideRedLocationIcon() {
        itemView.ivConsultantFavRed?.visibility = View.GONE
        itemView.ivConsultantFav?.visibility = View.VISIBLE
    }

    private fun handlePhoneIcon() {
        if (model.telefonoCelular.isPhoneNumberValid()) showPhoneIcon() else hidePhoneIcon()
    }

    private fun isPhoneNumberValid() = model.telefonoCelular.isPhoneNumberValid()

    private fun hidePhoneIcon() {
        itemView.callSeparator?.visibility = View.GONE
        itemView.ivConsultantCall?.visibility = View.GONE
    }

    private fun showPhoneIcon() {
        itemView.callSeparator?.visibility = View.VISIBLE
        itemView.ivConsultantCall?.visibility = View.VISIBLE
    }

    private fun showLevel(consultora: ConsultoraModel) {
        consultora.nivelActual?.let {
            if (!it.trim().isEmpty()) {
                itemView.tvConsultantLevel.visibility = View.VISIBLE
                itemView.tvConsultantLevel.text =
                    "${itemView.context.getString(R.string.consultant_level_hint)}: $it"
            } else {
                itemView.tvConsultantLevel.visibility = View.GONE
            }
        } ?: run {
            itemView.tvConsultantLevel.visibility = View.GONE
        }
    }

    private fun isBalanceis0orNull(consultora: ConsultoraModel): Boolean {
        return (consultora.saldoPendiente == Constants.ZERO_DOUBLE || consultora.saldoPendiente == null
            || consultora.saldoPendiente == Constants.ZERO || consultora.saldoPendiente == Constants.EMPTY_DOUBLE.toString()
            || consultora.saldoPendiente == Constants.EMPTY_STRING || consultora.saldoPendiente?.contains(
            "-"
        ) == true)
    }

}
