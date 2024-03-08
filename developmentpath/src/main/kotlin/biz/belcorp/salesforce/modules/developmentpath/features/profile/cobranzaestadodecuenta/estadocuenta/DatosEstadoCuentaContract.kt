package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta

interface DatosEstadoCuentaContract {
    interface View {
        fun mostrarData(data: List<CuentaCorrienteModel>)
    }

    interface Presenter {
        fun obtener(personaId: Long)
    }
}
