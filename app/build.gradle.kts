plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("jacoco")
}

android {
    namespace = "com.sharpedge.githubusers"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sharpedge.githubusers"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testCoverage {

        jacocoVersion = "0.8.7" // Check for the latest version compatible with your setup
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            // Any other unit test options you need
        }
    }

}

//sourceSets {
//    val test by getting {
//        java.srcDirs("src/test/kotlin", "src/test/java")
//    }
//}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("io.coil-kt:coil-compose:2.0.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("io.insert-koin:koin-android:3.1.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:4.0.0")
    testImplementation ("org.mockito:mockito-inline:3.11.2")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation ("app.cash.turbine:turbine:0.7.0")
    testImplementation ("org.hamcrest:hamcrest:2.2")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2") // Use the latest version
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")

}

//tasks.withType<Test> {
////    val testDebugUnitTest by existing(Test::class) {
////        // Configure your unit test task if needed
////    }
//    useJUnitPlatform()
//    extensions.configure(JacocoTaskExtension::class) {
//        isEnabled = true
//    }
//
//
//    val jacocoTestReport = tasks.create("jacocoTestReport",JacocoReport::class) {
//        dependsOn("testDebugUnitTest")
//        group = "Reporting"
//        description = "Generate Jacoco coverage reports"
//
//        reports {
//            xml.required.set(true)
//            html.required.set(true)
//        }
//
//        val debugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug").apply {
//            exclude("**/R.class")
//            exclude("**/R$*.class")
//            exclude("**/BuildConfig.*")
//            exclude("**/Manifest*.*")
//            exclude("**/androidx/lifecycle/*")
//            // Exclude Retrofit related classes (such as Retrofit builders or converters)
//            exclude("**/*Retrofit*")
//            exclude("**/*Converter*")
//        }
//
//        sourceDirectories.setFrom(files("src/main/kotlin", "src/main/java"))
//        classDirectories.setFrom(debugTree)
//        executionData.setFrom(fileTree(buildDir).include("/jacoco/testDebugUnitTest.exec"))
//    }
//}


tasks.withType<Test> {
    //useJUnitPlatform()
    extensions.configure(JacocoTaskExtension::class) {
        isEnabled = true
    }

//    violationRules {
//        rule {
//            limit {
//                minimum = BigDecimal(0.62)
//            }
//        }
//    }

//    afterEvaluate {
//        classDirectories.setFrom(files(classDirectories.files.map {
//            fileTree(it).apply {
//                exclude("com/generate/**")
//            }
//        }))
//    }
}




// Make sure to check if the jacocoTestReport task already exists before creating a new one
val jacocoTestReport = tasks.findByName("jacocoTestReport") ?: tasks.create("jacocoTestReport", JacocoReport::class.java) {

    dependsOn("testDebugUnitTest")
    group = "Reporting"
    description = "Generate JaCoCo coverage reports"

    doFirst {
        println("Class files included in Jacoco report:")
        classDirectories.asFileTree.files.forEach  { file ->
            println(file)
        }
    }

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val kotlinClassesDir = fileTree("${buildDir}/tmp/kotlin-classes/debug").apply {
            exclude("**/R.class")
            exclude("**/R$*.class")
            exclude("**/BuildConfig.*")
            exclude("**/Manifest*.*")
            exclude("**/androidx/lifecycle/*")
            // Exclude Retrofit related classes (such as Retrofit builders or converters)
            exclude("**/*Retrofit*")
            exclude("**/*Converter*")
        exclude("com/sharpedge/githubusers/ui/**")
//        exclude("**/GitHubUserSearchScreenKt*.class")
//        exclude("**/UserDetailScreenKt*.class")
        exclude ("com/sharpedge/githubusers/**/*Activity.class")
        exclude ("com/sharpedge/githubusers/**/*Fragment.class")
        exclude ("com/sharpedge/githubusers/ui/**/*kt.class")
        // Exclude any other class you consider should not be part of the coverage report
    }

    sourceDirectories.setFrom(files("src/main/kotlin", "src/main/java"))
    classDirectories.setFrom(kotlinClassesDir)
    executionData.setFrom(fileTree(buildDir).include("/jacoco/testDebugUnitTest.exec"))
}


//tasks.withType<JacocoReport> {
//    afterEvaluate {
//        classDirectories.setFrom(files(classDirectories.files.map {
//            fileTree(it).apply {
//                //exclude("com/generate/**")
//                exclude("**/R.class")
//                exclude("**/R$*.class")
//                exclude("**/BuildConfig.*")
//                exclude("**/Manifest*.*")
//                exclude("**/androidx/lifecycle/*")
//                // Exclude Retrofit related classes (such as Retrofit builders or converters)
//                exclude("**/*Retrofit*")
//                exclude("**/*Converter*")
//                exclude ("com/sharpedge/githubusers/**/*Activity.class")
//                exclude ("com/sharpedge/githubusers/**/*Fragment.class")
//                exclude ("com/sharpedge/githubusers/ui/**/*kt.class")
//            }
//        }))
//    }
//}

