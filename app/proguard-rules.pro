# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-printusage usages.txt
-printseeds seeds.txt
-printmapping mapping.txt

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

-dontoptimize
-dontpreverify

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification

-keepclasseswithmembernames class biz.belcorp.salesforce.** {
    native <methods>;
}

-keepattributes InnerClasses

-keepnames class biz.belcorp.salesforce.**.dto.** { *; }
-keep class biz.belcorp.salesforce.**.dto.**$* { *; }

# For AndroidX

-dontwarn com.google.android.material.**
-keep class com.google.android.material.** { *; }

-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

-keep class android.support.annotation.Keep
-keep class androidx.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}
-keep @androidx.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <init>(...);
}

-keep class * extends androidx.** { *; }

# For Squareup
-dontwarn com.squareup.okhttp.**

# For DBFlow
-keep class * extends com.raizlabs.android.dbflow.config.DatabaseHolder { *; }
-keep class * extends com.raizlabs.android.dbflow.runtime.BaseContentProvider { *; }

# For Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# For Google Play
-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**

-keep class **.R
-keep class **.R$* {
    public static <fields>;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keepattributes Exceptions

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

##---------------End: proguard configuration for Gson  ----------

# ServiceLoader support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}

-keep class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keep class kotlinx.coroutines.CoroutineExceptionHandler {}
-keep class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keep class kotlinx.coroutines.android.AndroidDispatcherFactory {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

-keep class kotlin.reflect.jvm.internal.** { *; }

# Kotlinx serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class biz.belcorp.salesforce.**$$serializer { *; } # <-- change package name to your app's
-keepclassmembers class biz.belcorp.salesforce.** { # <-- change package name to your app's
    *** Companion;
}
-keepclasseswithmembers class biz.belcorp.salesforce.** { # <-- change package name to your app's
    kotlinx.serialization.KSerializer serializer(...);
}

# Kinesis

-keepnames class com.amazonaws.**
-keepnames class com.amazon.**

-keep class com.amazonaws.services.**.*Handler

-dontwarn com.fasterxml.jackson.**
-dontwarn org.apache.commons.logging.**

-dontwarn org.apache.http.**

-dontwarn com.amazonaws.http.**
-dontwarn com.amazonaws.metrics.**

# DependenciesProvider
-keep class * implements biz.belcorp.salesforce.core.di.features.DependenciesProvider { *; }

# ArgType classes for navigation
-keep class biz.belcorp.salesforce.core.entities.zonificacion.Rol { *; }
-keep class biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA { *; }
-keep class biz.belcorp.salesforce.core.domain.usecases.browser.WebTopic { *; }

# Enums

-keep public enum biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddXUaEntity$TipoCambio {
    **[] $VALUES;
    public *;
}

# Exceptions CrashLytics
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

# Other Rules

-keep class biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.sync.SyncResponse { *; }
-keep class biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.sync.SyncResponse$Result { *; }
-keep class biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.sync.SyncResponse$Result$Data { *; }
-keep class biz.belcorp.salesforce.modules.calculator.core.domain.entities.CalculatingResultDetail { *; }
-keep class biz.belcorp.salesforce.modules.postulants.core.data.entities.unete.DevueltoSacEntity { *; }
-keep class biz.belcorp.salesforce.modules.postulants.core.data.entities.unete.BuroResponsePostulanteEntity { *; }
-keep class biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.ValidarRegionZonaEntity { *; }
-keep class biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.PrePostulantesContenidoResponse { *; }
-keep class biz.belcorp.salesforce.base.utils.caoc.Caoc { *; }

# New Relic

-keep class com.newrelic.** { *; }
-dontwarn com.newrelic.**
-keepattributes Exceptions, Signature, InnerClasses, LineNumberTable, SourceFile, EnclosingMethod


# security crypto
-keep class com.google.crypto.** { *; }

# Keep `Companion` object fields of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# @Serializable and @Polymorphic are used at runtime for polymorphic serialization.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

# Don't print notes about potential mistakes or omissions in the configuration for kotlinx-serialization classes
# See also https://github.com/Kotlin/kotlinx.serialization/issues/1900
-dontnote kotlinx.serialization.**

# Serialization core uses `java.lang.ClassValue` for caching inside these specified classes.
# If there is no `java.lang.ClassValue` (for example, in Android), then R8/ProGuard will print a warning.
# However, since in this case they will not be used, we can disable these warnings
-dontwarn kotlinx.serialization.internal.ClassValueReferences
