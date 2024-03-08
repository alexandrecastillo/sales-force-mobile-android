package biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.view

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderRecargarReconocimientosASuperior
import biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.model.ReconocimientoModel
import biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.presenter.ReconocimientoASuperiorDetallePresenter
import biz.belcorp.salesforce.modules.developmentpath.utils.recuperarNombreCorto
import kotlinx.android.synthetic.main.fragment_reconocimiento_a_superior.*

class ReconocimientoASuperiorDetalleFragment :
    BaseDialogFragment(),
    ReconocimientoASuperiorDetalleView,
    CalificarVisitaSuccessFragment.OnFragmentDialogInteractionListener {

    override fun getLayout() = R.layout.fragment_reconocimiento_a_superior

    private var idReconocimiento: Long = 0

    private val presenter: ReconocimientoASuperiorDetallePresenter by injectFragment()

    private val senderRecarga: SenderRecargarReconocimientosASuperior by injectFragment()

    companion object {

        const val ID_RECONOCIMIENTO = "RECOGNITION_ID_KEY"

        fun newInstance(idReconocimiento: Long): ReconocimientoASuperiorDetalleFragment {
            val bundle = Bundle()
            val fragment = ReconocimientoASuperiorDetalleFragment()
            bundle.putLong(ID_RECONOCIMIENTO, idReconocimiento)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarDatos()
    }

    private fun recuperarDatos() {
        arguments?.let {
            idReconocimiento = it.getLong(ID_RECONOCIMIENTO, 0)
        }
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    private fun inicializar() {
        presenter.establecerVista(this)
        presenter.recuperar(idReconocimiento)
        establecerCalificacionMinima()
        escucharEventos()
    }

    private fun establecerCalificacionMinima() {
        ratingbar_recognized.setOnRatingBarChangeListener { ratingBar, rating, _ ->
            if (rating < 1.0f) {
                ratingBar.rating = 1.0f
            }
        }
    }

    private fun escucharEventos() {
        iv_close?.setOnClickListener { cerrar() }
        btn_reconocer?.setOnClickListener { reconocer() }
    }

    override fun pintarReconocida(model: ReconocimientoModel) {
        cargarDatos(model)
        inhabilitarControles()
    }

    override fun pintarNoReconocida(model: ReconocimientoModel) {
        cargarDatos(model)
        habilitarControles()
    }

    override fun alGrabarseReconocimiento(nombre: String) {
        if (!isAttached()) return
        mostrarMensajeExito(nombre)
        senderRecarga.notificarCambio()
    }

    private fun mostrarMensajeExito(nombreCalificada: String) {
        val fragment = CalificarVisitaSuccessFragment.newInstance(nombreCalificada)
        fragment.establecerListener(this)
        fragment.show(childFragmentManager, fragment.tag)
    }

    private fun cargarDatos(model: ReconocimientoModel) {
        tv_iniciales?.text = model.iniciales
        tv_recognized_name?.text = model.nombreReconocida.recuperarNombreCorto()
        et_message?.setText(model.comentarios)
        tv_recognized_rol?.text = getString(R.string.guion_dos_campos, model.codRol, model.ua)
        ratingbar_recognized?.rating = model.valoracion.toFloat()
    }

    private fun reconocer() {
        val estrellas = ratingbar_recognized.rating.toInt()
        val comentarios = et_message.text.toString()
        presenter.grabar(idReconocimiento, estrellas, comentarios)
    }

    private fun cerrar() {
        senderRecarga.notificarCambio()
        dismiss()
    }

    private fun habilitarControles() {
        btn_reconocer?.visibility = View.VISIBLE
        ratingbar_recognized?.setIsIndicator(false)
        ratingbar_recognized?.rating = 1.0f
        et_message?.isEnabled = true
    }

    private fun inhabilitarControles() {
        btn_reconocer?.visibility = View.GONE
        ratingbar_recognized?.setIsIndicator(true)
        et_message?.isEnabled = false
    }

    override fun onAccept() {
        dismiss()
    }
}
