import processing.core.PApplet
import processing.core.PConstants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MySketch: PApplet(){

    var blendmodes = intArrayOf(BLEND, ADD, SUBTRACT, DARKEST, LIGHTEST, DIFFERENCE, EXCLUSION, MULTIPLY, SCREEN)
    private var current_blendmode = BLEND

    override fun setup() {
        colorMode(PConstants.HSB, 360f, 100f, 100f, 100f)
        background(color(250, 100, 20))
//        noStroke()
    }

    fun ease(x: Float, min: Float, max: Float, amplitude: Float): Float {
        var slide = map(x, min, max, 0f, 2f)
        if (slide <= 1) {
            return amplitude * easeInOutCubic(slide)
        } else {
            slide -= 1f
            return amplitude * (1 - easeInOutCubic(slide))
        }
    }

    override fun draw() {
        blendMode(PConstants.BLEND)
        background(color(250, 100, 20))
        blendMode(current_blendmode)

//        fill(color(150,100,100))
        loadPixels()
        for (i in 0 until width * height) {
            val h = ease(i.toFloat() % width, 0f, (width).toFloat(), 360f)
            pixels[i] = color(h.toInt(), 40, 80)
        }
        updatePixels()

        noLoop()
    }

    override fun mouseClicked() {
        super.mouseClicked()
        loop()
    }

    override fun keyPressed() {
        if (key == 's') {
            kotlin.io.println("saving screenshot")
            save("screenshots/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss")) + ".png")
        } else if (key in '0'..'9') {
            current_blendmode = blendmodes[Character.getNumericValue(key) % blendmodes.size]
        }
    }

    fun easeInOutCubic(x: Float): Float {
        return if (x < 0.5f) {
            4 * x * x * x
        } else {
            1 - pow(-2 * x + 2, 3f) / 2
        }
    }

    companion object Factory {
        fun run(sizeX: Int, sizeY: Int) {
            val mySketch = MySketch()
            mySketch.setSize(sizeY, sizeX)
            mySketch.runSketch()
        }
    }
}




fun main(args:Array<String>){
    MySketch.run(80, 800)
}

