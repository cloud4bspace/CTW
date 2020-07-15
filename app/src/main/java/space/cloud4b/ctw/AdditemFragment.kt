package space.cloud4b.ctw

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.additem_fragment.*
import kotlinx.android.synthetic.main.additem_steptwo_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.register_fragment.*
import kotlinx.android.synthetic.main.register_fragment.etRegCode
import kotlinx.android.synthetic.main.register_fragment.spTeam
import kotlinx.android.synthetic.main.register_steptwo_fragment.*
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
class AdditemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Register Fragment onCreateView")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.additem_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val current = LocalDateTime.now()
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val kal = Calendar.getInstance()

        kal.set(current.year, current.monthValue-1, current.dayOfMonth)
        dpDate.minDate = kal.timeInMillis
        checkDateAvailability(current.year, current.monthValue-1, current.dayOfMonth)

        dpDate.setOnDateChangedListener { datePicker, year, monthindex, day ->
            checkDateAvailability(year, monthindex+1, day)
        }
        buAddItemGotoStepTwo.setOnClickListener {
            findNavController().navigate(R.id.action_additemFragment_to_additemStepTwoFragment)
        }

    }
    fun checkDateAvailability(year : Int, month : Int, day : Int) {
        val date = LocalDate.of(year, month, day)
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        val tac = preferences.getString("TeamAccessCode", "")
        var url = "https://cloud4b.space/caketowork/checkdateavailability.php"
        url += "?TAC=$tac"
        url += "&Datum=${date.toString()}"
        val requestQueue = Volley.newRequestQueue(activity)
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                if(response.toInt()>0){
                    tvCheckResultDate.text = "Achtung! Dieses Datum ist schon besetzt!"
                    ivCheckResultIcon.setImageResource(R.drawable.warning)
                } else {
                    tvCheckResultDate.text = "Prima! Dieses Datum ist noch frei!"
                    ivCheckResultIcon.setImageResource(R.drawable.positivecharges)
                }
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }


}


