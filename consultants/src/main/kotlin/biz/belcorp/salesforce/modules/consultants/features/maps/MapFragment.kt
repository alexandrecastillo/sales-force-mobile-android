package biz.belcorp.salesforce.modules.consultants.features.maps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.components.utils.textToRoundDrawable
import biz.belcorp.salesforce.components.widgets.TextDrawable
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.constants.CONSULTANT_ID
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.IS_REGULAR_CONSULTANT_LIST
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.features.permissions.PermissionsUtil
import biz.belcorp.salesforce.core.gps.RequestGPS
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.features.maps.model.MapModel
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_maps.*
import kotlinx.android.synthetic.main.view_marker_custom.view.*
import kotlinx.android.synthetic.main.view_marker_custom_secundary.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.salesforce.components.R as ComponentR

class MapFragment : BaseDialogFragment(), OnMapReadyCallback {

    private val consultantId
        get() = requireNotNull(arguments?.getInt(CONSULTANT_ID, NUMBER_ZERO))

    private val isSearching: Boolean
        get() = arguments?.getBoolean(IS_REGULAR_CONSULTANT_LIST) ?: false

    private var gMap: GoogleMap? = null
    private var marker: Marker? = null
    private var googleApiClient: GoogleApiClient? = null

    private val viewModel by viewModel<MapViewModel>()
    private val gpsRequestGPS by inject<RequestGPS>()

    private val onActionListener = View.OnClickListener {
        (it as? MaterialButton)?.apply {
            when (text) {
                getString(R.string.text_confirm) -> doOnConfirmPosition()
                getString(R.string.text_edit_location) -> doOnEditPosition()
                getString(R.string.text_maps_cancel) -> doOnCancelEvent()
            }
        }
    }

    private val viewStateObserver = Observer<MapViewState> { state ->
        when (state) {
            is MapViewState.WithLocation -> doOnConsultantWithLocation(state.model)
            is MapViewState.WithoutLocation -> doOnConsultantWithoutLocation(state.model)
            is MapViewState.WithoutPermission -> requestFineLocationPermission()
            is MapViewState.Error -> doOnErrorState()
            is MapViewState.SuccessSave -> doOnSuccessSave(state.model)
        }
    }

