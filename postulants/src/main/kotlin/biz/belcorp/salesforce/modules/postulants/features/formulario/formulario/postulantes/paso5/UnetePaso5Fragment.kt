package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso5

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import biz.belcorp.salesforce.base.BuildConfig
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.PaisUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.TipoPago
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteConfig
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoTelefonico
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteDocumentoModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso5
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.UnetePaso5Factory
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.ImageZoomFragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.BaseUneteFragment
import biz.belcorp.salesforce.modules.postulants.utils.*
import biz.belcorp.salesforce.modules.postulants.utils.Constant.URL_ESIKA_APP_WEB
import biz.belcorp.salesforce.modules.postulants.utils.Constant.URL_LABEL_APP_WEB
import biz.belcorp.salesforce.modules.postulants.utils.setHtmlText
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.dialog_material.*
import kotlinx.android.synthetic.main.dialog_material.btnOk
import kotlinx.android.synthetic.main.dialog_material.tvContent
import kotlinx.android.synthetic.main.dialog_regularizar_documento.*
import kotlinx.android.synthetic.main.dialog_sms.*
import kotlinx.android.synthetic.main.dialog_unete_finalizar.*
import kotlinx.android.synthetic.main.fragment_unete_paso_5.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UnetePaso5Fragment : BaseUneteFragment(), Step, UnetePaso5View {

    private val presenter: UnetePaso5Presenter by injectFragment()

    private var content: IUnetePaso5? = null
    private var uri: Uri? = null

    private var esZonaAltoRiesgo: Boolean = false
    private var textoEmailSMS: Int = Constant.NUMERO_CERO
    private var pasoBloqueado: Int = Constant.SIN_ZONA

    companion object {
        const val REQUEST_GALLERY = 1000
        const val REQUEST_CAMERA = 1001
        const val REQUEST_PDF = 1002
        const val AUTHORITIES = "${BuildConfig.APPLICATION_ID}.FileProvider"
        const val PACKAGE = "package"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkTipoPostulante()

        btnContinuar?.setOnClickListener {
            if (content?.validate() == true) {
                validateAproveDisclaimer()
            }
        }
    }

    private fun updatePostulante() {
        content?.createModel()?.let {
            mModel = it
            if (mModel?.tipoPago == TipoPago.CONTADO.id && sobreEscribirImagenDocumentos(mModel?.pais)) createdDrawableImage()
            pasoBloqueado = formularioView?.getPasoBloqueado()!!
            presenter.updatePostulante(it)
        }
    }

    private fun sobreEscribirImagenDocumentos(pais: String?): Boolean {
        return when (pais) {
            Pais.SALVADOR.codigoIso,
            Pais.PANAMA.codigoIso,
            Pais.DOMINICANA.codigoIso,
            Pais.PUERTORICO.codigoIso -> false
            else -> true
        }
    }

    override fun paso(): Int {
        return formularioView?.pais()?.let { pais ->
            UneteConfig.find(pais)?.pasos ?: 0
        } ?: 0
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    reiniciarEInsertarDocumento()
                }
                REQUEST_GALLERY -> {
                    createImage(data?.data)
                    reiniciarEInsertarDocumento()
                }
                REQUEST_PDF -> {
                    data?.data?.let { d ->

                        activity?.let { act ->

                            ContentUriUtils.getFilePath(act, Uri.parse(d.toString()))?.let { p ->
                                if (p.toLowerCase().contains(".pdf")) {
                                    val file = File(p)

                                    if (file.exists()) {
                                        content?.setDocument(d.toString())
                                        content?.setDocument(
                                            obtenerDocumentoRuta(d.toString()),
                                            d.toString()
                                        )
                                    } else {
                                        toast(R.string.error_desconocido_pick_pdf_postulant)
                                    }
                                } else {
                                    toast(R.string.error_desconocido_pick_pdf_error)
                                }
                            } ?: run {
                                toast(R.string.error_desconocido_pick_pdf_postulant)
                            }
                        } ?: run {
                            toast(R.string.error_desconocido_pick_pdf_error)
                        }
                    } ?: run {
                        toast(R.string.error_desconocido_pick_pdf_error)
                    }
                }
            }
        }
    }

    private fun reiniciarEInsertarDocumento() {
        try {
            deleteImage()
            content?.setDocument(uri.toString())
            content?.setDocument(obtenerDocumentoRuta(uri.toString()), uri.toString())
        } catch (ex: Exception) {
            if (null != activity)
                ex.message?.let { showError(it) }
        }
    }

    private fun obtenerDocumentoRuta(uri: String): String {
        return Uri.parse(uri).toString()
    }

    private fun checkTipoPostulante() {
        val model = getModel()
        if (model.tipoPago == TipoPago.CONTADO.id
            && model.pais in PaisUnete.paisesPagoContado
            && model.pais !in PaisUnete.paisesPagoContadoConImagenes
        ) {
            content?.showDocuments(false)
        }
    }

    private fun createdDrawableImage() {

        createPagoContadoImage()
        deleteImage()

        mModel?.imagenIFE = uri.toString()
        mModel?.imagenCDD = uri.toString()
        mModel?.imagenContrato = uri.toString()
        mModel?.imagenPagare = uri.toString()
        mModel?.imagenDniAval = uri.toString()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            Constant.STORAGE_PERMISSION_GRANTED_CODE -> if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    activity, resources.getString(R.string.tx_permiso_garantizado_storage),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    activity, resources.getString(R.string.tx_permiso_denegado_storage),
                    Toast.LENGTH_SHORT
                ).show()

                showDialogPermissions()
            }
        }
    }

    private fun showDialogPermissions() {
        context?.also {
            val alertDialog = AlertDialog.Builder(it)
                .setTitle(R.string.dialog_title_permission)
                .setMessage(R.string.dialog_message_permission)
                .setNegativeButton(R.string.dialog_cancelar) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(R.string.dialog_aceptar) { dialog, _ ->
                    startActivity(
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .setData(Uri.fromParts(PACKAGE, it.packageName, null))
                    )
                    dialog.dismiss()
                }
                .create()

            if (!alertDialog.isShowing) {
                alertDialog.show()
            }
        }
    }

    private val dialogListener = DialogInterface.OnClickListener { _, which ->
        when (which) {
            Constant.NUMERO_CERO -> camera()
            Constant.NUMERO_UNO -> galeria()
            Constant.NUMERO_DOS -> pdf()
        }
    }

    private fun checkPermission(): Boolean {
        return permissionsStorage().all {
            checkPermission(it)
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context ?: return false, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        requestPermissions(
            permissionsStorage(),
            Constant.STORAGE_PERMISSION_GRANTED_CODE
        )
    }

    private fun permissionsStorage(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.CAMERA
            )
        } else {
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_unete_paso_5
    }

    override fun initView() {

        presenter.setView(this)

        val vw = UnetePaso5Factory.getView(activity as Context, formularioView?.pais(), this)
        content = vw as IUnetePaso5
        lyContainer?.removeAllViews()
        lyContainer?.addView(vw)

        if (!checkPermission()) {
            requestPermission()
        }
    }

    override fun showLoading() {
        formularioView?.showLoading()
    }

    override fun hideLoading() {
        formularioView?.hideLoading()
    }

    override fun showSolicitudRechazada(message: String) {
        context?.solicitudRechazadaDialog(message) {
            activity?.finish()
        }?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onSelected() = Unit

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onError(error: VerificationError) = Unit

    private fun successCreation() {
        val docValidation = formularioView?.disableSteps() ?: false
        if (docValidation)
            showDocRegulationCompleteDialog()
        else
            showRegistrationCompleteDialog()
    }

    private fun getBrandInformation(): Pair<String, String> {
        val brandFocus = ComunUtil.getBrandFocus(presenter.getPais())
        val url: String
        val brand: String
        if (brandFocus == BrandFocus.ESIKA) {
            url = URL_ESIKA_APP_WEB
            brand = getString(R.string.esika_conmigo)
        } else {
            url = URL_LABEL_APP_WEB
            brand = getString(R.string.label_conmigo)
        }
        return Pair(url, brand)
    }

    private fun showDocRegulationCompleteDialog() {
        val url = getBrandInformation().first
        context?.customDialog(R.layout.dialog_regularizar_documento) {
            btnShareAppFromDoc?.setOnClickListener {
                context.share(url)
            }
            setOnCancelListener {
                formularioView?.finalizar()
            }
            setCanceledOnTouchOutside(true)
        }?.show()
    }

    private fun showRegistrationCompleteDialog() {
        val brandInfo = getBrandInformation()
        val url = brandInfo.first
        val brand = brandInfo.second
        val labelApp = getString(R.string.unete_suggest, "<b>$brand</b>")
        val labelApp2 = getString(R.string.download_app, brand)
        context?.customDialog(R.layout.dialog_unete_finalizar) {
            lblAppName?.setHtmlText(labelApp)
            lblAppName2?.text = labelApp2
            if (textoEmailSMS == Constant.TEXT_SMS_EMAIL)
                lLayout_sms_email?.visible()
            else
                lLayout_sms_email?.gone()
            btnShareApp?.setOnClickListener {
                context.share(url)
            }
            setOnCancelListener {
                formularioView?.finalizar()
            }
            setCanceledOnTouchOutside(true)
        }?.show()
    }

    override fun openZoom(imagenRuta: String) {
        fragmentManager?.let {
            ImageZoomFragment().withArguments(
                ImageZoomFragment.IMAGEN_RUTA to imagenRuta
            ).show(it, ImageZoomFragment.TAG)
        }
    }

    override fun openDocumentDialog(): Boolean {
        context?.let {
            val alertDialogBuilder = AlertDialog.Builder(it, R.style.dialog)
                .setTitle(R.string.seleccionar)

            if (Constant.LOAD_PDF_COUNTRIES.contains(presenter.sesion.countryIso.toUpperCase())) {
                alertDialogBuilder.setItems(R.array.select_documento_photo_pdf, dialogListener)
            } else {
                alertDialogBuilder.setItems(R.array.select_documento, dialogListener)
            }

            val alertDialog = alertDialogBuilder.create()

            if (!alertDialog.isShowing) {
                alertDialog.show()
            }
        }
        return true
    }

    override fun setZonaRiesgo(esZonaRiesgo: Boolean) {
        esZonaAltoRiesgo = esZonaRiesgo
    }

    override fun setZonaCritica(esCritica: Boolean) {
        presenter.listarDocumentos(getModel(), esCritica)
    }

    override fun esZonaRiesgo(): Boolean {
        return esZonaAltoRiesgo
    }

    override fun setTextoEmailSMS(txtEmailSMS: Int) {
        textoEmailSMS = txtEmailSMS
    }

    override fun initModel() {
        presenter.validarZonaRiesgo(
            getModel().codigoZona?.trim() + Constant.EMPTY_STRING + getModel().codigoSeccion?.trim()
        )

        presenter.validarZonaCritica("${presenter.sesion.zona?.trim()}${(presenter.sesion.seccion ?: Constant.EMPTY_STRING).trim()}")
    }

    override fun loadDocumentos(documentos: List<UneteDocumentoModel>) {
        content?.loadDocumentos(documentos)
    }

    override fun galeria() {
        if (!checkPermission()) {
            requestPermission()
        }

        try {
            val intent = createImageChooserIntent(getString(R.string.image_chooser_title))
            if (intent != null) startActivityForResult(intent, REQUEST_GALLERY)
            else toast(R.string.image_chooser_error_message)
        } catch (ex: Exception) {
            showError(ex.message.orEmpty())
        }
    }

    override fun pdf() {
        if (!checkPermission()) {
            requestPermission()
        }

        startActivityForResult(
            Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ).apply {
                type = "*/*"
            }, REQUEST_PDF
        )
    }

    private fun camera() {
        if (!checkPermission()) {
            requestPermission()
        }

        try {
            createImage()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(intent, REQUEST_CAMERA)
        } catch (ex: Exception) {
            showError(ex.message.orEmpty())
        }
    }

    override fun getModel(): PostulanteModel {
        return formularioView?.postulante() ?: PostulanteModel()
    }

    override fun setModel(p: PostulanteModel) {
        formularioView?.postulante(p)
    }

    override fun updated(postulanteModel: PostulanteModel) {
        mModel = postulanteModel
        if (paso() == pasoBloqueado &&
            mModel?.estadoTelefonico != UneteEstadoTelefonico.VALIDADO.value
        ) {
            formularioView?.setPostulante(postulanteModel)
            mostrarMensajeBloqueoPaso()
        } else {
            formularioView?.postulante(postulanteModel)
            successCreation()
        }
    }

    @Throws(IOException::class)
    private fun createFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = Constant.TEXT_UNETE_ + timeStamp + Constant.LINE_SPACE
        val storageDir = File(activity?.filesDir, Constant.TEXT_IMAGES)
        if (!storageDir.exists()) storageDir.mkdirs()

        return File.createTempFile(imageFileName, Constant.FORMAT_PNG, storageDir)
    }

    @Throws(IOException::class)
    private fun createImage() {
        val image = createFile()
        context?.let {
            uri = FileProvider.getUriForFile(it, AUTHORITIES, image)
        }
    }

    private fun createImage(_uri: Uri?) {
        try {
            val thumbail = MediaStore.Images.Media.getBitmap(activity?.contentResolver, _uri)
            val resizedGaleria = Bitmap.createScaledBitmap(
                thumbail, Constant.DS_WIDTH_IMAGE, Constant.DS_HEIGHT_IMAGE, true
            )

            val image = createFile()
            val out = FileOutputStream(image)

            resizedGaleria.compress(Bitmap.CompressFormat.PNG, Constant.QUALITY_IMAGE, out)

            out.flush()
            out.close()

            uri = FileProvider.getUriForFile(context ?: return, AUTHORITIES, image)
        } catch (e: Exception) {
            Logger.e(javaClass.simpleName, e.message.orEmpty(), e)
        }
    }

    private fun createPagoContadoImage() {
        val thumbail = BitmapFactory.decodeResource(resources, R.drawable.pago_contado)
        val resizedGaleria = Bitmap.createScaledBitmap(
            thumbail, Constant.DS_WIDTH_IMAGE, Constant.DS_HEIGHT_IMAGE, true
        )

        val image = createFile()
        val out = FileOutputStream(image)

        resizedGaleria.compress(Bitmap.CompressFormat.PNG, Constant.QUALITY_IMAGE, out)

        out.flush()
        out.close()

        context?.let {
            uri = FileProvider.getUriForFile(it, AUTHORITIES, image)
        }
    }

    private fun deleteImage() {
        if (uri == null) return
        val image = File(uri?.path.orEmpty())
        if (image.exists())
            image.delete()
    }

    override fun initEstado() {

        when (getEstado()) {
            Estado.Edicion -> {
                content?.disable(true)
                btnContinuar?.isEnabled = true
            }
            Estado.Visualizacion -> {
                content?.disable()
                btnContinuar?.isEnabled = false
            }
        }
    }

    private fun mostrarMensajeBloqueoPaso() {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle?.gone()
            tvContent?.text = getString(R.string.alert_paso_bloqueado)
            ivIcon?.gone()

            btnOk?.text = getString(R.string.actualizacion_button_ok)
            btnCancel?.visible()
            btnOk?.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    private fun validateAproveDisclaimer() {
        if (presenter.sesion.codigoRol.equals(
                Rol.SOCIA_EMPRESARIA.codigoRol,
                true
            ) && Constant.MTO_DISCLAIMER_APROBE_COUNTRIES.contains(presenter.sesion.countryIso.toUpperCase())
        ) {
            context?.let { ctx ->
                ctx.customDialog(R.layout.dialog_sms) {

                    tvContent.text = getString(R.string.disclaimer_aprove_postulant)
                    tvSubContent.visibility = View.GONE

                    btnOk.setText(R.string.alert_continuar_button)
                    btnOk.setOnClickListener {
                        updatePostulante()
                        dismiss()
                    }

                    btnDiscard.visibility = View.VISIBLE
                    btnDiscard.setText(R.string.cancelar)
                    btnDiscard.setOnClickListener {
                        dismiss()
                    }

                }.show()

            } ?: run {
                updatePostulante()
            }
        } else {
            updatePostulante()
        }
    }
}
