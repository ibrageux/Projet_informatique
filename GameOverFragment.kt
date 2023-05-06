package com.example.plantsvszombies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.jeuprojet.R
import com.example.jeuprojet.VuePersonnage


class GameOverFragment( var Gview : VuePersonnage) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.gameover_fragment_layout,container,false)

        val winImage = view.findViewById<ImageView>(R.id.winimage)
        val loseImage = view.findViewById<ImageView>(R.id.loseImage)

        //val iv = view.findViewById<ImageView>(R.id.resImages)
        //iv.setImageResource(resImage)

            if (Gview.win) {

                winImage.setVisibility(View.VISIBLE)
                loseImage.setVisibility(View.GONE)
            }
            if (Gview.lose){
                winImage.setVisibility(View.GONE)
                loseImage.setVisibility(View.VISIBLE)
            }


        val buttonReset = view.findViewById<Button>(R.id.buttonreset)
        buttonReset.setOnClickListener{
            Gview.newGame()
            activity?.runOnUiThread(Runnable {
                    val ft = requireActivity().supportFragmentManager.beginTransaction()
                    ft.remove(this)
                    ft.commit()
                }
            )
        }
        return view
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}
