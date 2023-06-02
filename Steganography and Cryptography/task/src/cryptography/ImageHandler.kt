package cryptography

import java.awt.image.BufferedImage
import java.io.File
import java.io.FileNotFoundException
import javax.imageio.IIOException
import javax.imageio.ImageIO

class ImageHandler {

    companion object {
        fun readImage(fileName: String): BufferedImage? {
            val file = File(fileName)
            return try {
                ImageIO.read(file)
            } catch (_:FileNotFoundException) {
                null
            } catch (_:IIOException) {
                null
            }

        }

        fun saveImage(fileName: String, image: BufferedImage) {
            val file = File(fileName)
            ImageIO.write(image, "png", file)
        }
    }
}