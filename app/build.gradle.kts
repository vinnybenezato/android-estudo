plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.retrofit_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.retrofit_app"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
//    testOptions{
//        unitTests.all {
//            useJUnitPlatform()
//        }
//    }


}

dependencies {

    //retrofit:
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

    //rxjava:
    implementation("io.reactivex.rxjava3:rxjava:3.1.2")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
//    implementation("junit:junit:4.12")
//    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
//    implementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    //JUnit
    testImplementation("junit:junit:4.13.2")

    //Mockk
    testImplementation("io.mockk:mockk:1.12.5")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}