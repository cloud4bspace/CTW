package space.cloud4b.ctw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import kotlinx.android.synthetic.main.additem_stepthree_fragment.*
import kotlinx.android.synthetic.main.additem_steptwo_fragment.*
import kotlinx.android.synthetic.main.additem_steptwo_fragment.tvReason

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AdditemStepThreeFragment : Fragment() {

    var beverageList : MutableList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Register Fragment onCreateView")


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.additem_stepthree_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*  for(i in 0 until llReasonsLineOne.childCount) {
      if(llReasonsLineOne.getChildAt(i).id == ibGoodbye.id){

      } else {

          llReasonsLineOne.getChildAt(i).setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
      }
  }*/
        ibWine.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibLimmo.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibBeer.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibCocktails.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibChampagne.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibCroissant.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibTarte.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibCrisps.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibSandwich.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibVeggies.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);


        ibWine.setOnClickListener() {
            if(beverageList.contains("Wein"))  {
                ibWine.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                beverageList.remove("Wein")
            } else {
                ibWine.clearColorFilter()
                beverageList.add("Wein")
            }
            generateTvOutput()
        }
        ibLimmo.setOnClickListener() {
            if(beverageList.contains("Limmo"))  {
                ibLimmo.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                beverageList.remove("Limmo")
            } else {
                ibLimmo.clearColorFilter()
                beverageList.add("Limmo")
            }
            generateTvOutput()
        }
        ibBeer.setOnClickListener() {
            if(beverageList.contains("Bier"))  {
                ibBeer.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                beverageList.remove("Bier")
            } else {
                ibBeer.clearColorFilter()
                beverageList.add("Bier")
            }
            generateTvOutput()
        }
        ibCocktails.setOnClickListener() {
            if(beverageList.contains("Cocktails"))  {
                ibCocktails.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                beverageList.remove("Cocktails")
            } else {
                ibCocktails.clearColorFilter()
                beverageList.add("Cocktails")
            }
            generateTvOutput()
        }

        ibChampagne.setOnClickListener() {
            var button : ImageButton = it as ImageButton // TODO Casting wie dieses braucht es
            if(beverageList.contains(button.contentDescription.toString()))  {
                button.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                beverageList.remove(button.contentDescription.toString())
            } else {
                button.clearColorFilter()
                beverageList.add(button.contentDescription.toString())
            }
            generateTvOutput()
        }
    }
    fun stringContains(searchString : String) : Boolean {
        var string = tvFandB.text
        return string.indexOf(searchString)>=0
    }

    fun generateTvOutput() {
        var outputString : String? = null
        var counter : Int = 1
        for(beverage in beverageList) {
            when (counter) {
                1 -> outputString = beverage
                beverageList.size -> outputString += " & $beverage"
                else -> outputString += ", $beverage"
            }
            counter++
        }
        tvFandB.setText(outputString)
    }


}


