package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.marginRight
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.additem_stepfour_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.*
import space.cloud4b.ctw.model.NewCakeboardEntry
import space.cloud4b.ctw.services.IconMapper
import java.net.URLDecoder
import java.net.URLEncoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Letzter Schritt für die Erfassung eines neuen Eintrags (Termin)
 * Zusammenfassung wird angezeigt und User kann den Eintrag speichern.
 */
class AdditemStepFourFragment : Fragment() {
    val args: AdditemStepFourFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Register Fragment onCreateView")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.additem_stepfour_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var newEntryArray = args.newEntryArray
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)

        tvWer.text = preferences.getString("Username", "NA")
        tvFuerWen.text = preferences.getString("UserOrg", "NA") + ", " + preferences.getString("UserTeam", "NA")
        var date = LocalDate.parse(newEntryArray[0])
        tvDatumTageszeit.text = date.format(
            DateTimeFormatter.ofLocalizedDate(
                FormatStyle.LONG)) + " | " + IconMapper().getText(newEntryArray[1])
       // tvDatumTageszeit.text = newEntryArray[0] + " | " + newEntryArray[1]
        tvGrund.text = newEntryArray[2]
        tvBemerkungen.text = NewCakeboardEntry.infos


        // tvFoodAndBeverages.text = newEntryArray[4]
        var foodAndBeveragesList = newEntryArray[4].split("|")
        val lp = LinearLayout.LayoutParams(60, 60)
        lp.setMargins(10, 20, 10, 0)




        for (x in 0 until foodAndBeveragesList.size) {
                var newImage = ImageView(activity)
                newImage.setImageResource(
                    getResources().getIdentifier(
                        "space.cloud4b.ctw:drawable/${IconMapper().getIcnName(
                            foodAndBeveragesList.get(x)
                        )}", null, null
                    )
                )

                llFoodBeverageIcons.addView(newImage, 20, 20)
                newImage.setLayoutParams(lp)

        }

        buAddItemSave.setOnClickListener {
            it.setVisibility(View.GONE)
            buShowDashboard.setVisibility(View.VISIBLE)
            postNewEntry(newEntryArray) // TODO übergabe des Arrays ist am Ende obsolet
        }

        buShowDashboard.setOnClickListener() {
            findNavController().navigate(R.id.action_additemStepFourFragment_to_dashboard_fragment)
        }
    }

    fun postNewEntry(newEntryArray: Array<String>) {
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        val tac = preferences.getString("TeamAccessCode", "")
        NewCakeboardEntry.tac = preferences.getString("TeamAccessCode", "").toString()
        val userEmail = preferences.getString("UserEmail", "")
        NewCakeboardEntry.userEmail = preferences.getString("UserEmail", "").toString()
        var url = "https://cloud4b.space/caketowork/addnewitem.php"
        url += "?TAC=$tac"
        url += "&UserEmail=$userEmail"
        url += "&Date=${newEntryArray[0]}"
        url += "&Time=${newEntryArray[1]}"
        url += "&Reason=${newEntryArray[2]}"
        url += "&Infos=${newEntryArray[3]}"
        url += "&Beverages=${newEntryArray[4]}"

        Log.i("SQL", url)
        Log.i("Step 4 Summary", "tac: ${NewCakeboardEntry.tac}")
        Log.i("Step 4 Summary", "userEmail: ${NewCakeboardEntry.userEmail}")
        Log.i("Step 4 Summary", "date: ${NewCakeboardEntry.date}")
        Log.i("Step 4 Summary", "time: ${NewCakeboardEntry.time}")
        Log.i("Step 4 Summary", "reason: ${NewCakeboardEntry.reason}")
        Log.i("Step 4 Summary", "infos: ${NewCakeboardEntry.infos}")
        Log.i("Step 4 Summary", "beverages: ${NewCakeboardEntry.beverages}")
        val requestQueue = Volley.newRequestQueue(activity)
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
              // Log.i("Response", response)
                if(response.equals("1")) {
                    // object NewCakeBoardEntry zurücksetzen (soweit notwendig)
                    NewCakeboardEntry.infos = ""
                    // Rückmeldung anzeigen
                    val toast = Toast.makeText(activity, "Dein Team freut sich!", Toast.LENGTH_LONG)
                    toast.show()
                    // Bild anzeigen
                    val lp = LinearLayout.LayoutParams(700, 700)
                    lp.setMargins(10, 10, 10, 10)
                    lp.gravity = Gravity.HORIZONTAL_GRAVITY_MASK;
                    lp.marginStart = 150
                    var newImage = ImageView(activity)
                    newImage.setImageResource(
                        getResources().getIdentifier(
                            "space.cloud4b.ctw:drawable/icn_happyteam",
                            null,
                            null
                        )
                    )
                    llSummaryContainer.addView(newImage, 20, 20)
                    newImage.setLayoutParams(lp)
                } else {
                    val toast = Toast.makeText(activity, "Da hat etwas nicht geklappt", Toast.LENGTH_SHORT)
                    toast.show()
                }

            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)

    }



}


