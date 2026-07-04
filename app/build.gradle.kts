plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "cn.qwe.player"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "cn.qwe.player"
        minSdk = 31
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation("androidx.palette:palette:1.0.0")

    val media3_version = "1.2.1"
    implementation("androidx.media3:media3-exoplayer:$media3_version")
    implementation("androidx.media3:media3-session:$media3_version") // 用于车载系统控制
    implementation("androidx.media3:media3-ui:$media3_version")

    // 权限请求封装 (Accompanist)
    implementation("com.google.accompanist:accompanist-permissions:0.35.0-alpha")

    // ViewModel 和 Compose 结合
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // 图片加载 (Coil - 适合 Compose)
    implementation("io.coil-kt:coil-compose:2.6.0")
    // 引入 Compose 的完整 Material 图标库
    implementation("androidx.compose.material:material-icons-extended")
    // 解析 MP3 内嵌标签 (包含内嵌歌词和高清封面)
    implementation("androidx.media3:media3-exoplayer:1.2.1")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("net.jthink:jaudiotagger:3.0.1")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))



}