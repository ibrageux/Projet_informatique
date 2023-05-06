package com.example.jeuprojet

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Resources
import android.graphics.Color.WHITE
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.plantsvszombies.GameOverFragment
import kotlin.random.Random


class VuePersonnage @JvmOverloads constructor(context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attributes, defStyleAttr), SurfaceHolder.Callback, Runnable {
    private lateinit var thread: Thread
    var backgroundPaint = Paint()
    private var drawing = true
    private val joystick = Joystick2(250f, 1500f)
    private val personnage = Personnage(350F, 1000F, joystick, context)
    private val zombie = Zombie(100f, 100f, personnage, context, this)
    var zombie2 = ArrayList<Zombie>()
    val bullet = Bullet(this, personnage, joystick)
    var totalElapsedTime = 0.0
    var random = Random
    var screenWidth = Resources.getSystem().displayMetrics.widthPixels
    var screenHeight = Resources.getSystem().displayMetrics.heightPixels
    var vie_personnage = Vie(0f, 0f, personnage.contour_personnage.width(), 1f)
    var boss = Boss(context)
    var boule_de_feu = FireBall(this, boss, personnage)
    var vie_boss = Vie(0f, 0f, screenWidth.toFloat(), 1f)
    private var derniere_apparition = 0.0
    private val interval_appartion = 6.0
    var liste_angle = mutableListOf<Double>(90.0)
    var bulletLaunched = false
    var zombie_mort = 0
    var gameOver = false
    private val activity = context as FragmentActivity
    var win = false
    var lose = false

    init {
        holder.addCallback(this)
        backgroundPaint.color = WHITE

    }

    fun updatePositions(elapsedTimeMS: Double) {
        val interval = elapsedTimeMS / 1000.0
        bullet.update(interval, liste_angle.get(0))
        boule_de_feu.uptade(interval)
        vie_personnage.ratio = personnage.life / 300f
        vie_boss.ratio = boss.life / 1000f
        for (zombie in zombie2) {
            if (zombie.zombie_alive) {
                zombie.zombieavance()
            }
        }
        for (i in 0 until zombie2.size) {
            for (j in i + 1 until zombie2.size) {
                if (zombie2[i].isColliding(zombie2[j])) {
                    zombie2[i].contour_Zombie.left -= 5 // ajuster la position horizontale
                    zombie2[i].contour_Zombie.right -= 5 // ajuster la position verticale
                    zombie2[j].contour_Zombie.left += 5 // ajuster la position horizontale
                    zombie2[j].contour_Zombie.right += 5
                }
            }
        }
        if (bullet.bulletOnScreen) {
            for (zombie in zombie2) {
                if (zombie.zombie_alive) {
                    if (RectF.intersects(bullet.contour_bullet, zombie.contour_Zombie)) {
                        zombie.zombie_alive = false
                        bullet.resetCanonBall()
                        zombie_mort++
                    }
                }
            }
        }
        derniere_apparition += interval
        if (derniere_apparition >= interval_appartion) {
            // On décide aléatoirement sur quel bord de l'écran faire apparaître le zombie
            val bord = random.nextInt(4)
            val x: Float
            val y: Float
            when (bord) {
                0 -> {
                    // haut de l'écran
                    x = random.nextInt(screenWidth).toFloat()
                    y = 0f
                }
                1 -> {
                    // bas de l'écran
                    x = random.nextInt(screenWidth).toFloat()
                    y = screenHeight.toFloat()
                }
                2 -> {
                    // côté gauche de l'écran
                    x = 0f
                    y = random.nextInt(screenHeight).toFloat()
                }
                else -> {
                    // côté droit de l'écran
                    x = screenWidth.toFloat()
                    y = random.nextInt(screenHeight).toFloat()
                }
            }
            zombie2.add(Zombie(x, y, personnage, context, this))
            derniere_apparition -= interval_appartion
        }
        vie_personnage.x = personnage.x - (personnage.contour_personnage.width() / 2f)
        vie_personnage.y = personnage.y - (personnage.contour_personnage.height() / 2f)
        for (zombie in zombie2) {
            if (zombie.zombie_alive) {
                if (RectF.intersects(personnage.contour_personnage, zombie.contour_Zombie)) {
                    if (personnage.life > 0) {
                        personnage.life--
                    } else {
                        personnage.isAlive = false
                    }
                }
            }
        }
        if (zombie_mort == 2) {
            boss.Boss_alive = true
        }
        if (boss.Boss_alive) {
            if (boule_de_feu.FireOnScreen) {
                if (RectF.intersects(boule_de_feu.contour_fireball, personnage.contour_personnage)) {
                    if (personnage.life > 0) {
                        personnage.life -= 70
                    } else {
                        personnage.isAlive = false
                    }
                    boule_de_feu.Fire.x = (boss.contour_boss.right / 2f) + 100f
                    boule_de_feu.Fire.y = boss.contour_boss.bottom
                }
            }
        }
    }

