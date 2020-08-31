package space.cloud4b.ctw

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.entryitem_fragment.*
import kotlinx.android.synthetic.main.register_stepone_fragment.*
import space.cloud4b.ctw.services.IconMapper

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 *
 * in einem ersten Schritt soll hier Username, EMail und Firma abgefragt werden
 * --> gibt es die Kompination vo User-Email und Firma schon? -> kein neuer User
 * -->    sonst User neu anlegen..
 * in einem zweiten Schritt dann das Team mit AccessCode oder ein neues Team erfassen
 */
class EntryItemFragment : Fragment() {
    val args: EntryItemFragmentArgs by navArgs()

    val TEXTANGEMELDET = "Du bist bereits angemeldet"
    val TEXTABGEMELDET = "Du hast dich abgemeldet"
    val TEXTOHNEANTWORT = "Du hast noch nicht geantwortet"



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.entryitem_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        var userEmail = preferences.getString("UserEmail", "").toString()
        var teamAccessCode = preferences.getString("TeamAccessCode", "").toString()
        var entryItemArray = args.entryItemArray

        // define a request
       // activity?.let { execTransaction(it) }
      //  var url : String = "https://cloud4b.space/caketowork/index.php"

        anmeldeStatus(userEmail, entryItemArray[0])
        var icnEntryItemTime = IconMapper().getIcnName(entryItemArray[2])
        ivEntryItemTime.setImageResource(getResources().getIdentifier("space.cloud4b.ctw:drawable/$icnEntryItemTime",null,null))

        var date = LocalDate.parse(entryItemArray[1])
        tvEntryItemDate.text = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
        tvEntryItemAlias.text = "(${entryItemArray[7]})"
        tvEntryItemName.text = entryItemArray[6]
        tvEntryItemReason.text = entryItemArray[3]

        // Container mit Bemerkungen/Infos nur anzeigen, wenn Infos vorhanden sind
        if(entryItemArray[4].trim().isEmpty()){
            llEntryItemDescription.setVisibility(View.GONE)
        } else {
            llEntryItemDescription.setVisibility(View.VISIBLE)
            tvEntryItemDescription.text = entryItemArray[4]
        }


        var icnEntryItemReason = IconMapper().getIcnName(entryItemArray[3])
        ivEntryItemReason.setImageResource(getResources().getIdentifier("space.cloud4b.ctw:drawable/$icnEntryItemReason",null,null))
        ivEntryItemAvatar.setImageResource(getResources().getIdentifier("space.cloud4b.ctw:drawable/${entryItemArray[8]}",null,null))
        val lp = LinearLayout.LayoutParams(60, 60)
        lp.setMargins(10, 10, 10, 10)
        var reasonsArray = entryItemArray[5].split("|")
        for(i in reasonsArray.indices) {
                var newImage = ImageView(activity)
                newImage.setImageResource(getResources().getIdentifier("space.cloud4b.ctw:drawable/${IconMapper().getIcnName(reasonsArray[i])}",null,null))
                llEntryItemFBContainer.addView(newImage, 24, 24)
                newImage.setLayoutParams(lp)
        }

        tvEntryItemAngemeldet.text = entryItemArray[9] + " Personen sind angemeldet"
        tvEntryItemAbgemeldet.text = entryItemArray[10] + " Personen sind abgemeldet"
        tvEntryItemOhneAntwort.text = entryItemArray[11] + " Personen sind unentschlossen"

        pbEntryItem.visibility = View.GONE

        if(userEmail.equals(entryItemArray[12])) {
            llDeleteItem.visibility = View.VISIBLE
        } else {
            llDeleteItem.visibility = View.GONE
        }
        llAnmelden.setOnClickListener() {
            recalcTeilnehmer("1", entryItemArray)
            llAnmelden.visibility = View.GONE
            llAbmelden.visibility = View.VISIBLE
            llReset.visibility = View.VISIBLE
            tvEntryItemStatusString.text = TEXTANGEMELDET
            setAnmeldeStatus("1", userEmail, entryItemArray[0])
        }

        llAbmelden.setOnClickListener() {
            recalcTeilnehmer("2", entryItemArray)
            llAbmelden.visibility = View.GONE
            llAnmelden.visibility = View.VISIBLE
            llReset.visibility = View.VISIBLE
            tvEntryItemStatusString.text = TEXTABGEMELDET
            setAnmeldeStatus("2", userEmail, entryItemArray[0])
        }

        llReset.setOnClickListener() {
            recalcTeilnehmer("9", entryItemArray)
            llReset.visibility = View.GONE
            llAbmelden.visibility = View.VISIBLE
            llAnmelden.visibility = View.VISIBLE
            tvEntryItemStatusString.text = TEXTOHNEANTWORT
            setAnmeldeStatus("9", userEmail, entryItemArray[0])
        }

        llShowParticipants.setOnClickListener() {
           // findNavController().navigate(R.id.action_entryItemFragment_to_participantslistFragment)
            val action = EntryItemFragmentDirections.actionEntryItemFragmentToParticipantslistFragment(entryItemArray)
            findNavController().navigate(action)
         //   val action = EntrylistFragmentDirections.actionEntrylistFragmentToEntryItemFragment(entryItemArray)
        }

