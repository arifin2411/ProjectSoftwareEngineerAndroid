package com.tokopedia.climbingstairs

object Solution {
    fun climbStairs(n: Int): Long {
        // TODO, return in how many distinct ways can you climb to the top. Each time you can either climb 1 or 2 steps.
        // 1 <= n < 90
        val input = n / 2
        var result = 0

        for (i in 0 until input + 1) {
            result += factorial(n - i) / (factorial(n - (2 * i)) * factorial(i))
        }

        println("result $result")
        return result.toLong()
    }

    private fun factorial(number: Int): Int {
        if (number == 0) {
            return 1
        }

        var facto: Int = number
        for (i in number downTo  2) {
            facto *= (i - 1)
        }
        return facto
    }
}

