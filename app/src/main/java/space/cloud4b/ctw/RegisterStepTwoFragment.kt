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
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.entrylist_fragment.*
import kotlinx.android.synthetic.main.register_fragment.*
import kotlinx.android.synthetic.main.register_fragment.buBackToRegisterStepOne
import kotlinx.android.synthetic.main.register_fragment.etRegCode
import kotlinx.android.synthetic.main.register_fragment.spTeam
import kotlinx.android.synthetic.main.register_steptwo_fragment.*
import org.json.JSONObject
import space.cloud4b.ctw.model.Cakeboard
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
class RegisterStepTwoFragment : Fragment() {
   // var organisations = arrayOf("UBS", "Stadt Zürich")
   var organisationsList = ArrayList<String>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.register_steptwo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        buRegisterFinal.setOnClickListener {
            val editor = this.requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE).edit()
            editor.putString("UserTeam", spTeam.selectedItem.toString())
            editor.putString("TeamAccessCode", etRegCode.text.toString().trim())
            editor.apply()
            checkRegistration()

        }

       buBackToRegisterStepOne.setOnClickListener {

        }

    }

    fun checkRegistration() {
        var url = "https://cloud4b.space/caketowork/registration.php"
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
       // var teamAccessCode = preferences.getString("TeamAccessCode", "")
        url += "?TAC=${preferences.getString("TeamAccessCode", "")}"
        url += "&Username=${preferences.getString("Username", "")}"
        url += "&UserEmail=${preferences.getString("UserEmail", "")}"
        url += "&UserOrg=${preferences.getString("UserOrg", "")}"
        url += "&UserTeam=${preferences.getString("UserTeam", "")}"
        Log.i("URL", url)
        val requestQueue = Volley.newRequestQueue(activity)
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.i("Response --> VOLLEYOK", response)
                val editor = this.requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE).edit()
                editor.putString("UserStatus", response)
                editor.apply()
                if(response=="NOK"){
                    etRegCode.error = "falscher Zugangscode"
                   // findNavController().navigate(R.id.action_registerStepTwoFragment_to_dashboard_fragment)
                } else {
                    findNavController().navigate(R.id.action_registerStepTwoFragment_to_dashboard_fragment)
                }
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }
}