        llDeleteItem.setOnClickListener() {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Diesen Eintrag löschen")
            builder.setMessage("Soll dieser Eintrag wirklich gelöscht werden?")

            builder.setPositiveButton("Ja") { dialog, which ->
                delEntryElement(teamAccessCode, userEmail, entryItemArray[0])
                Toast.makeText(activity,
                    "Eintrag ${entryItemArray[0]} wird gelöscht", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_entryItemFragment_to_dashboard_fragment)
            }

            builder.setNegativeButton("Nein") { dialog, which ->
                Toast.makeText(activity,
                    "Aktion wurde abgebrochen", Toast.LENGTH_SHORT).show()
            }

            builder.show()
        }

    }

    fun edxecTransaction(context : Context) {
        var url = "https://cloud4b.space/caketowork/compstringlist.php"
        var companiesList : ArrayList<String>? = null
        //var organisationsList : List<String> = null
        Log.i("URL", url)
        val requestQueue = Volley.newRequestQueue(activity)
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.i("Response from URL", response)
                // var list = ArrayList<String>()
                var list = response.split("|") as ArrayList<String>
                val aa = ArrayAdapter<String>(context ,android.R.layout.simple_spinner_item,list)
                spOrg.adapter = aa
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }

    fun anmeldeStatus(userEmail: String, itemId: String) {
        var url = "https://www.cloud4b.space/caketowork/ctwDoodleTrx.php"
        url += "?ACT=getStatus"
        url += "&UserEmail=$userEmail"
        url += "&ItemId=$itemId"
        Log.i("URL", url)
        val requestQueue = Volley.newRequestQueue(activity)
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.i("Response from URL", response)
                when (response) {
                    "1|0" -> {
                        llAnmelden.visibility = View.GONE
                        tvEntryItemStatusString.text = TEXTANGEMELDET
                    }
                    "0|1" -> {
                        llAbmelden.visibility = View.GONE
                        tvEntryItemStatusString.text = TEXTABGEMELDET
                    }
                    "9|9" -> {
                        llReset.visibility = View.GONE
                        tvEntryItemStatusString.text = TEXTOHNEANTWORT
                    }
                }
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }

    fun setAnmeldeStatus(status : String, userEmail: String, itemId: String) {
        var url = "https://www.cloud4b.space/caketowork/ctwDoodleTrx.php"
        url += "?ACT=setStatus"
        url += "&Status=$status"
        url += "&UserEmail=$userEmail"
        url += "&ItemId=$itemId"
        Log.i("URL", url)
        val requestQueue = Volley.newRequestQueue(activity)
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.i("neuer Anmeldestatus = ", response)
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }

    fun delEntryElement(teamAccessCode: String, userEmail: String, entryId: String) {
        var url = "https://cloud4b.space/caketowork/delitem.php?TAC=$teamAccessCode&UserEmail=$userEmail&ItemId=$entryId"
        Log.i("URL", url)
        Log.i("Debug", "fun delEntryElement")
        val requestQueue = Volley.newRequestQueue(activity)
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.i("Response from URL (delitem.php)", response)
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }

    fun recalcTeilnehmer(status : String, entryItemArray : Array<String> ) {

        // angemeldet
        if(status == "1") {
            entryItemArray[9] = (entryItemArray[9].toInt() + 1).toString()
            tvEntryItemAngemeldet.text = entryItemArray[9] + " Personen sind angemeldet"
            if(tvEntryItemStatusString.text.toString().equals(TEXTABGEMELDET)) {
                entryItemArray[10] = (entryItemArray[10].toInt() - 1).toString()
                tvEntryItemAbgemeldet.text = entryItemArray[10] + " Personen sind abgemeldet"
            } else {
                entryItemArray[11] = (entryItemArray[11].toInt() - 1).toString()
                tvEntryItemOhneAntwort.text = entryItemArray[11] + " Personen sind unentschlossen"
            }
        }

        // abgemeldet
        if(status == "2") {
            entryItemArray[10] = (entryItemArray[10].toInt() + 1).toString()
            tvEntryItemAbgemeldet.text = entryItemArray[10] + " Personen sind abgemeldet"
            if(tvEntryItemStatusString.text.toString().equals(TEXTANGEMELDET)) {
                entryItemArray[9] = (entryItemArray[9].toInt() - 1).toString()
                tvEntryItemAngemeldet.text = entryItemArray[9] + " Personen sind angemeldet"
            } else {
                entryItemArray[11] = (entryItemArray[11].toInt() - 1).toString()
                tvEntryItemOhneAntwort.text = entryItemArray[11] + " Personen sind unentschlossen"
            }
        }

        // abgemeldet
        if(status == "9") {
            entryItemArray[11] = (entryItemArray[11].toInt() + 1).toString()
            tvEntryItemOhneAntwort.text = entryItemArray[11] + " Personen sind unentschlossen"

            if(tvEntryItemStatusString.text.toString().equals(TEXTANGEMELDET)) {
                entryItemArray[9] = (entryItemArray[9].toInt() - 1).toString()
                tvEntryItemAngemeldet.text = entryItemArray[9] + " Personen sind angemeldet"
            } else {
                entryItemArray[10] = (entryItemArray[10].toInt() - 1).toString()
                tvEntryItemAbgemeldet.text = entryItemArray[10] + " Personen sind abgemeldet"

            }
        }


    }
}