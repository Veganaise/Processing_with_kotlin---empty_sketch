import processing.core.PApplet
import processing.core.PConstants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MySketch : PApplet() {
    private var circleX: Float = 200f
    private var circleY: Float = 200f

    var blendmodes = intArrayOf(BLEND, ADD, SUBTRACT, DARKEST, LIGHTEST, DIFFERENCE, EXCLUSION, MULTIPLY, SCREEN)
    private var current_blendmode = BLEND

    override fun setup() {
        colorMode(PConstants.HSB, 360f, 100f, 100f, 1.0f)
        stroke(color(150, 255, 200))
        strokeWeight(100f)
    }

    override fun draw() {
        background(color(250, 100, 20))
        fill(color(150, 255, 200))
        val newCircleX = lerp(circleX, mouseX.toFloat(), 0.08f)
        val newCircleY = lerp(circleY, mouseY.toFloat(), 0.1f)
        line(circleX, circleY, newCircleX, newCircleY)
        circleX = newCircleX
        circleY = newCircleY
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
            mySketch.setSize(600, 600)

            mySketch.setSize(sizeY, sizeX)
            mySketch.runSketch()
        }
    }
}


fun main(args:Array<String>){
    MySketch.run(80, 800)
}

