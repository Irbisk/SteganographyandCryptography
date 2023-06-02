import java.util.*

fun printColor(myImage: BufferedImage) {
    val scanner = Scanner(System.`in`)
    val x = scanner.nextInt()
    val y = scanner.nextInt()

    val color = Color(myImage.getRGB(x, y), true)

    val red = color.red
    val green = color.green
    val blue = color.blue
    val a = color.alpha
    println("$red $green $blue $a")

}