    override fun run() {
        var previousFrameTime = System.currentTimeMillis()
        while (drawing) {
            var angle_canon = liste_angle.get(0)
            draw()
            val currentTime = System.currentTimeMillis()
            var elapsedTimeMS: Double = (currentTime - previousFrameTime).toDouble()
            totalElapsedTime += currentTime / 1000.0
            updatePositions(elapsedTimeMS)
            boss.gethit(bullet)
            previousFrameTime = currentTime
            Thread.sleep(30)
            if (boss.life <= 0) {
                gameOver_win()
            }
            if (personnage.life <= 0 ){
                gameOver_lose()
            }

        }
    }


    private fun draw() {
        var canvas = holder.lockCanvas()
        if (canvas != null) {
            //canvas.drawColor(WHITE)
            //canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), backgroundPaint)
            canvas.drawColor(0, PorterDuff.Mode.CLEAR)
            joystick.drawJoystick(canvas)
            if (personnage.isAlive) {
                personnage.draw_personnage(canvas)
                vie_personnage.draw(canvas)
            }
            //joystick.drawJoystick(canvas)
            personnage.personnage_avance()
            if (boss.Boss_alive) {
                boss.draw(canvas)
                vie_boss.draw(canvas)
                if (boule_de_feu.FireOnScreen) {
                    boule_de_feu.draw(canvas)
                }
            }
            for (j in zombie2) {
                if (j.zombie_alive) {
                    j.drawZombie(canvas)
                }
            }
            if (bullet.bulletOnScreen) {
                bullet.draw(canvas)
            }
            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //bullet.launch()
        var touchX = event.x
        var touchY = event.y
        bullet.angle_stock(liste_angle.get(0))
        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                if (touchX >= screenWidth / 2)
                    if (!bullet.bulletOnScreen) {
                        bullet.launch()
                    }
            }

            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                if (joystick.calculedistance(touchX, touchY) <= 100F) {
                    joystick.x = touchX
                    joystick.y = touchY
                }
                if (joystick.calculedistance(touchX, touchY) < 250F) {
                    // calculer le point sur le cercle de rayon 100 le plus proche du point touché
                    val angle = Math.atan2(
                        touchY.toDouble() - joystick.centerY,
                        touchX.toDouble() - joystick.centerX
                    )
                    joystick.x = (joystick.centerX + 100 * Math.cos(angle)).toFloat()
                    joystick.y = (joystick.centerY + 100 * Math.sin(angle)).toFloat()
                }
            }

            MotionEvent.ACTION_UP -> {
                if (-joystick.calculateAngle().toDouble() != 0.0) {
                    liste_angle.set(0, -joystick.calculateAngle().toDouble())
                }
                joystick.x = joystick.centerX
                joystick.y = joystick.centerY
            }
        }
        return true
    }

    fun pause() {
        drawing = false
        thread.join()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        /*drawing = true
        thread = Thread(this)
        thread.start()*/
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        /*drawing = false
        thread.join()*/
    }

    fun newGame() {
        this.visibility = View.VISIBLE
        personnage.life = 300
        zombie_mort = 0
        boss.Boss_alive = false
        drawing = true
        if (gameOver) {
            gameOver = false
            thread = Thread(this)
            thread.start()
        }
    }
    private fun showGameOver() {
        activity.runOnUiThread(Runnable {
            val gameResult = GameOverFragment(this)
            val ft = activity.supportFragmentManager.beginTransaction()
            ft.setReorderingAllowed(true)
            ft.add(R.id.fragment_container, gameResult, "gameoverfragment")
            this.visibility = View.INVISIBLE
            ft.commit()
        })
    }
    private fun gameOver_win() {
        drawing = false
        gameOver = true
        win = true
        lose = false
        showGameOver()
    }

    fun gameOver_lose() {
        drawing = false
        gameOver = true
        lose = true
        win = false
        showGameOver()

    }
}
