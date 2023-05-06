package com.example.jeuprojet

import android.graphics.*

class FireBall(var view: VuePersonnage, var boss: Boss, var personnage: Personnage) {

    var Fire = PointF((boss.contour_boss.right/2f) +100f,boss.contour_boss.bottom)
    var FireRadius = 30f
    var FirePaint = Paint()
    var FireSpeed = 400
    var contour_fireball = RectF()
    var FireOnScreen = true
    val damage = 100

    init {
        FirePaint.color = Color.RED
    }

    fun angle(): Double {
        var deltaX = ((boss.contour_boss.right/2f) +100f) - personnage.x
        var deltaY = boss.contour_boss.bottom - personnage.y
        return (Math.atan2(deltaY.toDouble(), deltaX.toDouble()))
    }

    fun uptade (interval : Double) {
        if (FireOnScreen) {
            Fire.x -= (interval * FireSpeed * Math.cos(angle())).toFloat()
            Fire.y -= (interval * FireSpeed * Math.sin(angle())).toFloat()
            contour_fireball = RectF(Fire.x-FireRadius,Fire.y - FireRadius,Fire.x+FireRadius,Fire.y + FireRadius)
            if (Fire.x + FireRadius > view.screenWidth || Fire.x - FireRadius < 0) {
                Fire.x = (boss.contour_boss.right/2f) +100f
                Fire.y = boss.contour_boss.bottom

            } else if (Fire.y + FireRadius > view.screenHeight || Fire.y - FireRadius < 0) {
                Fire.x = (boss.contour_boss.right/2f) +100f
                Fire.y = boss.contour_boss.bottom
            }
        }
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(Fire.x, Fire.y, FireRadius,
            FirePaint)
    }







}