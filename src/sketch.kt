import processing.core.PApplet
import processing.core.PConstants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MySketch: PApplet(){

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

//        fill(color(150,100,100))

        for (x in 100 until 501 step 1) {
            for (id_line in 0 until 1) {
                noFill()
                stroke(color(map(id_line.toFloat(), -2f, 2f, 0f, 360f), 50f, 90f), 100f)
                strokeWeight(1.3f)
                beginShape()
                for (y in 100 until 500 step 5) {
                    val easeVal = ease(y.toFloat(), 100f, 500f, 30f)
                    val n = easeVal * (0.5f - noise((y / 10).toFloat(), offset)) * sin(y / 10f)
                    val s = easeVal * (sin(y / 100f) * 2 + cos(y / 50f) * 2 + sin(x / 10f) * 0.5f)
                    val easeX = x + n + s
                    curveVertex(easeX, y.toFloat())
                }
                endShape()
                offset += 0.03f
            }
        }
        println("here")
        noLoop()
    }

    override fun mouseClicked() {
        super.mouseClicked()
        loop()
    }

    override fun keyPressed() {
        if (key == 's') {
            save("screenshots/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd - hhmmss")) + ".png")
        }
    }


    companion object Factory{
        fun run() {
            val mySketch = MySketch()
            mySketch.setSize(600,600)

            mySketch.runSketch()
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

