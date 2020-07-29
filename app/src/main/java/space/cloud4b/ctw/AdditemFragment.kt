package space.cloud4b.ctw

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.additem_fragment.*
import kotlinx.android.synthetic.main.additem_steptwo_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.*

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
    var newEntryArray = Array<String>(10){"9999"}

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

        // Set a SeekBar change listener
        sbTageszeit.progress = 1
        interpreteProgress(sbTageszeit.progress)
        sbTageszeit.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                // Display the current progress of SeekBar
               // tvTageszeit.text = "Progress : $progress"
                interpreteProgress(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
               // Toast.makeText(activity,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
               // Toast.makeText(activity,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })
        kal.set(current.year, current.monthValue-1, current.dayOfMonth)
        dpDate.minDate = kal.timeInMillis
        checkDateAvailability(current.year, current.monthValue-1, current.dayOfMonth)

        dpDate.setOnDateChangedListener { datePicker, year, monthindex, day ->
            checkDateAvailability(year, monthindex, day)
            Log.i("CheckDateAval", monthindex.toString())
        }
        buAddItemGotoStepTwo.setOnClickListener {
            val action = AdditemFragmentDirections.actionAdditemFragmentToAdditemStepTwoFragment(newEntryArray)
            findNavController().navigate(action)
           // findNavController().navigate(R.id.action_additemFragment_to_additemStepTwoFragment)
        }

    }

    fun interpreteProgress(progress : Int) {
        //newEntryArray[1] = progress.toString() // Tageszeit speichern
        when(progress) {
            1 -> {
                tvTageszeit.text = "Znüni (Vormittag)"
                newEntryArray[1] = "1"
                ivAM.clearColorFilter()
                ivPM.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ivApero.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
            2 -> {
                tvTageszeit.text = "Zvieri (Nachmittag)"
                newEntryArray[1] = "2"
                ivPM.clearColorFilter()
                ivAM.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ivApero.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
            3 -> {
                tvTageszeit.text = "Apéro (Abend)"
                newEntryArray[1] = "3"
                ivApero.clearColorFilter()
                ivPM.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                ivAM.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }

    }

    // TODO Datum auch auf Tage am Weekend prüfen...
    fun checkDateAvailability(year : Int, month : Int, day : Int) {
        val date = LocalDate.of(year, month+1, day)
        newEntryArray[0] = date.toString() // das Datum bei Index 0 speichern
        Log.i("gespeichertes Datum", date.toString())
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


