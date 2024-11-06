import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")


}

android {
    namespace = "com.syxfree.insertcalllog"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.syxfree.insertcalllog"
        minSdk = 21
        targetSdk = 34
        versionCode = 2
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }


    signingConfigs {
        create("release"){
            storeFile = rootProject.file("./insertcalllog.jks")
            storePassword = "insertcalllog"
            keyAlias = "insertcalllog"
            keyPassword ="insertcalllog"
        }
    }


    buildTypes {
        release {
            isDebuggable = true
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
//            buildConfigField("String", "appInterface", "\"https://abolis..com/\"") //app域名地址
//            buildConfigField("String", "h5Interface", "\"https://hoxper..com/\"")
            signingConfig = signingConfigs.getByName("release")//H5域名地址
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            //app域名地址
//            buildConfigField("String", "appInterface", "\"http://192.168.5.:/\"") //app域名地址
//            buildConfigField("String", "h5Interface", "\"http://192.168.5.:/\"")//H5域名地址


        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        viewBinding = true
    }
    // 打包改名
    applicationVariants.all {
        outputs.all {
            val timestamp = SimpleDateFormat("yyyyMMddhhmm").format(Date())
            val ver = defaultConfig.versionName
            (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName =
                "InsertCallLog_${ver}_${timestamp}_${buildType.name}.apk";
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("com.guolindev.permissionx:permissionx:1.8.0")

    implementation("androidx.recyclerview:recyclerview:1.3.2")
}