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


class GameOverFragment( var Gview : VuePersonnage, var resImage : Int) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.gameover_fragment_layout,container,false)
        //val tv = view.findViewById<TextView>(R.id.textView)

        val iv = view.findViewById<ImageView>(R.id.imageView)
        iv.setImageResource(resImage)

        val buttonReset = view.findViewById<Button>(R.id.buttonreset)


        buttonReset.setOnClickListener{
            Gview.newGame()

            activity?.runOnUiThread(
                Runnable {
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