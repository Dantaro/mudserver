package com.dev.eloper.mud.room

val testRoom = Room(
    roomId = "TEST",
    roomContents = listOf(
        RoomTerrain("Forest", emptyList(), 0, 0),
        RoomTerrain("Forest", emptyList(), 1, 0),
        RoomTerrain("Path", listOf(Traits.TRAVERSABLE), 2, 0),
        RoomTerrain("Forest", listOf(Traits.TRAVERSABLE), 0, 1),
        RoomTerrain("Path", listOf(Traits.TRAVERSABLE), 1, 1),
        RoomTerrain("Path", listOf(Traits.TRAVERSABLE), 2, 1),
        RoomTerrain("Path", listOf(Traits.TRAVERSABLE), 0, 2),
        RoomTerrain("Path", listOf(Traits.TRAVERSABLE), 1, 2),
        RoomTerrain("Path", listOf(Traits.TRAVERSABLE), 2, 2)
    ),
    roomSizeX = 3,
    roomSizeY = 3
)

val rooms = listOf(testRoom).associateBy { it.roomId }