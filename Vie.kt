package com.example.jeuprojet

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Vie (var x :Float,var y : Float,var longueur : Float,var ratio : Float){

    val life = Paint()
    val death = Paint()
    init {
        life.color = Color.GREEN
        death.color = Color.RED
    }



    fun draw(canvas: Canvas){
        canvas.drawRect(x,y,x + longueur*ratio,y +  20f,life)
        canvas.drawRect(x + longueur*ratio,y ,x + longueur, y +20f,death)
    }

}