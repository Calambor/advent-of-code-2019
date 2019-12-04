package day4

fun main(args: Array<String>) {

    val result = program("input4_1")
    println(result)

}

fun program(fileName: String): Int {
    val min = 128392
    val max = 643281

    var validNum = 0
    (min..max).forEach { pw ->
        if (isValidPass(pw)) {
            validNum++
        }
    }

    return validNum
}

fun isValidPass(pass: Int): Boolean {
    var remaining = pass
    val nums = Array(6) { index ->
        val curr = remaining % 10
        remaining = remaining / 10
        curr
    }
    nums.reverse()

    val valid = nums.toList().zipWithNext {a, b ->
        a <= b
    }.all { it }
    if (!valid) {
        return false
    }

    val groups = (0..9).map { value ->
        nums.count { it == value } == 2
    }
    return groups.count { it == true } >= 1
}