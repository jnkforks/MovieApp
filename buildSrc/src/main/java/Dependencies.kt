/**
 * Created by Anggit Prayogo on 6/21/20.
 */

object ApplicationId {
    const val id = "com.anggitprayogo.movieapp"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0.0"

    const val buildToolsVersion = "29.0.3"
    const val targetSdkVersion = 29
    const val minSdkVersion = 17
}

object Version {
    /**
     * Android Support Version
     */
    const val support = "1.1.0"
    const val material = "1.0.0"
    const val cardAndRv = "1.0.0"
    const val constraintLayout = "1.1.3"
    const val coreKtx = "1.3.0"

    /**
     * Coroutine
     */
    const val coroutines = "1.3.0-M2"
    const val coroutinesAdapter = "0.9.2"

    /**
     * Dagger
     */
    const val dagger = "2.27"

    /**
     * Glide
     */
    const val glide = "4.11.0"

    /**
     * Jetpack
     */
    const val archLifecycle = "2.0.0"

    /**
     * Retrofit Network version
     */
    const val retrofit2 = "2.7.1"
    const val gson = "2.8.6"
    const val okhttpLogging = "3.8.1"

    /**
     * Room
     */
    const val room = "2.2.5"

    /**
     * Shimmer
     */
    const val shimmer = "0.5.0"

    /**
     * Lottie
     */
    const val lottie = "2.8.0"

    /**
     * Font Adapter
     */
    const val calligraphy = "3.1.1"
    const val viewPump = "2.0.3"

    /**
     * Stheto
     */
    const val stheto = "1.5.1"

    /**
     * Test
     */
    const val junitVersion = "4.13"
    const val junitExt = "1.1.1"
    const val mockitoCore = "2.25.0"
    const val androidxTest = "1.3.0-alpha01"
    const val archCore = "2.1.0"
    const val androidxEspressoCore = "3.2.0"
    const val mockitoInline = "2.19.0"
}

object AndroidSupport {
    const val appCompat = "androidx.appcompat:appcompat:${Version.support}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Version.cardAndRv}"
    const val cardView = "androidx.cardview:cardview:${Version.cardAndRv}"
    const val design = "com.google.android.material:material:${Version.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
    const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
}

object Dagger {
    const val dagger = "com.google.dagger:dagger:${Version.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Version.dagger}"
}

object Jetpack {
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Version.archLifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Version.archLifecycle}"
}

object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutines}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Version.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Version.glide}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit2}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Version.retrofit2}"
    const val gson = "com.google.code.gson:gson:${Version.gson}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Version.okhttpLogging}"
    const val coroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Version.coroutinesAdapter}"
}

object Room{
    const val room = "androidx.room:room-runtime:${Version.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Version.room}"
    const val roomKtx = "androidx.room:room-ktx:${Version.room}"
}

object Shimmer {
    const val shimmer = "com.facebook.shimmer:shimmer:${Version.shimmer}"
}

object Lottie {
    const val lottie = "com.airbnb.android:lottie:${Version.lottie}"
}

object FontAdapter {
    const val calligraphy = "io.github.inflationx:calligraphy3:${Version.calligraphy}"
    const val viewPump = "io.github.inflationx:viewpump:${Version.viewPump}"
}

object Stheto{
    const val stheto = "com.facebook.stetho:stetho:${Version.stheto}"
}

object Test {
    const val junit = "junit:junit:${Version.junitVersion}"
    const val junitExt = "androidx.test.ext:junit:${Version.junitExt}"
    const val mockitoCore = "org.mockito:mockito-core:${Version.mockitoCore}"
    const val rules = "androidx.test:rules:${Version.androidxTest}"
    const val runner = "androidx.test:runner:${Version.androidxTest}"
    const val archCore = "androidx.arch.core:core-testing:${Version.archCore}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Version.androidxEspressoCore}"
    const val mockitoInline = "org.mockito:mockito-inline:${Version.mockitoInline}"
}