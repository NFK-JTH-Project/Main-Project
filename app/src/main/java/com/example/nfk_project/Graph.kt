import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet


class rNode(name: String, directions: String): Serializable{
    var name: String = name
    var directions: String = directions
    var adjList: LinkedList<rNode> = LinkedList()

    override fun toString(): String {
        return "${name}"
    }
}
class Graph: Serializable{
    var graph: HashMap<String, rNode> = HashMap()
    var nodePath: ArrayList<rNode> = ArrayList() // Empty this after returning ?

    fun getRoom(name: String): rNode? {
        return graph.get(name)
    }
    fun addRoom(room: rNode){
        if(!graph.contains(room.name))
            graph.put(room.name, room)
    }
    fun setConnectionBetweenRooms(room1: rNode, room2: rNode){
        if (room1 != null && room2 != null){
            if(!room1.adjList.contains(room2)){
                room1.adjList.push(room2)
            }
            if(!room2.adjList.contains(room1)) {
                room2.adjList.add(room1)
            }
        }
    }
    fun getRoomNames(): ArrayList<String>{
        var roomNames: ArrayList<String> = ArrayList()
        for(key in graph.keys){
            roomNames.add(key)
        }
        return roomNames
    }

    //Change name
    fun getPath(start: String, end: String):String{
        var visited: HashSet<String> = HashSet()
        var directions = ""
        var start: rNode = getRoom(start)!!
        var destination: rNode = getRoom(end)!!

        nodePath.clear()

        if(findPathDFS(start, destination, visited)){
            var roomsOnPath = nodePath.reversed()

            var iterator = roomsOnPath.size

            for(i in 0 until iterator){
                directions+="Step${i+1}: " + roomsOnPath[i].directions
                directions+="\n"
            }
        }
        else{
            directions = "The room was not recognized."
        }
        return directions
    }
    //Change name
    fun findPathDFS(start: rNode, destination: rNode, visited: HashSet<String>): Boolean {
        if(visited.contains(start.name)){
            return false
        }
        visited.add(start.name)
        if(start == destination){
            return true
        }
        for (nextToCheck in start.adjList){
            if(findPathDFS(nextToCheck, destination, visited)) {
                nodePath.add(nextToCheck)
                return true
            }
        }
        return false
    }
    override fun toString(): String {
        var s = ""
        for(key in graph.keys){
            s+= key
            s+= "-->"
            s+= graph.get(key)?.adjList
            s+= "\n"
        }
        return s
    }

}