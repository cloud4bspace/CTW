package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.entrylist_fragment.*
import space.cloud4b.ctw.model.Cakeboard
import space.cloud4b.ctw.model.Monthlyboard
import space.cloud4b.ctw.services.CakeboardAdapter
import space.cloud4b.ctw.services.IconMapper
import space.cloud4b.ctw.services.MonthlyboardAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DashboardFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // das nächste Znüni anzeigen
        setNextEntry()

        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        var teamAccessCode = preferences.getString("TeamAccessCode", "")
        val requestQueue = Volley.newRequestQueue(activity)
        var url = "https://cloud4b.space/caketowork/ctwmonthsummary.php"
        url += "?TAC=$teamAccessCode"
        Log.i("monthlySQL", url)
        // define a request
        val request = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                val entry : Monthlyboard? = Klaxon().parse<Monthlyboard>(response)
                val adapter = activity?.let { MonthlyboardAdapter(entry?.monthlyboard!!, it) }
                lvMonthlyReason.adapter = adapter
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dashboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        view.findViewById<Button>(R.id.bu_back).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/

        ibAddItem.setOnClickListener() {
            findNavController().navigate(R.id.action_dashboard_fragment_to_additemFragment)
        }
        ibShowList.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_fragment_to_entrylistFragment)
        }
        ibMeinTeam.setOnClickListener(){
            findNavController().navigate(R.id.action_dashboard_fragment_to_teamlistFragment)
        }
        ibHelp.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_fragment_to_websiteOneFragment)
        }
       // tvNextEvent.text = "17. Juni 2020"
        tvNextEvent.setOnClickListener {
            val text = "Hello toast!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(activity,text,  duration)
            toast.show()
        }
    }


    fun setNextEntry() {
        var url = "https://cloud4b.space/caketowork/ctwnextentry.php"
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        var teamAccessCode = preferences.getString("TeamAccessCode", "")
        val requestQueue = Volley.newRequestQueue(activity)
        url += "?TAC=$teamAccessCode"
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                println("Response: $response")
                var responseList = response.split("|")
                var date = LocalDate.parse(responseList[1])
                tvNextEvent.text = date.format(
                    DateTimeFormatter.ofLocalizedDate(
                        FormatStyle.LONG))
                tvVonWem.text = responseList[2]

                //tvTZeit.text = getTagesZeitString(responseList[3].toInt())
                // alle Icons auflisten
                val lp = LinearLayout.LayoutParams(60, 60)
                lp.setMargins(10, 10, 10, 10)

                var imageViewTime = ImageView(activity)
                imageViewTime.setImageResource(getResources().getIdentifier("space.cloud4b.ctw:drawable/${IconMapper().getIcnName(responseList[4])}",null,null))
                llContainerTop.addView(imageViewTime,20,20)
                imageViewTime.setLayoutParams(lp)

                var imageViewReason = ImageView(activity)
                imageViewReason.setImageResource(getResources().getIdentifier("space.cloud4b.ctw:drawable/${IconMapper().getIcnName(responseList[3])}",null,null))
                llContainerTop.addView(imageViewReason, 20, 20)
                imageViewReason.setLayoutParams(lp)
                var newRow : LinearLayout = LinearLayout(activity)
                for(x in 7 until responseList.size) {

                    newRow.orientation = LinearLayout.HORIZONTAL;

                    if(x<19) {
                        var newImage = ImageView(activity)
                        newImage.setImageResource(getResources().getIdentifier("space.cloud4b.ctw:drawable/${IconMapper().getIcnName(responseList[x])}",null,null))
                        llIcnContainer.addView(newImage, 20, 20)
                        newImage.setLayoutParams(lp)
                    } else {
                        if(x == 19) {
                            llWatsNext.addView(newRow)
                        }
                        var newImage = ImageView(activity)
                        newImage.setImageResource(getResources().getIdentifier("space.cloud4b.ctw:drawable/${IconMapper().getIcnName(responseList[x])}",null,null))
                        newRow.addView(newImage, 20, 20)
                        newImage.setLayoutParams(lp)
                    }
                }

            /*    var newRow = LinearLayout(activity)
                llWatsNext.addView(newRow)
                var newImage = ImageView(activity)
                newImage.setImageResource(getResources().getIdentifier("space.cloud4b.ctw:drawable/${IconMapper().getIcnName(responseList[1])}",null,null))

                newRow.addView(newImage, 20, 20)
                newImage.setLayoutParams(lp)*/


            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }




}