plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ktlint)
}

val releaseStoreFilePath = providers.gradleProperty("RELEASE_STORE_FILE")
    .orElse(providers.environmentVariable("RELEASE_STORE_FILE"))
val releaseStorePassword = providers.gradleProperty("RELEASE_STORE_PASSWORD")
    .orElse(providers.environmentVariable("RELEASE_STORE_PASSWORD"))
val releaseKeyAlias = providers.gradleProperty("RELEASE_KEY_ALIAS")
    .orElse(providers.environmentVariable("RELEASE_KEY_ALIAS"))
val releaseKeyPassword = providers.gradleProperty("RELEASE_KEY_PASSWORD")
    .orElse(providers.environmentVariable("RELEASE_KEY_PASSWORD"))
val releaseVersionNameOverride = providers.gradleProperty("RELEASE_VERSION_NAME")
    .orElse(providers.environmentVariable("RELEASE_VERSION_NAME"))
val releaseVersionCodeOverride = providers.gradleProperty("RELEASE_VERSION_CODE")
    .orElse(providers.environmentVariable("RELEASE_VERSION_CODE"))

val defaultVersionCode = 2
val defaultVersionName = "1.0.0"
val resolvedVersionName = releaseVersionNameOverride.orNull ?: defaultVersionName
val resolvedVersionCode = releaseVersionCodeOverride.orNull?.toIntOrNull() ?: defaultVersionCode

val hasReleaseSigning = listOf(
    releaseStoreFilePath.orNull,
    releaseStorePassword.orNull,
    releaseKeyAlias.orNull,
    releaseKeyPassword.orNull
).all { !it.isNullOrBlank() }

ktlint {
    version.set("1.5.0")
    android.set(true)
    outputToConsole.set(true)
    ignoreFailures.set(false)
    filter {
        exclude("**/generated/**")
    }
}

android {
    namespace = "com.pranshulgg.recordmaster"
    compileSdk = 36

    signingConfigs {
        if (hasReleaseSigning) {
            create("release") {
                storeFile = file(requireNotNull(releaseStoreFilePath.orNull))
                storePassword = requireNotNull(releaseStorePassword.orNull)
                keyAlias = requireNotNull(releaseKeyAlias.orNull)
                keyPassword = requireNotNull(releaseKeyPassword.orNull)
            }
        }
    }

    defaultConfig {
        applicationId = "com.pranshulgg.recordmaster"
        minSdk = 24
        targetSdk = 36
        versionCode = resolvedVersionCode
        versionName = resolvedVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            if (hasReleaseSigning) {
                signingConfig = signingConfigs.getByName("release")
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
            freeCompilerArgs.addAll(
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
            )
        }
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
    buildFeatures {
        compose = true
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
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.runtime)
    implementation(libs.colorpicker)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.materialKolor)
    implementation(libs.markdown.compose)
}
