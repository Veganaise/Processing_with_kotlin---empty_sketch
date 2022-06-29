import processing.core.PApplet
import processing.core.PImage
import java.io.File
import java.io.IOException
import javax.swing.JFileChooser

import javax.swing.filechooser.FileNameExtensionFilter


class FilePicker() : PApplet() {
    private var file: File? = null

    fun pick_image(): String {
        val chooser = JFileChooser("D:\\Images\\moodboard")
        val filter = FileNameExtensionFilter(
            "Images", "jpg", "gif", "png"
        )
        chooser.fileFilter = filter
        val returnVal = chooser.showOpenDialog(null)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            val absolutFilePath = chooser.selectedFile.absoluteFile.absolutePath
            kotlin.io.println(
                "You chose to open this file: $absolutFilePath"
            )
            return absolutFilePath
        } else {
            throw IOException("Window was closed or the user hit cancel.")
        }
    }

    fun load_image(filepath: String): PImage {
        return loadImage(filepath)
    }

    companion object Factory {
        fun run(): PImage {
            val mySketch = FilePicker()
            mySketch.runSketch()
            val image = mySketch.load_image(mySketch.pick_image())
            mySketch.clear()
            return image
        }
    }
}