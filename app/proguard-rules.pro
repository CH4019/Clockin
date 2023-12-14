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
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.huawei.hianalytics.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-dontwarn com.huawei.hms.**
-keep class com.huawei.hms.**{*;}
-dontwarn org.bouncycastle.**
-keep class org.bouncycastle.** {*;}


# proguard.cfg
-keepattributes Signature
-dontwarn com.jcraft.jzlib.**
-keep class com.jcraft.jzlib.**  { *;}
-dontwarn sun.misc.**
-keep class sun.misc.** { *;}
-dontwarn retrofit2.**
-keep class retrofit2.** { *;}
-dontwarn io.reactivex.**
-keep class io.reactivex.** { *;}
-dontwarn sun.security.**
-keep class sun.security.** { *; }
-dontwarn com.google.**
-keep class com.google.** { *;}
-dontwarn cn.leancloud.**
-keep class cn.leancloud.** { *;}
-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient
-dontwarn android.webkit.WebView
-dontwarn android.net.http.SslError
-dontwarn android.webkit.WebViewClient
-dontwarn android.support.**
-dontwarn org.apache.**
-keep class org.apache.** { *;}
-dontwarn okhttp3.**
-keep class okhttp3.** { *;}
-keep interface okhttp3.** { *; }
-dontwarn okio.**
-keep class okio.** { *;}
-keepattributes *Annotation*


-dontwarn org.slf4j.impl.StaticLoggerBinder
-keep class org.slf4j.LoggerFactory

# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
#-dontwarn com.google.errorprone.annotations.MustBeClosed