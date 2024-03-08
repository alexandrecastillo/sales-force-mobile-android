package biz.belcorp.salesforce

object Deps {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Libs.kotlinVersion}"
    const val kotlinCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Libs.coroutinesVersion}"
    const val kotlinCoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Libs.coroutinesVersion}"

    object BuildPlugins {
        const val androidGradle =
            "com.android.tools.build:gradle:${Versions.Libs.androidGradlePluginVersion}"
        const val googleServices =
            "com.google.gms:google-services:${Versions.Libs.googleServicesVersion}"
        const val kotlinGradlePlugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Libs.kotlinVersion}"
        const val objectboxPlugin =
            "io.objectbox:objectbox-gradle-plugin:${Versions.Libs.objectboxVersion}"
        const val crashlyticsPlugin = "com.google.firebase:firebase-crashlytics-gradle:${Versions.Libs.fabricVersion}"
        const val sonarPlugin =
            "org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:${Versions.Libs.sonarVersion}"
        const val jacocoPlugin =
            "com.vanniktech:gradle-android-junit-jacoco-plugin:${Versions.Libs.jacocoVersion}"
        const val firebasePlugin =
            "com.google.firebase:firebase-plugins:${Versions.Libs.firebasePluginVersion}"
        const val firebasePerfPlugin =
            "com.google.firebase:perf-plugin:${Versions.Libs.firebasePerfPluginVersion}"
        const val kotlinSerializationPlugin =
            "org.jetbrains.kotlin:kotlin-serialization:${Versions.Libs.kotlinVersion}"
        const val bundleTool =
            "com.android.tools.build:bundletool:${Versions.Libs.bundleToolVesion}"
        const val safeArgsPlugin =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Libs.navigationVersion}"
        const val newRelicPlugin =
            "com.newrelic.agent.android:agent-gradle-plugin:${Versions.Libs.newRelicVersion}"
    }

    object Android {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.Libs.androidXVersion}"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.Libs.swiperefreshlayoutVersion}"
        const val androidKTX = "androidx.core:core-ktx:${Versions.Libs.androidXVersion}"
        const val material = "com.google.android.material:material:${Versions.Libs.materialVersion}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Libs.constraintLayoutVersion}"
        const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.Libs.viewpager2Version}"

        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.Libs.navigationVersion}"
        const val navigationUI =
            "androidx.navigation:navigation-ui-ktx:${Versions.Libs.navigationVersion}"

        const val multidex = "androidx.multidex:multidex:${Versions.Libs.multidexVersion}"

        const val emojiCompat = "androidx.emoji:emoji:${Versions.Libs.emojiVersion}"
        const val emojiBundled = "androidx.emoji:emoji-bundled:${Versions.Libs.emojiVersion}"

        const val viewModelKTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Libs.viewmodelKTXVersion}"
    }

    object Libs {

        const val firebaseCore =
            "com.google.firebase:firebase-core:${Versions.Libs.firebaseCoreVersion}"
        const val firebasePerf =
            "com.google.firebase:firebase-perf:${Versions.Libs.firebasePerfVersion}"
        const val firebaseMessaging =
            "com.google.firebase:firebase-messaging:${Versions.Libs.firebaseMessagingVersion}"
        const val firebaseAnalytics =
            "com.google.firebase:firebase-analytics:${Versions.Libs.firebaseAnalyticsVersion}"
        const val firebaseConfig =
            "com.google.firebase:firebase-config:${Versions.Libs.firebaseConfigVersion}"
        const val firebaseCrashlytics =
            "com.google.firebase:firebase-crashlytics:${Versions.Libs.firebaseCrashlyticsVersion}"

        const val playServicesMap =
            "com.google.android.gms:play-services-maps:${Versions.Libs.playServicesMapVersion}"
        const val playServicesLocation =
            "com.google.android.gms:play-services-location:${Versions.Libs.playServicesLocationVersion}"

        const val glide = "com.github.bumptech.glide:glide:${Versions.Libs.glideVersion}"
        const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.Libs.glideVersion}"
        const val glideOkHttp =
            "com.github.bumptech.glide:okhttp3-integration:${Versions.Libs.glideVersion}"

        const val flexbox = "com.google.android:flexbox:${Versions.Libs.flexboxVersion}"

        const val koin = "org.koin:koin-android:${Versions.Libs.koinVersion}"
        const val koinCore = "org.koin:koin-core:${Versions.Libs.koinVersion}"
        const val koinScope = "org.koin:koin-android-scope:${Versions.Libs.koinVersion}"
        const val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.Libs.koinVersion}"

        const val jsonwebtoken = "io.jsonwebtoken:jjwt:${Versions.Libs.jwtVersion}"
        const val workManager = "androidx.work:work-runtime:${Versions.Libs.workManagerVersion}"
        const val workManagerKTX =
            "androidx.work:work-runtime-ktx:${Versions.Libs.workManagerVersion}"

        const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.Libs.rxJavaVersion}"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.Libs.rxAndroidVersion}"

        const val kotlinSerialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Libs.kotlinSerializationVersion}"

        // UI
        const val androidChart = "com.github.PhilJay:MPAndroidChart:${Versions.Libs.androidChartVersion}"

        const val belcorpComponentsCore = "biz.belcorp.mobile.components:core:${Versions.Libs.belcorpComponentsVersion}"
        const val belcorpComponentsDesign = "biz.belcorp.mobile.components:design:${Versions.Libs.belcorpComponentsVersion}"
        const val belcorpComponentsDialogs = "biz.belcorp.mobile.components:dialogs:${Versions.Libs.belcorpComponentsVersion}"
        const val belcorpComponentsLogin = "biz.belcorp.mobile.components:login:${Versions.Libs.belcorpComponentsVersion}"
        const val belcorpComponentsCharts = "biz.belcorp.mobile.components:charts:${Versions.Libs.belcorpComponentsVersion}"
        const val belcorpComponentsOffers = "biz.belcorp.mobile.components:offers:${Versions.Libs.belcorpComponentsVersion}"

        // Data
        const val dbflow = "com.github.agrosner.DBFlow:dbflow:${Versions.Libs.dbflowVersion}"
        const val dbflowCore =
            "com.github.agrosner.DBFlow:dbflow-core:${Versions.Libs.dbflowVersion}"
        const val dbflowProcessor =
            "com.github.agrosner.DBFlow:dbflow-processor:${Versions.Libs.dbflowVersion}"
        const val dbflowKotlin =
            "com.github.agrosner.DBFlow:dbflow-kotlinextensions:${Versions.Libs.dbflowKotlinVersion}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.Libs.retrofitVersion}"
        const val retrofitGsonConverter =
            "com.squareup.retrofit2:converter-gson:${Versions.Libs.retrofitVersion}"
        const val retrofitScalarsConverter =
            "com.squareup.retrofit2:converter-scalars:${Versions.Libs.retrofitVersion}"
        const val retrofitKotlixConverter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.Libs.retrofitKotlinxVersion}"
        const val retrofitRxJavaAdapter =
            "com.squareup.retrofit2:adapter-rxjava2:${Versions.Libs.retrofitVersion}"
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.Libs.okHttpVersion}"
        const val okHttpInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Libs.okHttpInterceptorVersion}"
        const val objectbox = "io.objectbox:objectbox-android:${Versions.Libs.objectboxVersion}"
        const val objectboxKotlin =
            "io.objectbox:objectbox-kotlin:${Versions.Libs.objectboxVersion}"
        const val objectboxBrowser =
            "io.objectbox:objectbox-android-objectbrowser:${Versions.Libs.objectboxVersion}"
        const val playCore = "com.google.android.play:core:${Versions.Libs.playCore}"
        const val gson = "com.google.code.gson:gson:${Versions.Libs.gsonVersion}"

        const val kinesis = "com.amazonaws:aws-android-sdk-kinesis:${Versions.Libs.kinesisVersion}"

        // Debug
        const val stetho = "com.facebook.stetho:stetho:${Versions.Libs.stethoVersion}"
        const val crashlytics =
            "com.crashlytics.sdk.android:crashlytics:${Versions.Libs.crashlyticsVersion}"
        const val newRelic =
            "com.newrelic.agent.android:android-agent:${Versions.Libs.newRelicVersion}"

        // SpinnerDatePicker
        const val spinnerDatePicker =
            "com.github.drawers:SpinnerDatePicker:${Versions.Libs.spinnerDatePickerVersion}"

        // Google Maps
        const val googleMap =
            "com.google.android.gms:play-services-maps:${Versions.Libs.googleMapVersion}"

        // Google Location
        const val googleLocation =
            "com.google.android.gms:play-services-location:${Versions.Libs.googleLocationVersion}"

        // Design
        const val design = "com.android.support:design:${Versions.Libs.design}"


        // Expandable
        const val expandable =
            "com.github.aakira:expandable-layout:${Versions.Libs.expandableVersion}"

        // CircleImage
        const val circleImage = "de.hdodenhof:circleimageview:${Versions.Libs.circleImageVersion}"
        const val circularprogressbar = "com.mikhaellopez:circularprogressbar:${Versions.Libs.circularProgressBarVersion}"

        const val sectionedRecyclerViewAdapter = "io.github.luizgrp.sectionedrecyclerviewadapter:sectionedrecyclerviewadapter:${Versions.Libs.sectionedRecyclerViewAdapter}"

        //Root validation
        const val rootbeer = "com.scottyab:rootbeer-lib:${Versions.Libs.rootBeer}"

        //Encrypt Sharedpreferences
        const val securityCrypto = "androidx.security:security-crypto:${Versions.Libs.securityCrypto}"

    }

    object Test {
        const val junit = "junit:junit:${Versions.Test.junitVersion}"
        const val espressoCore =
            "androidx.test.espresso:espresso-core:${Versions.Test.espressoCoreVersion}"
        const val runner = "com.android.support.test:runner:${Versions.Test.runnerVersion}"
        const val koinTest = "org.koin:koin-test:${Versions.Test.koinTestVersion}"
        const val junit5Api = "org.junit.jupiter:junit-jupiter-api:${Versions.Test.jUnit5Version}"
        const val junit5Params =
            "org.junit.jupiter:junit-jupiter-params:${Versions.Test.jUnit5Version}"
        const val junit5Engine =
            "org.junit.jupiter:junit-jupiter-engine:${Versions.Test.jUnit5Version}"
        const val roboelectric = "org.robolectric:robolectric:${Versions.Test.roboelectricVersion}"
        const val roboelectricMulidex =
            "org.robolectric:shadows-multidex:${Versions.Test.roboelectricVersion}"
        const val mockitoKotlin =
            "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.Test.mockitoKotlinVersion}"
        const val androidTestCore = "androidx.test:core:${Versions.Test.androidTestCoreVersion}"
        const val androidTestExt = "androidx.test.ext:junit:${Versions.Test.androidTestExtVersion}"
        const val androidTestRunner = "androidx.test:runner:${Versions.Test.androidTestCoreVersion}"
        const val androidTestRules = "androidx.test:rules:${Versions.Test.androidTestCoreVersion}"
        const val androidArchTestCore =
            "android.arch.core:core-testing:${Versions.Test.androidArchTestCoreVersion}"
        const val kluent = "org.amshove.kluent:kluent-android:${Versions.Test.kluentVersion}"
        const val mockk = "io.mockk:mockk:${Versions.Test.mockkVersion}"
        const val mockkAndroid = "io.mockk:mockk-android:${Versions.Test.mockkVersion}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Test.coroutines}"
        const val workManager = "androidx.work:work-testing:${Versions.Libs.workManagerVersion}"
    }
}
