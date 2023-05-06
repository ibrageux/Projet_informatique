package com.example.jeuprojet
import android.graphics.*
import java.lang.Math.cos
import java.lang.Math.sin

class Bullet (var view: VuePersonnage, var personnage: Personnage, var joystick2: Joystick2) {
    var bullet = PointF(personnage.x+130,personnage.y)
    var bulletVitesseX = -1000f
    var bulletVitesseY = -1000f
    var bulletOnScreen = false
    var bulletRadius = 20f
    var bulletPaint = Paint()
    var contour_bullet = RectF()
    val pi = Math.PI
    var bulletAngle = 0.0
    init {
        bulletPaint.color = Color.YELLOW
    }

    fun angle_stock ( angle : Double){
        bulletAngle = angle
    }

    fun position_sur_canon (){
        bullet.x = personnage.x - personnage.demi_largeur * -cos(Math.toRadians(bulletAngle)).toFloat()
        bullet.y = personnage.y - personnage.demi_hauteur * sin(Math.toRadians(bulletAngle) ).toFloat()
    }

    fun launch() {
        if ( !bulletOnScreen ) {
            position_sur_canon()
        }
        bulletOnScreen = true
    }


    fun update(interval: Double , angle : Double) {
        if (bulletOnScreen) {
            bullet.x += (interval * bulletVitesseX).toFloat() * -cos(Math.toRadians(bulletAngle)).toFloat()
            bullet.y += (interval * bulletVitesseY).toFloat() * sin(Math.toRadians(bulletAngle) ).toFloat()
            contour_bullet = RectF(bullet.x , bullet.y , bullet.x + bulletRadius, bullet.y + bulletRadius)
            if (bullet.x + bulletRadius > view.screenWidth || bullet.x - bulletRadius < 0) {
                bulletOnScreen = false
                position_sur_canon()
            } else if (bullet.y + bulletRadius > view.screenHeight || bullet.y - bulletRadius < 0) {
                bulletOnScreen = false
                position_sur_canon()
            }
        }
    }

    fun resetCanonBall() {
        bulletOnScreen = false
    }

    fun draw(canvas: Canvas) {
        canvas.save()
        canvas.drawCircle(bullet.x , bullet.y , bulletRadius, bulletPaint)
        canvas.restore()
    }

}