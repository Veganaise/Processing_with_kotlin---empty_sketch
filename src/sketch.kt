import processing.core.PApplet
import processing.core.PConstants
import processing.core.PImage
import processing.core.PVector
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MySketch : PApplet() {
    var blendmodes = intArrayOf(BLEND, ADD, SUBTRACT, DARKEST, LIGHTEST, DIFFERENCE, EXCLUSION, MULTIPLY, SCREEN)
    private var currentBlendmode = BLEND
    private var sketchIsLooping = true

    private var dots: ArrayList<PVector> = ArrayList(100)
    private var nb_dots: Int = 100

    private lateinit var base_image: PImage


    override fun setup() {
        colorMode(PConstants.HSB, 360f, 100f, 100f, 1.0f)
        stroke(color(150, 255, 200))
        strokeWeight(1f)
        //base_image = loadImage("D:\\Images\\moodboard\\abstrat\\FFXB6AOWYAInzvx.jpg")


        for (i in 0..width step 1) {
            for (j in 0..height step 1) {
                val newDot = PVector(i.toFloat(), j.toFloat())
                dots.add(newDot)
            }
        }

    }

    override fun draw() {
        background(color(243, 73, 25))
        //background(base_image)
        fill(color(150, 255, 200))
        base_image.loadPixels()
        val scale: Float = map(
            mouseX.toFloat(), 0F, base_image.width.toFloat(), 0F, 100F
        )
        println("scale:$scale")
        for (dot in dots) {
            val sc = scale * noise(dot.x * 0.01F, dot.y * 0.01F, 15F) * 0.5F
            val warpedPosition = getWarpedPosition(dot.x, dot.y, sc)
            //val col = getColorAtPosition(dot.x,dot.y)
            //println(col)
            //stroke(base_image.pixels[(dot.x*base_image.pixelWidth+dot.y).toInt()])
            //stroke(base_image.pixels[(dot.x+dot.y*base_image.pixelHeight).toInt()%base_image.pixels.size])
            stroke(base_image.get(warpedPosition.x.toInt(), warpedPosition.y.toInt()))
            point(dot.x, dot.y)
        }

        noLoop()
    }

    override fun mouseClicked() {
        super.mouseClicked()
        redraw()
    }

    override fun keyPressed() {
        if (key == 's') {
            kotlin.io.println("saving screenshot")
            save("screenshots/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss")) + ".png")
        } else if (key in '0'..'9') {
            kotlin.io.println("Changing blendmode")
            currentBlendmode = blendmodes[Character.getNumericValue(key) % blendmodes.size]
        } else if (key == ' ') {
            kotlin.io.println("Looping once")
            if (sketchIsLooping) {
                noLoop()
            } else {
                loop()
            }
            sketchIsLooping = !sketchIsLooping
        }
    }

    companion object Factory {
        fun run(width_input: Int, height_input: Int) {
            val mySketch = MySketch()
            mySketch.setSize(width_input, height_input)
            mySketch.runSketch()
        }

        fun run(image: PImage) {
            val mySketch = MySketch()
            mySketch.base_image = image
            mySketch.setSize(image.pixelWidth, image.pixelHeight)
            mySketch.runSketch()
        }

        fun run(imageAbsoluteFilePath: String) {
            val mySketch = MySketch()

            mySketch.base_image = mySketch.loadImage(imageAbsoluteFilePath)
            mySketch.setSize(
                mySketch.base_image.pixelWidth, mySketch.base_image.pixelHeight
            )
            mySketch.runSketch()

        }
    }


    fun getWarpedPosition(x: Float, y: Float, scale: Float): PVector {
        var dx = 0F
        var dy = 0F
        var sc = scale
        var offset = random(100F)

        for (i in 0..2) {
            dx += (noise(x * 0.01F + i + offset, y * 0.01F + i, i.toFloat()) - 0.5F) * scale
            dy += (noise(x * 0.01F - i + offset, y * 0.01F - i, (-i).toFloat()) - 0.5F) * scale
            sc *= 0.5F
        }

        return PVector(x + dx, y + dy)
    }

    fun getColorAtPosition(x: Float, y: Float): Int {
        val machin = x % 9//map(x,80F, (height-80).toFloat(), 0F,1F)//noise(x*0.01f,y*0.1f).absoluteValue
        if (machin < 0.5F) {
            print('0')
            return color(333, 85, 97)
        } else {
            print(x)
            return color(194, 68, 94)
        }
        //print(machin," : ")
        //return lerpColor(color(30, 93, 100),color(35, 10, 50),machin)
        //        return lerpColor("FF8811".toInt(16),"392F5A".toInt(16),machin)
    }

    fun getColorAtPosition(x: Float, y: Float, pixels: Array<Int>): Int {
        val machin = x % 9//map(x,80F, (height-80).toFloat(), 0F,1F)//noise(x*0.01f,y*0.1f).absoluteValue
        if (machin < 0.5F) {
            print('0')
            return color(333, 85, 97)
        } else {
            print(x)
            return color(194, 68, 94)
        }
        //print(machin," : ")
        //return lerpColor(color(30, 93, 100),color(35, 10, 50),machin)
        //        return lerpColor("FF8811".toInt(16),"392F5A".toInt(16),machin)
    }
}


fun main(args: Array<String>) {
    //val fileChooser:FilePicker = FilePicker()
    //file_chooser.showDialog(null,"t")
    val image = FilePicker.run()
    MySketch.run(image)
}


