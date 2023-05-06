package com.example.jeuprojet
import com.example.jeuprojet.VuePersonnage
import android.content.Context
import android.graphics.*
import android.graphics.Paint
import android.os.Looper
import androidx.core.graphics.toRect


class Zombie( x : Float, y: Float , var personnage: Personnage, var context: Context, vieuw: com.example.jeuprojet.VuePersonnage) {
    var zombie_alive = true
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.zombie1)
    var vitesse_X = 5
    var vitesse_Y = 5
    val contour_Zombie = RectF(x- (bitmap.width.toFloat())/2, y - (bitmap.height.toFloat())/2, x + (bitmap.width.toFloat())/2, y + (bitmap.height.toFloat())/2)
    val couleurZombie = Paint() // Couleur légèrement plus claire que Color.WHITE

    fun isColliding(other: Zombie): Boolean {
        return RectF.intersects(contour_Zombie, other.contour_Zombie)
    }
    fun angle(): Double {
        var deltaX = contour_Zombie.centerX() - personnage.x
        var deltaY = contour_Zombie.centerY() - personnage.y
        return (Math.atan2(deltaY.toDouble(), deltaX.toDouble()))
    }

    fun zombieavance() {
        val cos = Math.cos(angle()).toFloat()
        val sin = Math.sin(angle()).toFloat()
        val next_rect = RectF(contour_Zombie)
        next_rect.offset(-vitesse_X * cos, -vitesse_Y * sin)
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            if (!personnage.contour_personnage.intersects(contour_Zombie.left, contour_Zombie.top, contour_Zombie.right, contour_Zombie.bottom)) {
                contour_Zombie.offset(-vitesse_X * cos, -vitesse_Y * sin)
            }
        }, 100)
    }

    fun drawZombie(canvas: Canvas) {
        canvas.save()
        canvas.rotate(Math.toDegrees(angle()).toFloat()-90, contour_Zombie.centerX(), contour_Zombie.centerY())
        couleurZombie.color =  Color.rgb(240, 0, 0)
        canvas.drawBitmap(bitmap, null, contour_Zombie.toRect(), couleurZombie)
        canvas.restore()
    }
}





/*fun RotImage() {
    if (angle() > -(Math.PI)/2 && angle() < (Math.PI)/2){
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.zombieeeeeeeeeee22)
    }
    else{
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.zombieeeeeeeeeee1)
    }
}*/