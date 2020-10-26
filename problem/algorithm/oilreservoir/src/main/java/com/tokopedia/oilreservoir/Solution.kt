package com.tokopedia.oilreservoir

/**
 * Created by fwidjaja on 2019-09-24.
 */
object Solution {
    lateinit var height:IntArray

    var oilCube: Int = 0
    var leftPosition: Int = 0
    var leftWall: Int = 0
    var bool: Int = 0
    var rightPosition: Int = 0

    fun collectOil(height: IntArray): Int {
        // TODO, return the amount of oil blocks that could be collected
        // below is stub
        this.height = height

        while (this.height[leftPosition] == 0 ) {
            leftPosition += 1
        }

        leftWall = this.height[leftPosition]
        while (leftPosition != (this.height.size - 1)) {
            bool = 0
            for (i in (leftPosition + 1) until this.height.size) {
                 if (this.height[i] >= leftWall) {
                     getOitCube(leftWall , i)
                     break
                 }
            }

            var x: Int = 1
            while (bool == 0){
                for (i in (leftPosition + 1) until this.height.size) {
                    if (this.height[i] == (this.height[leftPosition] - x)) {
                        getOitCube((this.height[leftPosition] - x), i)
                        break
                    }
                }
                x += 1
            }
        }
        println("result oilCube: $oilCube")
        return oilCube
    }

    private fun getOitCube(oilHeight: Int, i: Int ) {
        rightPosition = i

        for (j in (leftPosition + 1) until rightPosition) {
            oilCube += (oilHeight - this.height[j])
        }
        leftPosition = rightPosition
        leftWall = this.height[leftPosition]
        bool = 1
    }
}
