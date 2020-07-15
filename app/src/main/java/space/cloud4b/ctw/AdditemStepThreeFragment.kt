package space.cloud4b.ctw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.additem_steptwo_fragment.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AdditemStepThreeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Register Fragment onCreateView")


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.additem_stepthree_fragment_old, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*  for(i in 0 until llReasonsLineOne.childCount) {
      if(llReasonsLineOne.getChildAt(i).id == ibGoodbye.id){

      } else {

          llReasonsLineOne.getChildAt(i).setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
      }
  }*/
        ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);


        ibGoodbye.setOnClickListener() {
            if(tvReason.text.toString().equals("Abschied"))  {
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                tvReason.setText("")
            } else {
                ibGoodbye.clearColorFilter()
                tvReason.setText("Abschied")
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }


    }



}


