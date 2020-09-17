package space.cloud4b.ctw

import android.content.Context
import android.media.MediaPlayer
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
 * Dashboard ist der zentrale Einstiegspunkt, wenn der User bereits registriert ist
 * (d.h. SharedPreferences "USR_INFO" -> "UserStatus" = "OK")
 * Dashboard zeigt den nächsten Termin, die Navigationsfunktionen und eine Monatsstatistik
 */
class DashboardFragment : Fragment() {
    var entryItemArray = Array<String>(13){"9999"}
  // TODO app stürzt ab, wenn es noch keine Einträge in der Datebank gibt...
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
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
        cvWhatsNext.visibility = View.GONE

        // das nächste Znüni anzeigen in der obersten CardView
        setNextEntry()

        ibAddItem.setOnClickListener() {
            findNavController().navigate(R.id.action_dashboard_fragment_to_additemFragment)
        }
        ibShowList.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_fragment_to_entrylistFragment)
        }
        ibMeinTeam.setOnClickListener(){
            findNavController().navigate(R.id.action_dashboard_fragment_to_teamlistFragment)
        }

       // tvNextEvent.text = "17. Juni 2020"
        /* TODO das hier braucht es wohl nicht mehr..
        tvNextEvent.setOnClickListener {
            val text = "Hello toast!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(activity,text,  duration)
            toast.show()
        }*/

        cvWhatsNext.setOnClickListener() {
            val action = DashboardFragmentDirections.actionDashboardFragmentToEntryItemFragment(entryItemArray)
            findNavController().navigate(action)
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
            { response ->
                if(response.isEmpty()) {
                    llWatsNext.visibility = View.GONE
                    tvDBWhatsNext.visibility = View.GONE
                    divDBDividerI.visibility = View.GONE
                    tvDBEventList.visibility = View.GONE
                    divDBDividerII.visibility = View.GONE
                    cvWhatsNext.visibility = View.GONE

                } else {
                    llWatsNext.visibility = View.VISIBLE
                    tvDBWhatsNext.visibility = View.VISIBLE
                    divDBDividerI.visibility = View.VISIBLE
                    tvDBEventList.visibility = View.VISIBLE
                    divDBDividerII.visibility = View.VISIBLE
                    cvWhatsNext.visibility = View.VISIBLE
                    println("Response: $response")
                    var responseList = response.split("|")
                    entryItemArray[0] = responseList[0]
                    entryItemArray[6] = responseList[2]
                    entryItemArray[1] = responseList[1]
                    entryItemArray[3] = responseList[3]
                    entryItemArray[2] = responseList[4]
                    entryItemArray[4] = responseList[6]
                    entryItemArray[7] = responseList[7] // UserAlias
                    entryItemArray[8] = responseList[8] // Avatar
                    entryItemArray[12] = responseList[9] // UserEmail
                    entryItemArray[9] = responseList[10] // angemeldet
                    entryItemArray[10] = responseList[11] // abgemeldet
                    entryItemArray[11] = responseList[12] // keine antwort
                    var foodListString = ""
                    for(i in 13 until (responseList.size)) {
                        foodListString += responseList[i]
                        if(i < (responseList.size-1)) {foodListString += "|"}
                    }
                    entryItemArray[5] = foodListString // FoodList

                    var date = LocalDate.parse(responseList[1])

                    tvNextEvent.text = date.format(
                        DateTimeFormatter.ofLocalizedDate(
                            FormatStyle.LONG
                        )
                    )
                    tvVonWem.text = responseList[2]


                    // alle Icons auflisten
                    val lp = LinearLayout.LayoutParams(60, 60)
                    lp.setMargins(10, 10, 10, 10)

                    var imageViewTime = ImageView(activity)
                    imageViewTime.setImageResource(
                        getResources().getIdentifier(
                            "space.cloud4b.ctw:drawable/${IconMapper().getIcnName(
                                responseList[4]
                            )}", null, null
                        )
                    )

                    llContainerTop.addView(imageViewTime, 20, 20)
                    imageViewTime.setLayoutParams(lp)

                    var imageViewReason = ImageView(activity)
                    imageViewReason.setImageResource(
                        getResources().getIdentifier(
                            "space.cloud4b.ctw:drawable/${IconMapper().getIcnName(
                                responseList[3]
                            )}", null, null
                        )
                    )
                    llContainerTop.addView(imageViewReason, 20, 20)
                    imageViewReason.setLayoutParams(lp)
                    val newRow: LinearLayout = LinearLayout(activity)
                    for (x in 13 until responseList.size) {
                        newRow.orientation = LinearLayout.HORIZONTAL;

                        if (x < 19) {
                val newImage = ImageView(activity)
                newImage.setImageResource(
                    resources.getIdentifier(
                    "space.cloud4b.ctw:drawable/${IconMapper().getIcnName(responseList[x])}",
                    null, null
                    )
                )
                llIcnContainer.addView(newImage, 20, 20)
                newImage.layoutParams = lp
                        } else {
                            if (x == 19) {
                                llWatsNext.addView(newRow)
                            }
                            var newImage = ImageView(activity)
                            newImage.setImageResource(
                                getResources().getIdentifier(
                                    "space.cloud4b.ctw:drawable/${IconMapper().getIcnName(
                                        responseList[x]
                                    )}", null, null
                                )
                            )
                            newRow.addView(newImage, 20, 20)
                            newImage.setLayoutParams(lp)
                        }
                    }

                }

            },
            {
                it.message?.let { it1 -> Log.e("VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }




}