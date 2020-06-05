import android.os.Parcel
import android.os.Parcelable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.contracts.contract

class mDictionary() : Parcelable {
    var dictionary: HashMap<String, String> = HashMap()



    fun getNameOfNode(searched: String): String{
        val searchItem = searched.toUpperCase(Locale.ROOT)
        println("Node name: $searchItem")
        if(searched == ""){
            return "NO_ROOM"
        }
        if(dictionary.contains(searchItem))
            return searchItem

        if(dictionary.containsValue(searchItem))
            return dictionary.filterValues { it == searchItem }.keys.toString()

        if(searchItem.get(0) != 'E')
            return getNameOfOuterBuilding(searchItem)

        return getNameOfClosestNode(searchItem)
    }
    fun getNameOfClosestNode(searchItem: String):String{
        when(searchItem){
            "E4106" -> return "DA_VINCI"
            "E1423" -> return "FAGERHULTSAULAN"
            //"E1405" -> return "GJUTERISALEN"
            "E3105D" -> return "LEONARDO"
        }

        return when(val nameOfBlock = searchItem.substring(0, 3).toUpperCase(Locale.ROOT)){
            "E10" -> "E1020"
            else -> nameOfBlock + "XX"
        }
    }
    private fun getNameOfOuterBuilding(searchItem: String): String{
        if(searchItem == "A4422B"){
            return "GALLILEO"
        }

        return when(searchItem[0]){
            'A' -> "Rektorskansli"
            'B' -> "JIBS"
            'C' -> "Bibliotek"
            'D' -> "Studenternas Hus"
            'F' -> "Mariedal"
            'G' -> "Hälso"
            'H' -> "HLK"
            'J' -> "Campus"
            else -> "NOT_FOUND"
        }
    }

    fun init(nodeNames: ArrayList<String>){
        val letters = "ABCDFGHJ"
        for (l in letters){
            dictionary.put(l.toString(), "")
        }
        for(n in nodeNames) dictionary[n] = ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<mDictionary> {
        override fun createFromParcel(parcel: Parcel): mDictionary {
            return mDictionary()
        }

        override fun newArray(size: Int): Array<mDictionary?> {
            return arrayOfNulls(size)
        }
    }

}