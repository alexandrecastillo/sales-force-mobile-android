package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.sv

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.InputType
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.SV
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.SexoModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso1
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.sv.digitalsign.DigitalSignDialogFragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1.UnetePaso1View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.BaseInput
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMAIL
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.Constant.providersEmail
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.calcularEdad
import biz.belcorp.salesforce.modules.postulants.utils.customDialog
import biz.belcorp.salesforce.modules.postulants.utils.onEnterKeyListener
import kotlinx.android.synthetic.main.dialog_material.*
import kotlinx.android.synthetic.main.dialog_material.btnOk
import kotlinx.android.synthetic.main.dialog_material.tvContent
import kotlinx.android.synthetic.main.unete_sv_paso_1.view.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

@SuppressLint("ViewConstructor")
class UnetePaso1(mContext: Context, view: UnetePaso1View) :
    BaseUnetePaso<UnetePaso1View>(mContext, view), IUnetePaso1 {

    private val downloadHelper by lazy { DocumentHelper(mContext) }
    private var signBase64 = Constant.EMPTY_STRING

    private val digitalSignDialogFragment by lazy {
        DigitalSignDialogFragment()
    }

    private val permissionDeniedDialog by lazy {
        buildPermissionDeniedDialog()
    }

    override fun initView() {
        super.initView()
        btnTerms.text = HtmlCompat.fromHtml(
            resources.getString(R.string.sv_terms_html_underlined),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    override fun initEvents() {

        txtDocumento?.onEnterKeyListener {
            if (txtDocumento?.validate()!!) {
                val p = createModelDocument()
                view.setModel(p)
                view.validateBloqueos(
                    p.pais.orEmpty(),
                    p.numeroDocumento.orEmpty(),
                    p.tipoDocumento.orEmpty(),
                    view.getZona().orEmpty()
                )
                true
            } else {
                false
            }
        }

        txtCorreo?.setListFiltrer(providersEmail)
        txtCorreoRepeat?.setListFiltrer(providersEmail)

        btnTerms.setOnClickListener {
            if (!checkStoragePermission()) {
                permissionDeniedDialog.show()
                return@setOnClickListener
            }
            downloadHelper.openDocument(Constant.URL_TERMS_UNETE_SV)
        }

        chxSVTerms.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && txtMesssageTerms.visibility == View.VISIBLE) txtMesssageTerms.visibility =
                View.INVISIBLE
        }

        btnFirmar.setOnClickListener {
            if (!checkStoragePermission()) {
                permissionDeniedDialog.show()
                return@setOnClickListener
            }

            digitalSignDialogFragment.setListener(object :
                DigitalSignDialogFragment.Companion.Listener {
                override fun onSignDone(bitmap: Bitmap) {
                    try {
                        Log.i(LOG_TAG, "Bitmap con la firma recibida!!!")

                        val directoryPath = "${context.codeCacheDir}/unete_${System.currentTimeMillis()}.jpg"

                        Log.i(LOG_TAG, "Se guarda la firma en: $directoryPath")

                        bitmap.compress(
                            Bitmap.CompressFormat.JPEG,
                            100,
                            FileOutputStream(File(directoryPath))
                        )

                        signBase64 =
                            Base64.encodeToString(File(directoryPath).readBytes(), Base64.NO_WRAP)
                        Log.i(LOG_TAG, "Archivo encodeado en Base64:\n$signBase64")

                        btnFirmar.text = resources.getString(R.string.unete_paso1_update_sign)

                        if (txtMesssageSign.visibility == View.VISIBLE) txtMesssageSign.visibility =
                            View.INVISIBLE
                    } catch (ex: Exception) {
                        Log.i("jorgevc", "Error: ${ex.message}")
                    }
                }

                override fun onSignError() {
                    Log.i(LOG_TAG, "Error obteniendo la firma")
                    Toast.makeText(context, "Error obteniendo la firma", Toast.LENGTH_LONG).show()
                }
            })

            digitalSignDialogFragment.show(
                (context as AppCompatActivity).supportFragmentManager,
                null
            )
        }

        if (!checkStoragePermission()) {
            requestStoragePermission()
        }
    }

    override fun getLayout(): Int {
        return R.layout.unete_sv_paso_1
    }

    override fun createModelDocument(): PostulanteModel {

        val model = view.getModel()

        model.pais = Pais.SALVADOR.codigoIso
        model.paisID = Pais.SALVADOR.id

        model.tipoDocumento = SV.TIPO_DOCUMENTO
        model.numeroDocumento = txtDocumento?.getText()?.trim()
        model.UrlFirma = signBase64
        model.deviceId = DeviceUtil.getId(context)
        model.soMobile =
            "Android (Linux; version ${DeviceUtil.getVersionCode()}; sdk ${DeviceUtil.getVersionSDK()}; ${DeviceUtil.getModel()})"

        return model
    }

    override fun showGeneros(model: List<SexoModel>) {
        spnGenero?.load(model)
        val selected = view.getModel().sexo
        spnGenero?.selection<SexoModel> { it?.codigo == selected }
    }

    override fun createModel(): PostulanteModel {

        val model = createModelDocument()

        if (model.solicitudPostulanteID <= Constant.NUMERO_CERO) {
            model.codigoZona = view.getZona()
            model.codigoSeccion = view.getSeccion()
            model.pais = Pais.SALVADOR.codigoIso
            model.paisID = Pais.SALVADOR.id

            model.fuenteIngreso = UneteAlgorithms.buildFuenteIngreso(view.getCodigoRol())

            model.tipoContacto = UneteTipoContacto.MOVIL.value.toString()
            model.estadoGEO = UneteEstadoGEO.SIN_CONSULTAR.value.toString()
            model.estadoBurocrediticio = UneteEstadoCrediticio.SIN_CONSULTAR.value.toString()

            model.estadoTelefonico = UneteEstadoTelefonico.NO_REQUERIDO.value
            model.indicadorActivo = "true"

        }

        model.primerNombre = txtPrimerNombre?.getText()
        model.segundoNombre = txtSegundoNombre?.getText()
        model.apellidoPaterno = txtApellidoPaterno?.getText()
        model.apellidoMaterno = txtApellidoMaterno?.getText()
        val fechaNacimiento = txtFechaNacimiento?.getText()?.toDate(formatShort2)
        model.fechaNacimiento = fechaNacimiento?.string(formatLongT)
        model.edad = fechaNacimiento?.calcularEdad() ?: Constant.NUMERO_CERO

        model.correoElectronico = txtCorreo?.getText()
        model.sexo = spnGenero?.selected<SexoModel>()?.codigo
        model.estadoPostulante = UneteEstado.GESTION_SAC.estado.toString()
        model.paso = 1

        model.sincronizado = false
        model.UrlFirma = signBase64
        model.deviceId = DeviceUtil.getId(context)
        model.soMobile =
            "Android (Linux; version ${DeviceUtil.getVersionCode()}; sdk ${DeviceUtil.getVersionSDK()}; ${DeviceUtil.getModel()})"

        return model
    }

    override fun loadModel() {
        val model = view.getModel()

        txtDocumento?.setText(model.numeroDocumento)
        txtPrimerNombre?.setText(model.primerNombre)
        txtSegundoNombre?.setText(model.segundoNombre)
        txtApellidoPaterno?.setText(model.apellidoPaterno)
        txtApellidoMaterno?.setText(model.apellidoMaterno)
        txtCorreo?.setText(model.correoElectronico)
        txtCorreoRepeat?.setText(model.correoElectronico)
        val fechaNacimiento = model.fechaNacimiento
            ?.changeDateFormat(formatLongT, formatShort2)
        txtFechaNacimiento?.setText(fechaNacimiento)
    }

    /*override fun showModal(isValid: Boolean) {
        if (isValid) {
            txtDocumento?.setEnable(false)
            lyBody?.visible()
        } else {
            txtDocumento?.setEnable(true)
        }
    }*/

    /*override fun showBody(isBodyVisible: Boolean) {
        if (isBodyVisible) {
            lyBody?.visible()
        } else {
            lyBody?.gone()
        }
    }*/

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += txtDocumento?.validate()
        v += txtPrimerNombre?.validate()
        v += txtSegundoNombre?.validate()
        v += txtApellidoPaterno?.validate()
        v += txtApellidoMaterno?.validate()
        v += txtFechaNacimiento?.validate()
        v += txtCorreo?.validate()
        v += txtCorreoRepeat?.validateRepeatEmail(txtCorreo?.getText().toString())
        v += validateFirma()
        v += validateAcceptTyC()

        return v.all { it ?: false }
    }

    override fun validateDocument() = validate()

    private fun validateAcceptTyC(): Boolean {
        return if (chxSVTerms.isChecked) {
            txtMesssageTerms.visibility = View.INVISIBLE
            true
        } else {
            txtMesssageTerms.visibility = View.VISIBLE
            false
        }
    }

    private fun validateFirma(): Boolean {
        return if (signBase64.trim().isEmpty()) {
            txtMesssageSign.visibility = View.VISIBLE
            false
        } else {
            txtMesssageSign.visibility = View.INVISIBLE
            true
        }
    }

    override fun initRequired() {
        txtPrimerNombre?.setRequired(true)
        txtSegundoNombre?.setRequired(false)
        txtApellidoPaterno?.setRequired(true)
        txtApellidoMaterno?.setRequired(false)
        txtDocumento?.setRequired(true)
        txtFechaNacimiento?.setRequired(true)
        txtCorreo?.setIsRequired(true)
        txtCorreoRepeat?.setIsRequired(true)
    }

    override fun initRestriction() {
        txtPrimerNombre?.setR(Filters.filterLetters(SV.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtSegundoNombre?.setR(Filters.filterLetters(SV.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtApellidoMaterno?.setR(Filters.filterLetters(SV.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtApellidoPaterno?.setR(Filters.filterLetters(SV.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtDocumento?.setR(Filters.filterNumber(SV.DUI_MAX_DIGITOS))
        txtCorreo?.setR(Filters.filterMaxLength(SV.CORREO_MAX_LENGHT))
        txtCorreoRepeat?.setR(Filters.filterMaxLength(SV.CORREO_MAX_LENGHT))

    }

    override fun initValidation() {
        txtPrimerNombre?.addV(V(context, EMPTY))
        txtApellidoPaterno?.addV(V(context, EMPTY))
        txtApellidoMaterno?.addV(V(context, EMPTY))
        txtFechaNacimiento?.addV(V(context, EMPTY))

        txtDocumento?.v(
            V(context, EMPTY, R.string.unete_valid_obligatorio),
            V(
                context, MIN_LENGTH, SV.DUI_MIN_DIGITOS,
                R.string.unete_paso1_valid_documento_min_length
            )
        )
        txtDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtCorreo?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_correo_empty),
            V(context, EMAIL, R.string.unete_paso1_valid_correo_format)
        )
        txtCorreoRepeat?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_correo_empty),
            V(context, EMAIL, R.string.unete_paso1_valid_correo_format)
        )

        txtFechaNacimiento?.minDate = Calendar.getInstance().minusYears(SV.EDAD_MAX)
        txtFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(SV.EDAD_MIN)

    }

    override fun showDuplicateMailError(message: String) {
        txtCorreo?.setEstado(BaseInput.ERROR, message)
        txtCorreoRepeat?.setEstado(BaseInput.ERROR, message)
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {
        val lst = mutableListOf<View>(txtDocumento!!, spnGenero!!)

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    txtPrimerNombre,
                    txtSegundoNombre,
                    txtApellidoPaterno,
                    txtApellidoMaterno,
                    txtFechaNacimiento,
                    txtCorreo,
                    txtCorreoRepeat
                )
            )
        }

        return lst
    }

    private fun checkStoragePermission(): Boolean {
        return permissionsStorage().all {
            checkPermission(it)
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context ?: return false, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        (context as AppCompatActivity).requestPermissions(
            permissionsStorage(),
            Constant.STORAGE_PERMISSION_GRANTED_CODE
        )
    }

    private fun buildPermissionDeniedDialog(): Dialog {
        return context.customDialog(R.layout.dialog_material) {
            ivIcon.visibility = View.GONE

            tvTitle.setText(R.string.permisos_rechazados)
            tvContent.text = context.getString(R.string.permisos_almacenamiento_denegados_mensaje)
            btnOk.setText(R.string.ir_configuracion)
            btnOk.setOnClickListener {
                context.startActivity(Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", context.packageName, null)
                })
                dismiss()
            }
            btnCancel.visibility = View.VISIBLE
            btnCancel.setText(R.string.cancelar)
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun permissionsStorage(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        } else {
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    companion object {
        private const val LOG_TAG: String = "UnetePaso1"
    }
}
