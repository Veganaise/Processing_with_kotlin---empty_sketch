import processing.core.PApplet
import processing.core.PConstants

class MySketch: PApplet(){

    override fun setup() {
        colorMode(PConstants.HSB, 360f, 100f, 100f, 1.0f)
        noStroke()
    }

    override fun draw(){
        background(color(250,100,20))
        fill(color(150,255,200))
        ellipse(mouseX.toFloat(),mouseY.toFloat(),100f,100f)

    }


    companion object Factory{
        fun run() {
            val mySketch = MySketch()
            mySketch.setSize(600,600)

            mySketch.runSketch()
        }
    }
}

fun main(args:Array<String>){
    MySketch.run()
}

