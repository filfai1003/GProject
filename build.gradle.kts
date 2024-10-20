plugins {
    java
    application
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


group = "com.gproject"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("com.google.code.gson:gson:2.8.9")

    implementation ("org.lwjgl:lwjgl:3.2.3")
    implementation ("org.lwjgl:lwjgl-glfw:3.2.3")
    implementation ("org.lwjgl:lwjgl-opengl:3.2.3")

    runtimeOnly ("org.lwjgl:lwjgl:3.2.3:natives-linux")
    runtimeOnly ("org.lwjgl:lwjgl-glfw:3.2.3:natives-linux")
    runtimeOnly ("org.lwjgl:lwjgl-opengl:3.2.3:natives-linux")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainClass.set("com.gproject.core.Game")

    // Imposta il percorso delle librerie native
    applicationDefaultJvmArgs = listOf(
        "-Dorg.lwjgl.librarypath=${buildDir}/natives"
    )
}

// Estrai i file nativi per LWJGL
tasks.register<Copy>("copyNatives") {
    from(configurations.runtimeClasspath.get().filter { it.name.contains("natives") }.map { zipTree(it) })
    into("${buildDir}/natives")

    // Ignora i file duplicati (come META-INF/INDEX.LIST)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}


tasks.named("run") {
    dependsOn("copyNatives")
}
