package cryptography

import java.awt.Color
import java.lang.StringBuilder
import kotlin.experimental.xor

class CommandHandler {

    companion object {
        fun handle(command: Command) {
            when (command.text) {
                "hide" -> hide()
                "show" -> show()
                "exit" -> exit()
                else -> println("Wrong task: ${command.text}")
            }
        }
    }
}

fun hide() {
    println("Input image file:")
    val inFileName = readln()
    println("Output image file:")
    val outFileName = readln()
    println("Message to hide:")
    val message = readln()
    println("Password:")
    val password = readln()

    val image = ImageHandler.readImage(inFileName)
    if (image != null) {
        val messageByteArray = message.encodeToByteArray().toMutableList()
        val passwordByteArray = password.encodeToByteArray().toMutableList()

        var passwordIndex = 0
        for (i in messageByteArray.indices) {
            messageByteArray[i] = messageByteArray[i].xor(passwordByteArray[passwordIndex])
            passwordIndex++
            if (passwordIndex == passwordByteArray.size) {
                passwordIndex = 0
            }
        }

        val messageBitArray = messageByteArray.map { it.to8thBinary() }.toMutableList()

        repeat(2) { messageBitArray.add("00000000") }
        messageBitArray.add("00000011")
        val messageBitString = messageBitArray.joinToString("")


        if (image.width * image.height < messageBitString.length) {
            println("The input image is not large enough to hold this message.")
            return
        }

        var count = 0

        breaking@for (y in 0 until image.height)  {
            for (x in 0 until image.width) {
                if (count == messageBitString.length) {
                    break@breaking
                }

                var color = Color(image.getRGB(x, y))
                color = Color(color.red, color.green, color.blue.and(254).or(messageBitString[count].digitToInt()) % 256)
                image.setRGB(x, y, color.rgb)

                count++
            }
        }

        ImageHandler.saveImage(outFileName, image)
        println("Message saved in  $outFileName image.")

    } else {
        println("Can't read input file!")
    }
}

fun show() {
    println("Input image file:")
    val inFileName = readln()
    println("Password:")
    val password = readln()
    val image = ImageHandler.readImage(inFileName)
    val messageBitArray = mutableListOf<String>()
    val passwordByteArray = password.encodeToByteArray().toMutableList()

    val messageString = StringBuilder()
    if (image != null) {
        breaking@for (y in 0 until image.height)  {
            for (x in 0 until image.width) {
                if (messageString.endsWith("000000000000000000000011")) {
                    break@breaking
                }
                val color = Color(image.getRGB(x, y))
                messageString.append(color.blue.toString(2).last())
            }
        }
    }

    var byte = ""
    for (i in messageString) {
        byte += i
        if (byte.length == 8) {
            messageBitArray.add(byte)
            byte = ""
        }
    }

    repeat(3) { messageBitArray.removeAt(messageBitArray.lastIndex) }
    val messageByteArray = messageBitArray.map { it.toByte(2) }.toByteArray()

    var passwordIndex = 0
    for (i in messageByteArray.indices) {
        messageByteArray[i] = messageByteArray[i].xor(passwordByteArray[passwordIndex])
        passwordIndex++
        if (passwordIndex == passwordByteArray.size) {
            passwordIndex = 0
        }
    }

    val message = messageByteArray.toString(Charsets.UTF_8)

    println("Message: $message")
}

fun exit() {
    println("Bye!")
    isOn = false
}

fun Byte.to8thBinary(): String {
    return String.format("%" + 8 + "s", this.toString(2)).replace(" ".toRegex(), "0")
}