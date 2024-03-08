@file:Suppress("DEPRECATION")

package biz.belcorp.salesforce.core.connectivity

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.utils.AppBuildConfig
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ConnectionLiveDataTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var activeNetwork: NetworkInfo
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private lateinit var connectionLiveData: ConnectivityLiveData

    @Before
    fun before() {

        connectivityManager = mockk(relaxed = true)
        activeNetwork = mockk(relaxed = true)

        mockkObject(AppBuildConfig)

        every { AppBuildConfig.getBuildVersion() } returns 28

        every { connectivityManager.activeNetworkInfo } returns activeNetwork

        every { connectivityManager.unregisterNetworkCallback(any<ConnectivityManager.NetworkCallback>()) } just Runs

        connectionLiveData =
            ConnectivityLiveData(connectivityManager)
    }

    @Test
    fun `currently connected observe receive true`() {

        every { activeNetwork.isConnectedOrConnecting } returns true

        captureNetworkCallback()

        val observer = mockk<Observer<ConnectivityState>>(relaxed = true)
        connectionLiveData.observeForever(observer)

        verify { observer.onChanged(ConnectivityState.Online) }
    }

    private fun captureNetworkCallback() {
        val slot = slot<ConnectivityManager.NetworkCallback>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            every {
                connectivityManager.registerDefaultNetworkCallback(capture(slot))
            } answers {
                networkCallback = slot.captured
            }
        } else {
            every {
                connectivityManager.registerNetworkCallback(any(), capture(slot))
            } answers {
                networkCallback = slot.captured
            }
        }
    }

}
