package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.core.extensions.setSafeOnClickListener
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.model.MetaPersonalModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.presenter.MetaPersonalPresenter
import biz.belcorp.salesforce.modules.developmentpath.utils.KeyboardUtil
import kotlinx.android.synthetic.main.fragment_meta_personal.*
import java.util.*

class MetaPersonalFragment : BaseFragment(),
    MetaPersonalView, MetaPersonalAdapter.OnItemClickListener {

    private var personaId: Long = -1L
    private var rol = Rol.NINGUNO

    private var metasAdapter: MetaPersonalAdapter? = null


    private val presenter by injectFragment<MetaPersonalPresenter>()

    private var metaEnEdicion: MetaPersonalModel? = null
    private var indiceEnEdicion = -1

    override fun getLayout() = R.layout.fragment_meta_personal

    companion object {

        const val ARG_ID = "Id"
        const val ARG_ROL = "Rol"

        fun newInstance(personaId: Long, personaRol: Rol): MetaPersonalFragment {
            return MetaPersonalFragment()
                .withArguments(
                    ARG_ID to personaId,
                    ARG_ROL to personaRol)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    private fun recuperarArgumentos() {
        arguments?.let {
            personaId = it.getLong(ARG_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    private fun inicializar() {
        inicializarSubTitulo()
        configurarRecycler()
        inicializarPresenter()
        escucharEventos()
    }

    private fun inicializarSubTitulo() {
        textSubtituloMeta?.text = getString(R.string.registro_metas, rol.comoTexto())
    }

    private fun inicializarPresenter() {
        presenter.recuperarMetas(personaId)
    }

    private fun configurarRecycler() {
        metasAdapter = MetaPersonalAdapter()
        metasAdapter?.establecerListener(this)

        recyclerMetasGrabadas?.layoutManager = NonScrollableLayoutManager()
            .withContext(activity)
            .linearVertical()
        recyclerMetasGrabadas?.adapter = metasAdapter
    }

    private fun escucharEventos() {
        buttonCrearMeta.setOnClickListener { mostrarCampoDeEdicion() }
        layoutCabeceraMeta.setOnClickListener { expandirOContraer() }
        buttonCancelarNuevaMeta.setOnClickListener { cancelarEdicion() }
        buttonGuardarNuevaMeta.setSafeOnClickListener { intentarGuardar() }
    }

    private fun expandirOContraer() = laytMetaList.cambiarVisibilidad()

    private fun cancelarEdicion() {
        devolverMetaEnEdicionALista()
        ocultarCampoDeEdicion()
        limpiarDescripcion()
        ocultarTeclado()
    }

    private fun devolverMetaEnEdicionALista() {
        if (metaEnEdicion != null && indiceEnEdicion != -1) {
            metasAdapter?.agregar(metaEnEdicion!!, indiceEnEdicion)
            reiniciarMetaEnEdicion()
            limpiarDescripcion()
        }
    }

    private fun intentarGuardar() {
        ocultarTeclado()
        if (validarDescripcion())
            guardarMeta()
        else
            mostrarMensaje(getString(R.string.campo_descripcion_necesario))
    }

    private fun validarDescripcion() = editTextMeta.text.toString().trim().isNotEmpty()

    private fun guardarMeta() {
        val meta = recuperarMeta()
        presenter.guardarMeta(meta, rol)
        reiniciarMetaEnEdicion()
    }

    private fun reiniciarMetaEnEdicion() {
        metaEnEdicion = null
        indiceEnEdicion = -1
    }

    private fun recuperarMeta(): MetaPersonalModel {
        return MetaPersonalModel(
            metaId = metaEnEdicion?.metaId ?: -1L,
            personaId = personaId,
            descripcion = editTextMeta.text.toString(),
            fecha = Calendar.getInstance())
    }

    private fun limpiarDescripcion() = editTextMeta.text.clear()

    override fun alEliminar(model: MetaPersonalModel) {
        devolverMetaEnEdicionALista()
        presenter.eliminarMeta(model)
    }

    override fun alEditar(model: MetaPersonalModel, indice: Int) {
        devolverMetaEnEdicionALista()
        setearEnEdicion(model, indice)
        quitarDeLista(model)
    }

    private fun setearEnEdicion(modelo: MetaPersonalModel, indice: Int) {
        metaEnEdicion = modelo
        indiceEnEdicion = indice
        setearDescripcion(modelo.descripcion)
    }

    private fun setearDescripcion(descripcion: String) {
        mostrarCampoDeEdicion()
        editTextMeta.setText(descripcion)
        editTextMeta.setSelection(editTextMeta.text.length)
    }

    private fun quitarDeLista(modelo: MetaPersonalModel) = metasAdapter?.eliminar(modelo)

    override fun agregarMeta(modelo: MetaPersonalModel) {
        metasAdapter?.agregar(modelo)
        limpiarDescripcion()
    }

    override fun eliminarMeta(modelo: MetaPersonalModel) = metasAdapter?.eliminar(modelo)

    override fun habilitarNueva() {
        buttonCrearMeta?.isEnabled = true
        pintarComoHabilitado()
    }

    override fun deshabilitarNueva() {
        buttonCrearMeta?.isEnabled = false
        pintarComoDeshabilitado()
    }

    override fun pintarContador(cantidad: Int, limite: Int) {
        textContadorMetas?.text = getString(R.string.divisor_dos_parametros, cantidad, limite)
    }

    override fun ocultarCampoDeEdicion() {
        layoutNuevaMeta?.visibility = View.GONE
    }

    override fun mostrarCampoDeEdicion() {
        layoutNuevaMeta?.visibility = View.VISIBLE
    }

    private fun pintarComoDeshabilitado() {
        context?.apply {
            buttonCrearMeta.textColor = ContextCompat.getColor(this, R.color.meta_fecha_agregado)
        }
        buttonCrearMeta.setCompoundDrawablesWithIntrinsicBounds(
            0, 0, R.drawable.ic_mas_deshabilitado, 0)
    }

    private fun pintarComoHabilitado() {
        context?.apply {
            buttonCrearMeta?.textColor = ContextCompat.getColor(this, R.color.titles)
        }
        buttonCrearMeta?.setCompoundDrawablesWithIntrinsicBounds(
            0, 0, R.drawable.ic_mas_habilitado, 0)
    }

    override fun mostrarMetas(list: List<MetaPersonalModel>) = metasAdapter?.actualizar(list)

    private fun expandir() {
        laytMetaList?.visibility = View.VISIBLE
        imageExpandirColapsar?.imageResource = R.drawable.ic_expand_less_blue
    }

    private fun colapsar() {
        laytMetaList?.visibility = View.GONE
        imageExpandirColapsar?.imageResource = R.drawable.ic_expand_more_blue
    }

    private fun View.cambiarVisibilidad() {
        when (this.visibility) {
            View.VISIBLE -> colapsar()
            View.GONE -> expandir()
        }
    }

    override fun mostrarMensaje(mensaje: String) {
        toast(mensaje)
    }

    private fun ocultarTeclado() = KeyboardUtil.dismissKeyboard(activity, view)
}
