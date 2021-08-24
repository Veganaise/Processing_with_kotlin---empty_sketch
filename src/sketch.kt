import processing.core.PApplet
import processing.core.PConstants

class MySketch : PApplet() {
    private var circleX: Float = 200f
    private var circleY: Float = 200f

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


    companion object Factory {
        fun run() {
            val mySketch = MySketch()
            mySketch.setSize(600, 600)

            mySketch.runSketch()
        }
    }
}

fun main(args:Array<String>){
    MySketch.run()
}

