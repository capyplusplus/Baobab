package org.example.project.model

import java.io.File
import kotlinx.serialization.json.*
import kotlin.math.max

object Paths {
    const val FOLDERS = "Folders"
    const val CAMERA = "Camera"
}

object JsonSystem {

    fun save() {
        val root = buildJsonObject {
            put("nextId", AppState.nextId)

            putJsonArray("folders") {
                AppState.Folders.forEach { folder ->
                    add(
                        buildJsonObject {
                            put("id", folder.id)
                            put("name", folder.name)

                            putJsonObject("position") {
                                put("x", folder.position.x)
                                put("y", folder.position.y)
                            }
                        }
                    )
                }
            }
        }
        File(Paths.FOLDERS).writeText(root.toString())
        File(Paths.CAMERA).writeText("${AppState.camera.x},${AppState.camera.y}")
    }

    fun loadCamera() {
        val file = File(Paths.CAMERA)
        val pos = file.readText()
        val vars = pos.split(',')
        AppState.camera = Camera(vars[0].toFloat(), vars[1].toFloat())
    }

    fun loadFolders() {
        val file = File(Paths.FOLDERS)

        if (!file.exists()) return

        val root = Json.parseToJsonElement(file.readText()).jsonObject

        AppState.nextId = root["nextId"]!!.jsonPrimitive.long

        AppState.Folders.clear()

        root["folders"]!!.jsonArray.forEach { element ->
            val obj = element.jsonObject
            val pos = obj["position"]!!.jsonObject

            AppState.Folders.add(
                Folder(
                    name = obj["name"]!!.jsonPrimitive.content,
                    id = obj["id"]!!.jsonPrimitive.int,
                    position = Position(
                        pos["x"]!!.jsonPrimitive.float,
                        pos["y"]!!.jsonPrimitive.float
                    )
                )
            )
            AppState.nextId = max(AppState.nextId, obj["id"]!!.jsonPrimitive.long + 1)
        }
    }
}