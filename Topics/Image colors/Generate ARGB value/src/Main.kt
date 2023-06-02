import java.awt.Color
import java.util.Scanner

fun printARGB() {
    val sc = Scanner(System.`in`)
    val a = sc.nextInt()

    val r= sc.nextInt()
    val g = sc.nextInt()
    val b = sc.nextInt()
    if (r !in 0..255 || g !in 0..255 || b !in 0..255 || a !in 0..255) {
        print("Invalid input")
    } else {
        val color = Color(r, g, b, a)
        println(color.rgb.toUInt())

    }


}