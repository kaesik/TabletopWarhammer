plugins {
    alias(libs.plugins.android.library)

    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.native.cocoapods)
    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.sqldelight)

    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    room {
        schemaDirectory("$projectDir/schemas")
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            isStatic = false
            baseName = "shared"
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.bundles.ktor)
            implementation(libs.bundles.coil)

            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.kotlin.date.time)

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            api(libs.koin.core)

            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(libs.supabase.postgrest)
            implementation(libs.supabase.auth)
            implementation(libs.supabase.realtime)

//            implementation(libs.mongodb.driver)
//            implementation(libs.mongodb.bson)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)

            implementation(libs.ktor.android)

            implementation(libs.sqldelight.android.driver)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }

        iosMain.dependencies {
            implementation(libs.ktor.ios)
            implementation(libs.sqldelight.native.driver)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.assertk)
            implementation(libs.turbine)
        }
        getByName("commonMain") {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
            }
        }
    }
}

android {
    namespace = "com.kaesik.tabletopwarhammer"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

sqldelight {
    databases {
        create("TabletopWarhammerDatabase") {
            packageName.set("com.kaesik.tabletopwarhammer.database")
        }
    }
}
