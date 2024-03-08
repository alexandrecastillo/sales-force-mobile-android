package biz.belcorp.salesforce

import org.gradle.api.JavaVersion

object Versions {
    object Android {
        val javaVersion = JavaVersion.VERSION_1_8
        const val compileSdkVersion = 33
        const val minSdkVersion = 23
        const val targetSdkVersion = 33
        const val appVersionCode = 404
        const val appVersionName = "4.2.4"
        const val buildToolsVersion = "30.0.3"
    }

    object Libs {
        const val kotlinVersion = "1.4.31"
        const val coroutinesVersion = "1.3.2"

        const val fabricVersion = "2.2.0"
        const val sonarVersion = "2.6.2"
        const val jacocoVersion = "0.16.0"
        const val newRelicVersion = "6.5.0"
        const val firebasePluginVersion = "1.1.5"
        const val firebasePerfPluginVersion = "1.3.1"
        const val bundleToolVesion = "0.13.0"

        const val androidGradlePluginVersion = "4.1.3"
        const val googleServicesVersion = "4.3.0"

        const val androidXVersion = "1.2.0"
        const val swiperefreshlayoutVersion = "1.1.0"
        const val materialVersion = "1.1.0-rc02"
        const val constraintLayoutVersion = "2.0.0-beta4"
        const val viewpager2Version = "1.0.0"
        const val navigationVersion = "2.2.0"
        const val viewmodelKTXVersion = "2.2.0"
        const val multidexVersion = "2.0.1"
        const val playCore = "1.6.5"

        const val firebaseCoreVersion = "17.2.0"
        const val firebasePerfVersion = "19.0.8"
        const val firebaseMessagingVersion = "20.2.4"
        const val firebaseAnalyticsVersion = "17.4.4"
        const val firebaseConfigVersion = "19.2.0"
        const val firebaseCrashlyticsVersion = "17.1.1"

        const val playServicesMapVersion = "16.1.0"
        const val playServicesLocationVersion = "16.0.0"

        const val koinVersion = "2.0.1"

        const val kotlinSerializationVersion = "1.3.1"

        const val androidChartVersion = "v3.0.3"

        const val stethoVersion = "1.5.1"
        const val glideVersion = "4.11.0"
        const val flexboxVersion = "1.0.0"

        const val retrofitVersion = "2.6.1"
        const val retrofitKotlinxVersion = "0.8.0"
        const val okHttpVersion = "3.10.0"
        const val okHttpInterceptorVersion = "3.9.1"
        const val crashlyticsVersion = "2.10.1"
        const val crashActivityVersion = "2.3.0"
        const val workManagerVersion = "2.7.1"
        const val jwtVersion = "0.9.0"
        const val gsonVersion = "2.8.5"
        const val rxJavaVersion = "2.1.5"
        const val rxAndroidVersion = "2.0.1"

        const val kinesisVersion = "2.11.0"

        const val dbflowVersion = "4.1.2"
        const val dbflowKotlinVersion = "4.1.2@aar"
        const val objectboxVersion = "3.0.1"

        const val belcorpComponentsVersion = "2.0.7-v10-FFVV-SNAPSHOT-fix-alexandre-1"

        const val spinnerDatePickerVersion = "1.0.6"
        const val firebaseVersion = "17.2.1"
        const val googleMapVersion = "17.0.0"
        const val googleLocationVersion = "17.0.0"

        const val design = "29.0.0"

        const val expandableVersion = "1.6.0@aar"
        const val circleImageVersion = "2.2.0"

        const val emojiVersion = "1.0.0"
        const val circularProgressBarVersion = "1.1.1"
        const val sectionedRecyclerViewAdapter = "1.1.3"
        const val rootBeer = "0.1.0"
        const val securityCrypto = "1.1.0-alpha01"
    }

    object Test {
        const val junitVersion = "4.12"
        const val jUnit5Version = "5.4.1"
        const val espressoCoreVersion = "3.2.0"
        const val runnerVersion = "1.0.2"
        const val androidTestCoreVersion = "1.2.0"
        const val androidTestExtVersion = "1.1.1"
        const val androidArchTestCoreVersion = "1.1.1"
        const val roboelectricVersion = "4.3"
        const val mockitoKotlinVersion = "2.1.0"
        const val koinTestVersion = "2.0.1"
        const val kluentVersion = "1.56"
        const val mockkVersion = "1.9.3"
        const val coroutines = "1.3.2"
    }
}

