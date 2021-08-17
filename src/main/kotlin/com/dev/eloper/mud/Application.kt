package com.dev.eloper.mud

import com.dev.eloper.mud.room.Room
import com.dev.eloper.mud.service.User
import com.dev.eloper.mud.service.UserService
import com.google.gson.*
import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.LinkedHashSet

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

val gson = Gson()

@Suppress("unused")
fun Application.module() {
    install(WebSockets)

    routing {
        val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
        webSocket("/mud") {
            //Setup
            val connection = Connection(this)
            connections += connection
            var currentRoom: Room? = null

            send(
                createResponseString(
                    responseType = SocketResponseType.SYSTEM_MESSAGE,
                    userMessage = "You are connected!"
                )
            )
            send(
                createResponseString(
                    responseType = SocketResponseType.SYSTEM_MESSAGE,
                    userMessage = "What is your name?"
                )
            )
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText: String = frame.readText()
                val userMessage = gson.fromJson(receivedText, SocketMessage::class.java)
                if (connection.user == null) {
                    // If the user isn't "logged in"
                    if (userMessage.userMessage.isNullOrBlank()) {
                        send(
                            createResponseString(
                                responseType = SocketResponseType.SYSTEM_MESSAGE,
                                userMessage = "Please give yourself a name!"
                            )
                        )
                    } else {
                        val createdUser = UserService.createUser(userMessage.userMessage)
                        connection.user = createdUser
                        send(
                            createResponseString(
                                responseType = SocketResponseType.SYSTEM_MESSAGE,
                                userMessage = "Welcome ${createdUser.name}!",
                                systemData = mapOf("userId" to createdUser.userId)
                            )
                        )
                    }
                } else {
                    // The user is logged in
                    if (currentRoom == null) {
                        if(userMessage.messageType == SocketMessageType.CHANGE_ROOM) {

                        } else {

                        }
                    }
                    if (userMessage.messageType == SocketMessageType.CHAT) {
                        connections.forEach {
                            it.session.send(
                                createResponseString(
                                    responseType = SocketResponseType.CHAT,
                                    userMessage = userMessage.userMessage,
                                    systemData = mapOf(
                                        "userName" to connection.user?.name
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

fun createResponseString(
    responseType: SocketResponseType,
    userMessage: String? = null,
    systemData: Map<String, Any?>? = null
): String {
    return gson.toJson(
        SocketResponse(responseType = responseType, userMessage = userMessage, systemData = systemData)
    )
}

data class SocketMessage(val userMessage: String?, val messageType: SocketMessageType?)
data class SocketResponse(
    val responseType: SocketResponseType,
    val userMessage: String? = null,
    val systemData: Map<String, Any?>? = null
)