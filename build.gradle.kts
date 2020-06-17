import de.undercouch.gradle.tasks.download.Download
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

plugins {
    java
    id ("de.undercouch.download") version "4.0.4"
    id ("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "me.ihdeveloper"
version = "0.1"

// The server to run the plugins on it
val server = Server()

// The build tools to use the Bukkit api
val buildTools = BuildTools(

        // Server Version
        minecraftVersion = "1.8.8",

        // Spigot = true
        // Craftbukkit = false
        useSpigot = true
)

subprojects {

    apply(plugin = "java")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    dependencies {
        // Include the common source to every project ( except common )
        if (project.name != "Common") {
            implementation(project(":Common"))
        }

        // Include the server jar source
        compileOnly(files(buildTools.serverJar.absolutePath))

        testCompileOnly("junit", "junit", "4.12")
    }

    tasks {

        shadowJar {

            // If there's no version implemented then we give a default version to the project
            if (archiveVersion.orNull == null) {
                archiveVersion.set("0.1")
            }

            // Change the generated archive name
            val name = "${archiveBaseName.get()}-${archiveVersion.get()}.${archiveExtension.get()}"
            archiveFileName.set(name)
        }

        /**
         * Overwrite the build task to put the compiled jar into the build folder instead of build/libs
         */
        build {
            if (project.name != "Common") {
                dependsOn("shadowJar")
            }

            doLast {
                val pluginJar = project.buildDir.absolutePath + "/libs/" + shadowJar.get().archiveFileName.get()

                // Copy the compiled plugin jar from build/libs to build/
                if (project.name != "Common") {
                    copy {
                        from(pluginJar)
                        into(rootProject.buildDir)
                    }
                }
            }
        }

        /**
         * Copy the generated plugin jar of the project to the server plugins folder
         */
        register("copy-to-server") {
            dependsOn("build")

            doLast {
                val pluginJar = project.buildDir.absolutePath + "/libs/" + shadowJar.get().archiveFileName.get()

                // Copy the compiled plugin jar to the server/plugins
                copy {
                    from(pluginJar)
                    into(server.plugins)
                    rename {
                        "${project.name}.jar"
                    }
                }
            }
        }

        /**
         * Run the server with the plugin on it
         */
        register("run") {
            dependsOn(":build-server")
            dependsOn(":clean-plugins")
            dependsOn("copy-to-server")
            dependsOn(":run")
        }

    }

}

tasks {

    /**
     * Delete the server after the gradle clean task is done
     */
    getByName("clean").doLast {
        // Delete the server folder
        server.delete()
    }

    /**
     * Clean the plugins jars in the plugins folder of the server
     */
    register("clean-plugins") {
        doLast {
            for (file in server.plugins.listFiles()) {
                if (!file.name.endsWith(".jar"))
                    continue
                file.delete()
            }
        }
    }

    /**
     * Setup the workspace to develop the plugin
     */
    register("setup") {

        // Build the plugin to be able to test it
        dependsOn("build-server")
    }

    /**
     * Download the build tools
     */
    register<Download>("download-build-tools") {
        onlyIf {
            !buildTools.file.exists()
        }

        val temp = buildTools.buildDir

        // Check if the temporary folder doesn't exist
        if (!temp.exists())
            temp.mkdir() // Create the temporary folder

        // Check if the temporary folder is file
        if (temp.isFile)
            error("Can't use the folder [.build-tools] because it's a file")

        // Download the latest successful build of SpigotMC/BuildTools
        src("https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar")

        // Put it in the .build-tools/
        dest(buildTools.file)
    }

    /**
     * Run build tools to create tools for the workspace
     */
    register("run-build-tools") {
        dependsOn("download-build-tools")

        onlyIf {
            !buildTools.serverJar.exists()
        }

        doLast {
            // Run the build tools to generate the server
            javaexec {
                workingDir = buildTools.buildDir
                main = "-jar"
                args = mutableListOf<String>(
                        buildTools.file.absolutePath,
                        "--rev",
                        buildTools.minecraftVersion
                )
            }
        }
    }

    /**
     * Build the server to test the plugin on it
     */
    register("build-server") {
        dependsOn("run-build-tools")

        val server = server

        onlyIf {
            !server.exists
        }

        server.mkdir()

        doLast {
            // Print the EULA to the user
            printEULA()

            // Wait for 10 seconds to realise the message
            try {
                Thread.sleep(10 * 1000)
            } catch (e: Exception) {}

            // Since the process didn't stop
            // This means the user indicates to agree on the Minecraft EULA
            // And this code automates the indicates process
            val eula = server.eula
            if (eula.exists()) {
                var text = eula.readText()
                text = text.replace("eula=false", "eula=true", true)
                eula.writeText(text)
            } else {
                eula.writeText("eula=true")
            }


            // Copy the selected compiled server jar to the server folder
            copy {
                from(buildTools.serverJar)
                into(server.dir)
                rename {
                    "server.jar"
                }
            }

            // Sends "stop" command to the server to stop after initialising
            val stopCommand = "stop"
            val input = ByteArrayInputStream(stopCommand.toByteArray(StandardCharsets.UTF_8))

            // Run the server to initialise everything and then it executes the command "stop" to stop itself after getting the environment ready
            javaexec {
                standardInput = input
                workingDir = server.dir
                main = "-jar"
                args = mutableListOf<String>(
                        server.jar.absolutePath
                )
            }

            // Close the input after the termination of the server
            input.close()
        }
    }

    /**
     * Run the server
     */
    register("run") {
        doLast {
            printIntro()
            logger.lifecycle("> Starting the server...")
            logger.lifecycle("")

            // Run the server to test the plugin
            javaexec {
                standardInput = System.`in`
                workingDir = server.dir
                main = "-jar"
                args = mutableListOf(
                        server.jar.absolutePath
                )
            }
        }
    }

}

/**
 * Print to the user that using the kit indicates that his/her agreement to Minecraft's EULA
 */
fun printEULA() {
    val eulaInfo = mutableListOf(
            " _____________________________________________________________________________________",
            "|  _________________________________________________________________________________  |",
            "| |                                                                                 | |",
            "| |                        ███████╗██╗   ██╗██╗      █████╗                         | |",
            "| |                        ██╔════╝██║   ██║██║     ██╔══██╗                        | |",
            "| |                        █████╗  ██║   ██║██║     ███████║                        | |",
            "| |                        ██╔══╝  ██║   ██║██║     ██╔══██║                        | |",
            "| |                        ███████╗╚██████╔╝███████╗██║  ██║                        | |",
            "| |                                                                                 | |",
            "| |                                                                                 | |",
            "| |                       [#] By using Spigot Starter Kit [#]                       | |",
            "| |                                                                                 | |",
            "| |              You are indicating your agreement to Minecraft's EULA              | |",
            "| |               https://account.mojang.com/documents/minecraft_eula               | |",
            "| |_________________________________________________________________________________| |",
            "|_____________________________________________________________________________________|"
    )

    // Separate the EULA for more attention
    for (i in 1..3) {
        logger.lifecycle("")
    }

    for (i in eulaInfo) {
        logger.lifecycle(i)
    }

    // Separate the EULA for more attention
    logger.lifecycle("")
}

fun printIntro() {
    val intro = arrayOf(
            """   _____       _             __  """,
            """  / ___/____  (_)___ _____  / /_ """,
            """  \__ \/ __ \/ / __ `/ __ \/ __/ """,
            """  ___/ / /_/ / / /_/ / /_/ / /_  """,
            """/____/ .___/_/\__, /\____/\__/   """,
            """    /_/      /____/              """,
            """                                 """,
            """    [#] Spigot Starter Kit [#]   """,
            """                                 """
    )
    for (line in intro) {
        logger.lifecycle(line)
    }
}

class BuildTools (
        val minecraftVersion: String,
        val useSpigot: Boolean
) {
    val buildDir = File(".build-tools")
    val file = File(buildDir, "build-tools.jar")
    val libsDir = File("build/libs/")

    val serverJar = if (useSpigot) {
        File(buildDir, "spigot-${minecraftVersion}.jar")
    } else {
        File(buildDir, "craftbukkit-${minecraftVersion}.jar")
    }
}

/**
 * Help making the server and structuring it
 */
class Server {

    /**
     * Directory of the server
     */
    val dir: File get() { return File(rootProject.projectDir, "server") }

    /**
     * Plugins of the sever
     */
    val plugins: File get() { return File(dir, "plugins") }

    /**
     * Server jar that manages the server
     */
    val jar: File get() { return File(dir, "server.jar") }

    /**
     * Minecraft's EULA file
     */
    val eula: File get() { return File(dir, "eula.txt") }

    /**
     * Does the server exist in the right way
     */
    val exists: Boolean
        get() {
            return dir.exists() and plugins.exists() and jar.exists() and eula.exists()
        }

    /**
     * Make the directories required for the server
     */
    fun mkdir() {
        dir.mkdir()
        plugins.mkdir()
    }

    /**
     * Delete the server
     */
    fun delete() {
        // Delete everything including the directory itself
        dir.deleteRecursively()

        // Create an empty directory for better user experience
        dir.mkdir()
    }
}
