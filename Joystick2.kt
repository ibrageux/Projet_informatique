package com.example.jeuprojet
import android.content.ContentValues.TAG
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import kotlin.math.atan2
import kotlin.math.sqrt
import kotlin.math.pow


class Joystick2 (var x: Float,var y: Float){
    var aucentre: Boolean = true
    var baseRadius: Float = 200f
    var hatRadius: Float = 100f
    var centerX: Float= 250f
    var centerY: Float= 1500f
    //var derniereDirection: Boolean? = null  // variable pour stocker la dernière direction du joystic

fun calculedistance(_x: Float,_y: Float): Float {
    var deplacement = sqrt((_x - centerX).toDouble().pow(2.0) + (_y - centerY).toDouble().pow(2.0)).toFloat()
    //return if (displacement > baseRadius) baseRadius else displacement
    return deplacement
}

fun calculateAngle(): Float{
    var angle = 0f
    if (!(centerX == x && centerY == y)) {
        aucentre=false
        angle = Math.toDegrees(atan2((y- centerY).toDouble(), (x-centerX ).toDouble())).toFloat()
        /*if ( angle < 0) {
            angle += 360
        }*/
    }
    else aucentre= true
    //print(angle)
    return angle
}

fun drawJoystick(canvas: Canvas) {
    //Log.d(TAG, calculateAngle().toString())
    val paint = Paint()
    paint.color = Color.BLACK
    canvas.drawCircle(centerX, centerY, baseRadius, paint)
    paint.color = Color.GREEN
    canvas.drawCircle(x , y, hatRadius, paint)
}
}
//var derniereDirection: Boolean? = null  // variable pour stocker la dernière direction du joystic
/*fun chang_direction(){
    val angle = calculateAngle()
    if (!aucentre  )  {
        derniereDirection = angle <90 && angle > -90// si l'angle est supérieur à 180 degrés, le joystick est à gauche
    }
}*/