    override fun getLayout() = R.layout.fragment_maps

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupMap()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        gMap = googleMap
        with(viewModel) {
            if (hasLocationPermissions()) viewModel.getConsultantInfo(consultantId)
            else requestFineLocationPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
            moveToPositionWithDelay()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermissionsUtil.FINE_LOCATION -> {
                if (grantResults.size == NUMBER_ONE
                    && grantResults[NUMBER_ZERO] == PackageManager.PERMISSION_GRANTED
                ) {
                    viewModel.moveToConsultantLocation(consultantId)
                } else {
                    clMapFragment?.let {
                        showPermissionNoGrantedSnackBar(it, getString(R.string.text_phone))
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun moveToPositionWithDelay() {
        Handler().postDelayed(
            { viewModel.moveToConsultantLocation(consultantId) },
            DEFAULT_DELAY_MILLS
        )
    }

    private fun showPermissionNoGrantedSnackBar(layout: View, hint: String) {
        val snackBar = Snackbar.make(
            layout,
            getString(R.string.text_permissions_not_granted),
            Snackbar.LENGTH_LONG
        )

        snackBar.setAction(
            R.string.text_give_permissions_manually
        ) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireContext().packageName, null)
            intent.data = uri
            startActivity(intent)
            Toast.makeText(
                requireContext(),
                java.lang.String.format(getString(R.string.text_permissions_hint), hint),
                Toast.LENGTH_LONG
            ).show()
        }
        showNotPermissionSnackBar(snackBar)
    }

    private fun showNotPermissionSnackBar(snackBar: Snackbar) {
        snackBar.view.setBackgroundColor(
            getColor(
                requireContext(),
                R.color.colorSnackBarPermission
            )
        )
        snackBar.show()
    }

    private fun init() {
        initViews()
        initEvents()
        setupViewModel()
    }

    private fun initViews() {
        setupToolbar()
    }

    private fun setupViewModel() {
        viewModel.location.observe(viewLifecycleOwner, viewStateObserver)
    }

    private fun setupToolbar() {
        actionBar(toolbar) {
            title = getString(R.string.text_save_location)
            setTitleTextAppearance(
                requireContext(),
                ComponentR.style.TextAppearance_Toolbar_Title_Centered
            )
            setNavigationIcon(BaseR.drawable.ic_backspace)
            navigationIcon?.tinted(getColor(context, BaseR.color.white))
            setNavigationOnClickListener { closeDialog() }
        }
    }

    private fun setupMap() {
        val mapFragment = SupportMapFragment.newInstance()
        childFragmentManager
            .beginTransaction()
            .replace(R.id.mapConsultant, mapFragment)
            .commit()
        mapFragment.getMapAsync(this)
    }

    private fun setupMarker(model: MapModel? = null) {
        requireContext().let {
            val view = LayoutInflater
                .from(it)
                .inflate(R.layout.view_marker_custom, null, false)

            view.ivMarkerCustomAvatar?.setImageDrawable(
                textToRoundDrawable(
                    getName(model),
                    AVATAR_DIMENSION,
                    AVATAR_DIMENSION,
                    getColor(it, ComponentR.color.colorRddPrimaryDark)
                )
            )
            ivMarker?.setImageBitmap(view.toBitmap(it))
        }
    }

    private fun doOnConsultantWithoutLocation(model: MapModel) {
        changeButtonsState(isEditable = true)
        moveToCurrentLocation()
        setupMarker(model)
    }

    private fun doOnConsultantWithLocation(model: MapModel) {
        changeButtonsState(isEditable = false)
        moveToMapPosition(model.position, getName(model), true)
        setupMarker(model)
    }

    private fun doOnSuccessSave(model: MapModel) {
        changeButtonsState(isEditable = false)
        ivMarker?.gone()
        moveToMapPosition(model.position, getName(model), true)
        notifyEvent(model.position)
        Toast.makeText(context, getString(R.string.text_consultant_updated), Toast.LENGTH_LONG)
            .show()
        dismiss()
    }

    private fun getName(model: MapModel?): String {
        return if (isSearching) model?.name.orEmpty() else model?.fullName.orEmpty()
    }

    private fun moveToMapPosition(
        latLng: LatLng,
        icon: String = Constant.EMPTY_STRING,
        isAvailableMarker: Boolean
    ) {
        if (gMap.isNull()) return
        if (viewModel.hasLocationPermissions()) {
            val cameraUpdateFactory = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZONE)
            gMap?.animateCamera(cameraUpdateFactory)
            val markerIcon = getMarkerLayout(icon)

            if (marker.isNotNull()) {
                marker?.remove()
                marker = null
            }

            if (isAvailableMarker) {
                marker = gMap?.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .icon(markerIcon)
                )
                ivMarker?.gone()
            }
            gMap?.isMyLocationEnabled = true
        }
    }

    private fun moveToCurrentLocation() {
        LocationServices.getFusedLocationProviderClient(requireContext()).apply {
            lastLocation.addOnSuccessListener {
                if (it.isNull()) verifyLocationConfig()
                else moveToMapPosition(
                    latLng = LatLng(it.latitude, it.longitude),
                    isAvailableMarker = false
                )
            }
        }
    }

    private fun verifyLocationConfig() {
        if (!isAttached()) return
        if (DeviceUtil.hasGPS(requireContext())) {
            (requireContext().getSystemService(Context.LOCATION_SERVICE) as? LocationManager)
                ?.let {
                    val isLocationProviderEnabled =
                        it.isProviderEnabled(LocationManager.GPS_PROVIDER)

                    if (isLocationProviderEnabled) getPrevLocation() else requestGPS()
                }
        }
    }

    private fun initEvents() {
        btnAction?.setOnClickListener(onActionListener)
        btnCancel?.setOnClickListener(onActionListener)
    }

    private fun doOnEditPosition() {
        changeButtonsState(isEditable = true)
        ivMarker?.visible()
        marker?.remove()
    }

    private fun doOnConfirmPosition() {
        val latLng = gMap?.cameraPosition?.target ?: return
        viewModel.saveLocation(consultantId, latLng)
    }

