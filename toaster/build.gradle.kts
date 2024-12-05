import com.vanniktech.maven.publish.SonatypeHost


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.mavenPublish)
}

kotlin {

    compilerOptions {



        freeCompilerArgs.addAll("-Xcontext-receivers",
            "-opt-in=kotlin.RequiresOptIn",
            "-Xexpect-actual-classes",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",)
    }
    composeCompiler {


        featureFlags = setOf()
    }
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    //useFirefox()
                }
            }
        }
        //binaries.library()
    }
    js {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    //useFirefox()
                }
            }
        }
        //binaries.library()
    }



    jvm("desktop")

    sourceSets {
        val desktopMain by getting


        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(libs.kotlinx.coroutines.core)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(libs.kotlinx.coroutines.test)
        }
        val desktopTest by getting {
            dependencies {
                implementation(compose.desktop.uiTestJUnit4)
            }
        }
    }
}
mavenPublishing {

    coordinates(
        groupId = "com.composevisualeditor.apolostudio",
        artifactId = "sonner-compose",
        version = libs.versions.composePlugin.toString()
    )
    println("Publishing to Maven Central with version ${libs.versions.composePlugin}")

    pom {

        name.set(project.name)
        description.set("A Compose Multiplatform library for displaying Toasts")
        inceptionYear.set("2024")
        url.set("https://github.com/apolostudio/compose-sonner-updated")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("apolostudio")
                name.set("Apolo Studio")
                email.set("contact@composevisualeditor.com")
                url.set("https://github.com/apolostudio/")
            }
        }

        scm {
            url.set("https://github.com/apolostudio/compose-sonner-updated")
        }

    }

    // Configure publishing to Maven Central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    // Enable GPG signing for all publications
    signAllPublications()

}
tasks.register("debugVersion") {
    doLast {
        println("Publishing to Maven Central with version ${libs.versions.composePlugin.get()}")
    }
}
