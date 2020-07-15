package space.cloud4b.ctw

import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.additem_fragment.*
import kotlinx.android.synthetic.main.additem_steptwo_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.register_fragment.*
import org.jetbrains.anko.childrenRecursiveSequence
import org.jetbrains.anko.forEachChild
import org.jetbrains.anko.forEachChildWithIndex
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AdditemStepTwoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Register Fragment onCreateView")


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.additem_steptwo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buAddItemGotoStepThree.setOnClickListener {
            findNavController().navigate(R.id.action_additemStepTwoFragment_to_additemStepThreeFragment)
        }
        /*  for(i in 0 until llReasonsLineOne.childCount) {
      if(llReasonsLineOne.getChildAt(i).id == ibGoodbye.id){

      } else {

          llReasonsLineOne.getChildAt(i).setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
      }
  }*/
        ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
        ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
        ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
        ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
        ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
        ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
        ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
        ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
        ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
        ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
        ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
        ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)


        ibGoodbye.setOnClickListener() {
            if(tvReason.text.toString().equals("Abschied"))  {
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                tvReason.setText("")
            } else {
                ibGoodbye.clearColorFilter()
                tvReason.setText("Abschied")
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
        ibBirthday.setOnClickListener() {
            if(tvReason.text.toString().equals("Geburtstag"))  {
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                tvReason.setText("")
            } else {
                ibBirthday.clearColorFilter()
                tvReason.setText("Geburtstag")
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
        ibWedding.setOnClickListener() {
            if(tvReason.text.toString().equals("Heirat"))  {
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                tvReason.setText("")
            } else {
                ibWedding.clearColorFilter()
                tvReason.setText("Heirat")
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
        ibSecret.setOnClickListener() {
            if(tvReason.text.toString().equals("Geheim"))  {
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                tvReason.setText("")
            } else {
                ibSecret.clearColorFilter()
                tvReason.setText("Geheim")
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
        ibRetirement.setOnClickListener() {
            if(tvReason.text.toString().equals("Pensionierung"))  {
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                tvReason.setText("")
            } else {
                ibRetirement.clearColorFilter()
                tvReason.setText("Pensionierung")
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
        ibNewborn.setOnClickListener() {
            if(tvReason.text.toString().equals("Nachwuchs"))  {
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                tvReason.setText("")
            } else {
                ibNewborn.clearColorFilter()
                tvReason.setText("Nachwuchs")
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
        ibJackpot.setOnClickListener() {
            if(tvReason.text.toString().equals("Jackpot"))  {
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                tvReason.setText("")
            } else {
                ibJackpot.clearColorFilter()
                tvReason.setText("Jackpot")
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
        ibHoliday.setOnClickListener() {
            if(tvReason.text.toString().equals("Ferien"))  {
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                tvReason.setText("")
            } else {
                ibHoliday.clearColorFilter()
                tvReason.setText("Ferien")
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
        ibGraduation.setOnClickListener() {
            if(tvReason.text.toString().equals("Abschluss"))  {
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                tvReason.setText("")
            } else {
                ibGraduation.clearColorFilter()
                tvReason.setText("Abschluss")
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
        ibFirstprize.setOnClickListener() {
            if(tvReason.text.toString().equals("Auszeichnung"))  {
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                tvReason.setText("")
            } else {
                ibFirstprize.clearColorFilter()
                tvReason.setText("Auszeichnung")
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
        ibPromotion.setOnClickListener() {
            if(tvReason.text.toString().equals("Beförderung"))  {
                ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                tvReason.setText("")
            } else {
                ibPromotion.clearColorFilter()
                tvReason.setText("Beförderung")
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
        ibJustForFun.setOnClickListener() {
            if(tvReason.text.toString().equals("einfach so"))  {
                ibJustForFun.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                tvReason.setText("")
            } else {
                ibJustForFun.clearColorFilter()
                tvReason.setText("einfach so")
                ibGoodbye.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibBirthday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibFirstprize.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibGraduation.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibHoliday.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibJackpot.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibNewborn.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibRetirement.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibSecret.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibWedding.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ibPromotion.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }



    }



}