    private fun doOnCancelEvent() {
        if (viewModel.hasConsultantLocation()) {
            viewModel.moveToConsultantLocation(consultantId)
        } else {
            dismiss()
        }
    }

    private fun doOnErrorState() {
        Toast.makeText(
            context,
            getString(R.string.text_error_consultant_non_provided),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun changeButtonsState(isEditable: Boolean) {
        btnAction?.apply {
            text = getString(
                if (isEditable) R.string.text_confirm else R.string.text_edit_location
            )
            backgroundColor = getColor(
                requireContext(),
                if (isEditable) {
                    ComponentR.color.colorButtonVariant
                } else {
                    R.color.colorButtonEditPosition
                }
            )
            visible()
        }

        btnCancel?.apply { if (isEditable) visible() else gone() }
    }

    private fun getMarkerLayout(name: String): BitmapDescriptor {
        val layoutNoHoy: View = LayoutInflater.from(context)
            .inflate(R.layout.view_marker_custom_secundary, null, false)
        val imageNoHoy = layoutNoHoy.image_custom_marker_avatar_secundary
        val drawable: Drawable = TextDrawable
            .builder()
            .beginConfig()
            .width(AVATAR_DIMENSION)
            .height(AVATAR_DIMENSION)
            .fontSize(FONT_SIZE_DIMENSION)
            .endConfig()
            .buildRound(name, getColor(requireContext(), R.color.colorSnackBarPermission))
        imageNoHoy.setImageDrawable(drawable)
        return BitmapDescriptorFactory.fromBitmap(layoutNoHoy.toBitmap(requireContext()))
    }

    private fun requestGPS() {
        gpsRequestGPS.requestGPS(requireActivity())
    }

    private fun getPrevLocation() {
        googleApiClient?.let {
            val location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)

            if (location.isNotNull()) {
                val latLng = LatLng(location.latitude, location.longitude)
                moveToMapPosition(latLng, isAvailableMarker = true)
            } else {
                getLocationByGoogleAPI()
            }
        }
    }

    private fun getLocationByGoogleAPI() {
        googleApiClient = GoogleApiClient.Builder(requireContext())
            .addConnectionCallbacks(object : ConnectionCallbacks {
                override fun onConnected(bundle: Bundle?) {
                    getPrevLocation()
                }

                override fun onConnectionSuspended(i: Int) {
                    googleApiClient?.connect()
                }
            })
            .addApi(LocationServices.API).build()
        googleApiClient?.connect()
    }

    private fun requestFineLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionType: Int = PermissionsUtil.FINE_LOCATION
            val permission = Manifest.permission.ACCESS_FINE_LOCATION

            if (requireActivity().shouldShowRequestPermissionRationale(permission)) {
                getPermissionSnackBar(permissionType)
            } else {
                requestPermissions(arrayOf(permission), permissionType)
                showPermissionNoGrantedSnackBar(
                    clMapFragment,
                    getString(R.string.text_location)
                )
            }
        }
    }

    private fun getPermissionSnackBar(permissionType: Int) {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION

        val snackBar = Snackbar
            .make(
                clMapFragment,
                R.string.text_fine_location_warning,
                Snackbar.LENGTH_INDEFINITE
            )
        snackBar.setAction(
            getString(R.string.text_accept)
        ) {
            requestPermissions(arrayOf(permission), permissionType)
        }
        showNotPermissionSnackBar(snackBar = snackBar)
    }

    private fun notifyEvent(latLng: LatLng) {
        LiveDataBus.publish(
            EventSubject.CONSULTANTS_LOCATION,
            bundleOf(
                HASH_CONSULTANT_LAT_LNG to latLng,
                HASH_CONSULTANT_ID to consultantId
            )
        )
    }

    companion object {
        private const val DEFAULT_ZONE = 16.2f
        private const val DEFAULT_DELAY_MILLS: Long = 3000
        private const val AVATAR_DIMENSION = 40
        private const val FONT_SIZE_DIMENSION = 16

        const val HASH_CONSULTANT_LAT_LNG = "HASH_CONSULTANT_LAT_LNG"
        const val HASH_CONSULTANT_ID = "HASH_CONSULTANT_ID"
    }
}
