package org.example.project.model

import java.io.File
import kotlinx.serialization.json.*

object JsonSystem {

    fun save(path: String) {
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

        File(path).writeText(root.toString())
    }

    fun load(path: String) {
        val file = File(path)

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
        }
    }
}