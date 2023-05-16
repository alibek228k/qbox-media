plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

repositories{
    mavenCentral()
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "shared"
            isStatic = false
            export("dev.icerock.moko:permissions:0.16.0")
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies{
                api("dev.icerock.moko:permissions:0.16.0")
            }
        }
        val androidMain by getting {
            dependencies{
                implementation("androidx.core:core-ktx:1.10.1")
                implementation("androidx.core:core:1.10.1")
                implementation("androidx.activity:activity-ktx:1.7.1")
                implementation("androidx.fragment:fragment-ktx:1.5.7")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "kz.qbox.qbox_media"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}
