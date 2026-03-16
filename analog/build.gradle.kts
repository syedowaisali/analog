plugins {
    alias(libs.plugins.android.library)
    id("com.vanniktech.maven.publish") version "0.36.0"
}

android {
    namespace = "com.mumdyverse.analog"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
}

mavenPublishing {

    publishToMavenCentral()

    signAllPublications()

    coordinates("com.mumdyverse", "analog", "1.0.1")

    pom {
        name.set("Analog Library")
        description.set("A lightweight DSL based Android logging library")
        inceptionYear.set("2026")
        url.set("https://github.com/syedowaisali/analog")
        licenses {
            license {
                name.set("The MIT License")
                url.set("https://opensource.org")
            }
        }
        developers {
            developer {
                id.set("syedowaisali")
                name.set("Syed Owais Ali")
                email.set("dp.owaisali@gmail.com")
            }
        }
        scm {
            connection.set("scm:git:github.com/syedowaisali/analog.git")
            developerConnection.set("scm:git:ssh://github.com/syedowaisali/analog.git")
            url.set("https://github.com/syedowaisali/analog")
        }
    }
}