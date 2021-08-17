package com.dev.eloper.mud

import com.dev.eloper.mud.service.User
import io.ktor.http.cio.websocket.*

class Connection(val session: DefaultWebSocketSession, var user: User? = null)