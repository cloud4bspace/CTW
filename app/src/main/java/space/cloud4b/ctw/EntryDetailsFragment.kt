package space.cloud4b.ctw

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.entrylist_fragment.*
import kotlinx.android.synthetic.main.enty_details_fragment.*
import kotlinx.android.synthetic.main.register_fragment.*
import org.json.JSONObject
import space.cloud4b.ctw.model.Cakeboard
import space.cloud4b.ctw.model.CakeboardEntry
import space.cloud4b.ctw.model.Companies
import space.cloud4b.ctw.services.CakeboardAdapter

import java.net.URL


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 *
 * in einem ersten Schritt soll hier Username, EMail und Firma abgefragt werden
 * --> gibt es die Kompination vo User-Email und Firma schon? -> kein neuer User
 * -->    sonst User neu anlegen..
 * in einem zweiten Schritt dann das Team mit AccessCode oder ein neues Team erfassen
 */
class EntryDetailsFragment : Fragment() {
    var entry : CakeboardEntry? = null
   var organisationsList = ArrayList<String>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Spinner mit Firmennamen aus der DB füllen
        val requestQueue = Volley.newRequestQueue(activity)
        var url = "https://cloud4b.space/caketowork/complist.php"
        // define a request

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.enty_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var url : String = "https://cloud4b.space/caketowork/index.php"

        tvDatumDetail.text = "Test"
    }
}