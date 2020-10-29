package com.tokopedia.minimumpathsum

import kotlin.math.min

object Solution {
    fun minimumPathSum(matrix: Array<IntArray>): Int {
        // TODO, find a path from top left to bottom right which minimizes the sum of all numbers along its path, and return the sum
        // below is stub
        val openPaths = matrix.map { it.map { true }.toBooleanArray() }.toTypedArray()
        val result: Array<IntArray> = matrix.map { it.map { Int.MAX_VALUE }.toIntArray() }.toTypedArray()
        result[0][0] = matrix[0][0]

        val column = matrix.size
        val row = matrix.first().size

        fun tryWalk(currentX: Int, currentY: Int, moveX: Int, moveY: Int) {
            val toX = currentX + moveX
            val toY = currentY + moveY
            try {
                if (openPaths[toX][toY] == false) return
                val moveCost = result[currentX][currentY] + matrix[toX][toY]
                result[toX][toY] = min(moveCost, result[toX][toY])
            } catch (e: Exception) {
                return
            }
        }
        for (i in 0 until column) {
            for (j in 0 until row) {
                tryWalk(i, j, 1, 0)
                tryWalk(i, j, 0, 1)
                tryWalk(i, j, 0, -1)
                tryWalk(i, j, -1, 0)
                openPaths[i][j] = false
            }
        }

        return result[column - 1][row - 1]
    }
}
