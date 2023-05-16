pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "qbox-media"
include(":media")
includeBuild("convention-plugins")
