package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderCambioReconocimiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.features.rdd.helpers.setIsertIconPerson
import kotlinx.android.synthetic.main.fragment_reconocimiento_comportamientos.*

class ReconocerComportamientosFragment : BaseDialogFragment(), ReconocerComportamientosView {

    private var personaId = -1L
    private lateinit var rol: Rol
    private val presenter by injectFragment<ReconocerComportamientosPresenter>()
    private val senderCambioReconocimiento by inject<SenderCambioReconocimiento>()
    private val dialogoExito by lazy { DialogRecognitionSuccess.newInstance(rol) }
    private val adapter by lazy { instanciarAdapter() }

    override fun getLayout() = R.layout.fragment_reconocimiento_comportamientos

    private fun instanciarAdapter() = ReconocimientosAdapter().apply {
        setClickListener { pos -> presenter.invertirSeleccion(pos) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    private fun recuperarArgumentos() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRecycler()
        configurarBotonCerrar()
        configurarBotonGuardado()
        cargarComportamientos()
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    private fun cargarComportamientos() {
        presenter.cargarDatosIniciales(personaId, rol)
    }

    private fun configurarRecycler() {
        recyclerComportamientos.layoutManager = GridLayoutManager(context, 2)
        recyclerComportamientos.adapter = adapter
    }

    private fun configurarBotonCerrar() {
        imgBack.setOnClickListener { dismiss() }
    }

    private fun configurarBotonGuardado() {
        btnRecognition.setOnClickListener { presenter.guardar() }
    }

    override fun mostrarDatosBasicosPersona(nombreCompleto: String) {
        imgIconConsultant?.setIsertIconPerson(requireContext(), nombreCompleto)
        txtNamePersonReco?.text = WordUtils.capitalizeFully(nombreCompleto)
    }

    override fun mostrarDatosSocia(nivelProductividad: String, exito: Boolean) {
        textPerfilSeNivelRec?.text = nivelProductividad
        textPerfilSeNivelRec?.visible()
        imagePerfilSeNivel?.visible()
        if (exito) {
            txtPersonTypeReco?.text = getString(R.string.socia_exitosa)
            txtPersonTypeReco?.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.estado_positivo
                )
            )
            imgPointTypeReco?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.circulo_exitosa
                )
            )
        } else {
            txtPersonTypeReco?.text = getString(R.string.socia_no_exitosa)
            txtPersonTypeReco?.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.estado_negativo
                )
            )
            imgPointTypeReco?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.circulo_no_exitosa
                )
            )
        }
    }

    override fun mostrarDatosConsultora(segmento: String, tipo: ConsultoraRdd.Tipo) {
        imgPointTypeReco?.setImageDrawable(puntoConsultora(requireContext(), tipo))
        txtPersonTypeReco?.text = segmento
    }

    private fun puntoConsultora(context: Context, tipo: ConsultoraRdd.Tipo): Drawable? {
        return when (tipo) {
            ConsultoraRdd.Tipo.NUEVA ->
                ContextCompat.getDrawable(context, R.drawable.circulo_consultora_nueva)
            ConsultoraRdd.Tipo.ESTABLECIDA, ConsultoraRdd.Tipo.C3 ->
                ContextCompat.getDrawable(context, R.drawable.circulo_consultora_establecida)
            else -> null
        }
    }

    override fun pintarReconocimientos(modelos: List<ReconocimientoModel>) {
        adapter.actualizar(modelos)
    }

    override fun pintarRazonEnBoton(numerador: Int, denominador: Int) {
        btnRecognition?.text = getString(R.string.rdd_reconocer_habilidad, numerador, denominador)
    }

    override fun ocultarDialogoPrincipal() {
        dialog?.hide()
    }

    override fun mostrarDialogoExito(alCerrarCallback: () -> Unit) {
        if (!isAdded) return
        dialogoExito.apply { onDismissCallback = alCerrarCallback }
            .safeShow(childFragmentManager, null)
    }

    override fun cerrarDialogoPrincipal() {
        safeDismiss()
    }

    override fun notificarCambioenReconocimiento() {
        senderCambioReconocimiento.notificarCambioEnReconocimiento()
    }

    companion object {

        val name: String = ReconocerComportamientosFragment::class.java.simpleName
        private const val ARG_PERSONA_ID = "param1"
        private const val ARG_ROL = "param2"

        fun newInstance(personaId: Long, rol: Rol) = ReconocerComportamientosFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
