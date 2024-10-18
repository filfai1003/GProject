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
    // LWJGL core
    /*implementation("org.lwjgl:lwjgl:3.3.1")
    implementation("org.lwjgl:lwjgl-glfw:3.3.1")
    implementation("org.lwjgl:lwjgl-opengl:3.3.1")
    implementation("org.lwjgl:lwjgl-openal:3.3.1")*/
    implementation ("org.lwjgl:lwjgl:3.3.0")
    implementation ("org.lwjgl:lwjgl-glfw:3.3.0")
    implementation ("org.lwjgl:lwjgl-opengl:3.3.0")

    // Librerie native per Linux (GLFW nativo)
    /*runtimeOnly("org.lwjgl:lwjgl:3.3.1:natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.1:natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-opengl:3.3.1:natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-openal:3.3.1:natives-linux")*/
    runtimeOnly ("org.lwjgl:lwjgl:3.3.0:natives-linux")
    runtimeOnly ("org.lwjgl:lwjgl-glfw:3.3.0:natives-linux")
    runtimeOnly ("org.lwjgl:lwjgl-opengl:3.3.0:natives-linux")
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
