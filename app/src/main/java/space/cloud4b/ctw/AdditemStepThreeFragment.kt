package space.cloud4b.ctw

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.additem_stepthree_fragment.*
import kotlinx.android.synthetic.main.additem_steptwo_fragment.*
import kotlinx.android.synthetic.main.additem_steptwo_fragment.tvReason
import space.cloud4b.ctw.model.NewCakeboardEntry

/**
 * Dritter Schritt für die Erfassung eines neuen Eintrags (Termin)
 */
class AdditemStepThreeFragment : Fragment() {
    val args: AdditemStepThreeFragmentArgs by navArgs()
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
        var newEntryArray = args.newEntryArray


        buAddItemGotoStepFour.setOnClickListener() {
            if(validation()) {
                newEntryArray[4] = getBeverageStringList()
                NewCakeboardEntry.beverages = getBeverageStringList()
                val action = AdditemStepThreeFragmentDirections.actionAdditemStepThreeFragmentToAdditemStepFourFragment(newEntryArray)
                findNavController().navigate(action)
            }
        }

        // alle ImageButtons innerhalb des GridLayouts "glBeverages" durchlaufen
        for(i in 0 until glBeverages.childCount) {
            var imageButton : ImageButton = glBeverages.getChildAt(i) as ImageButton

            // alle Buttons abdunkeln
            imageButton.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);

            imageButton.setOnClickListener() {
                // prüfen, ob das Getränk in der Liste bereits vorhanden ist oder nicht
                if(beverageList.contains(imageButton.contentDescription.toString()))  {
                    imageButton.setColorFilter(R.color.black,
                        android.graphics.PorterDuff.Mode.MULTIPLY);
                    beverageList.remove(imageButton.contentDescription.toString())
                } else {
                    imageButton.clearColorFilter()
                    beverageList.add(imageButton.contentDescription.toString())
                }
                // Liste der gewählten Getränke als String aufbereiten und ausgeben
                generateTvOutput()
            }
        }

        // alle ImageButtons (Food) abdunkeln
        for(i in 0 until glFood.childCount) {
            var imageButton : ImageButton = glFood.getChildAt(i) as ImageButton
            imageButton.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);

            imageButton.setOnClickListener() {
                if(beverageList.contains(imageButton.contentDescription.toString()))  {
                    imageButton.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                    beverageList.remove(imageButton.contentDescription.toString())
                } else {
                    imageButton.clearColorFilter()
                    beverageList.add(imageButton.contentDescription.toString())
                }
                generateTvOutput()
            }
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

    fun getBeverageStringList() : String {
        var beverageStringList : String = ""
        var counter : Int = 1
        for(beverage in beverageList) {
            if(counter == 1) {
                beverageStringList += beverage
            } else {
                beverageStringList += "|$beverage"
            }
            counter++
        }
        return beverageStringList
    }

    fun validation() : Boolean {
        // Auswahl Food&Beverages
        if(tvFandB.getText().toString().trim().isEmpty()){
            tvFandB.error = "Bitte mindestens eine Kachel auswählen"
            return false
        }

        return true
    }


}


