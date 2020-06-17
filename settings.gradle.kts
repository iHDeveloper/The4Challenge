rootProject.name = "The4Challenge"

includeProject("Common", "common")
includeProject("SkyWars", "skywars")
includeProject("BedWars", "bedwars")
includeProject("MurderMystery", "murdermystery")
includeProject("BuildBattle", "buildbattle")

fun includeProject(name: String, dirName: String) {
    include(name)

    val dir = file(dirName)
    project(":${name}").projectDir = dir
    dir.mkdir()

    file("${dirName}/build.gradle.kts").createNewFile()
}
