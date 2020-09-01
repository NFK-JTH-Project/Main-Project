package com.example.nfk_project

class Levenshtein (){

    fun getBestMatch(compareList: ArrayList<String>, searchedString: String):String{
        var bestMatch = compareList[0]
        var distance = getLevenSteinDistance(compareList[0], searchedString)

        if(searchedString in compareList) return searchedString

        for(s in compareList){
            if(s == null) compareList.remove(s)
            else{
                var lastDistance = getLevenSteinDistance(s, searchedString)
                if (lastDistance < distance){
                    bestMatch = s
                    distance = lastDistance
                }
            }
        }
        return bestMatch
    }

    private fun getLevenSteinDistance(s1: String, s2: String): Int{

        var s1Len = s1.length
        var s2Len = s2.length

        var distance = Array(s1Len){it}
        var newDistance = Array(s1Len){0}

        for(i in 1..s2Len-1){
            newDistance[0] = i

            for(j in 1..s1Len-1){
                val match = if(s1[j-1] == s2[i-1]) 0 else 1
                val rCost = distance[j-1] + match
                val iCost = distance[j] + 1
                val dCost = newDistance[j-1] + 1

                newDistance[j] = Math.min(Math.min(dCost, iCost), rCost)
            }
            val temp = distance
            distance = newDistance
            newDistance = temp
        }
        if(distance[s1Len-1] < 4){
            println("Comparing: " + s1 + " And: " + s2 + " returning a distance of: " + distance[s1Len-1])
        }
        return distance[s1Len - 1]
    }

}