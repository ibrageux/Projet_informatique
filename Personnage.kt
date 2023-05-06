package com.example.jeuprojet

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import androidx.core.graphics.toRect

class Personnage(var x: Float, var y: Float , var joystick: Joystick2, var context: Context) {
    var screenWidth = Resources.getSystem().displayMetrics.widthPixels
    var screenHeight = Resources.getSystem().displayMetrics.heightPixels
    var bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.personnage3)
    val vitesse = 20
    var life = 300
    val demi_largeur = bitmap.width.toFloat()/2
    val demi_hauteur = bitmap.height.toFloat()/2
    var contour_personnage = RectF(x- demi_largeur, y - demi_hauteur, x + demi_largeur, y + demi_hauteur)
    var angl = 0.0
    var isAlive = true

    fun draw_personnage(canvas: Canvas) {
        canvas.save()
        canvas.rotate(angl.toFloat(), contour_personnage.centerX(), contour_personnage.centerY())
        canvas.drawBitmap(bitmap, null, contour_personnage.toRect(), null)
        if (condition(joystick.calculateAngle().toDouble()-270) != 0.0 ) {
        angl = condition(joystick.calculateAngle().toDouble()-270)
        }
        canvas.restore()
        }

    fun condition(valeur_angle: Double): Double {
        var resultat_angle = 0.0
        if (valeur_angle!=-270.0) {
            resultat_angle = valeur_angle
        }
        return resultat_angle
    }

    fun personnage_avance() {
        var angle = joystick.calculateAngle().toDouble()
        if (!joystick.aucentre) {
            val dx = vitesse * Math.cos(Math.toRadians(angle)).toFloat()
            val dy = vitesse * Math.sin(Math.toRadians(angle)).toFloat()
            val next_rect = RectF(contour_personnage)
            next_rect.offset(dx, dy)
            if (!(next_rect.left < 0 || next_rect.right > screenWidth || next_rect.top < 0 || next_rect.bottom > screenHeight)) {
                contour_personnage.offset(dx, dy)
                x += dx
                y += dy
            }
        }
    }

}
/*fun RotImage2() {
        joystick.chang_direction()
        if (joystick.derniereDirection == true) {
            bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.soldat1)
        }
        else {
            bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.soldat2)
        }
    }*/