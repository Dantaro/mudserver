package com.dev.eloper.mud.room

class RoomService {

}

data class Room(
    val roomId: String,
    val roomContents: List<RoomObject>,
    val roomSizeX: Int,
    val roomSizeY: Int
)

sealed class RoomObject(name: String, traits: List<Trait>, posX: Int, posY: Int)

data class RoomItem(val name: String, val traits: List<Trait>, val posX: Int, val posY: Int)
    : RoomObject(name, traits, posX, posY)

data class RoomTerrain(val name: String, val traits: List<Trait>, val posX: Int, val posY: Int) :
    RoomObject(name, traits, posX, posY)