package com.example.jeuprojet

import android.content.Context
import android.graphics.*
import androidx.core.graphics.toRect


class Boss(val context : Context) {

    var life = 1000
    var bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.boss)
    var contour_boss = RectF(100f,0f,100f + bitmap.width*2.5f, bitmap.height*2.5f)
    var Boss_alive = false


    fun gethit(bullet: Bullet){
        if(life>0){
            if (bullet.bulletOnScreen){
                if (RectF.intersects(bullet.contour_bullet, contour_boss)){
                    life = life -50
                    bullet.resetCanonBall()
                }
            }
        }
        else {
            Boss_alive = false
        }

    }
    fun draw(canvas: Canvas){
        canvas.save()
        canvas.rotate(180f, contour_boss.centerX(), contour_boss.centerY())
        canvas.drawBitmap(bitmap, null, contour_boss.toRect(),null)
        canvas.restore()
    }


}