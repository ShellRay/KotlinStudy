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


# greendao 3.2.0混淆
-keepclassmembersclass * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
}

#greedao sqlite
-keep class com.kotlin.study.greendaogenlib.** {*;}
-keep class com.kotlin.study.greendaogenlib.utils.* {*;}

-keep class **$Properties

# If you do not use SQLCipher:
-dontwarnorg.greenrobot.greendao.database.**
# If you do not use Rx:
-dontwarn rx.**

