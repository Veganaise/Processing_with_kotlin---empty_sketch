import processing.core.PApplet
import processing.core.PConstants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.cos
import kotlin.math.sin

class MySketch: PApplet(){

    companion object Factory {
        fun run() {
            val mySketch = MySketch()
            mySketch.setSize(1000, 1000)

            mySketch.runSketch()
        }
    }

    var offset = 0f

    override fun setup() {
        colorMode(PConstants.HSB, 360f, 100f, 100f, 100f)
        background(color(250, 100, 20))
//        noStroke()
    }

    fun ease(x: Float, min: Float, max: Float, amplitude: Float): Float {
        val x_mod = x
        var slide = map(x_mod, min, max, 0f, 2f)
        if (slide <= 1) {
            return amplitude * easeInOutCubic(slide)
        } else {
            slide -= 1f
            return amplitude * (1 - easeInOutCubic(slide))
        }
//        return amplitude * sin(slide* PConstants.TWO_PI)
    }

    override fun draw(){
        blendMode(PConstants.BLEND)
        background(color(250, 100, 20))
        blendMode(PConstants.ADD)

//        fill(color(150,100,100))

        for (x in 300 until 701 step 1) {
            for (id_line in 0 until 3) {
                noFill()
                stroke(color(map(id_line.toFloat(), 0f, 3f, 0f, 360f), 50f, 90f), 33f)
                strokeWeight(1.3f)
                beginShape()
                for (y in 200 until 801 step 3) {
                    val easeVal = ease(y.toFloat(), 200f, 800f, 40f)
                    val n = easeVal * (0.5f - noise((y / 10).toFloat(), offset, id_line.toFloat()))
                    val mod2 = if (id_line % 2 < 0.1) 1 else -1
                    val s =
                        easeVal * (sin((y * 0.01f * PConstants.TWO_PI)) * mouseX.toFloat() * 0.001f + cos(y * 0.0001f * PConstants.TWO_PI) * mouseY.toFloat() * 0.01f)
                    val easeX = (x + n + s) * mod2
                    curveVertex(easeX, y.toFloat())
                }
                endShape()
                offset += 0.03f
            }
        }
//        println("here")
        noLoop()
    }

    override fun mouseClicked() {
        super.mouseClicked()
        loop()
    }

    override fun keyPressed() {
        if (key == 's') {
            kotlin.io.println("saving screenshot")
            save("screenshots/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd - hhmmss")) + ".png")
        }
    }




    fun easeInOutCubic(x: Float): Float {
        return if (x < 0.5f) {
            4 * x * x * x
        } else {
            1 - pow(-2 * x + 2, 3f) / 2
        }

    }
}




fun main(args:Array<String>){
    MySketch.run()
}

