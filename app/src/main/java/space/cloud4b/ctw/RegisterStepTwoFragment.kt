package space.cloud4b.ctw

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.register_steptwo_fragment.*


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
        activity?.let { fillTeamSpinner(it) }
        return inflater.inflate(R.layout.register_steptwo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        buRegisterFinal.setOnClickListener {
            if(validation()) {
                val editor = this.requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE).edit()
                editor.putString("UserTeam", spTeam.selectedItem.toString())
                editor.putString("TeamAccessCode", etRegCode.text.toString().trim())
                editor.apply()
                checkRegistration()
            }
        }

       buBackToRegisterStepOne.setOnClickListener {

        }

    }

    // TODO interessante Textstelle: Spinner füllen
    fun fillTeamSpinner(x : Context) {
        var url = "https://cloud4b.space/caketowork/teamstringlist.php"
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        url += "?CompBez=${preferences.getString("UserOrg", "")}"
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
                var responseNeu = "Team wählen..|Neues Team hinzufügen..|" + response
                var list = responseNeu.split("|") as ArrayList<String>

                val aa = ArrayAdapter<String>(x,android.R.layout.simple_spinner_item,list)
                spTeam.adapter = aa
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }

    fun checkRegistration() {
        var url = "https://cloud4b.space/caketowork/registration.php"
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        url += "?TAC=${preferences.getString("TeamAccessCode", "")}"
        url += "&Username=${preferences.getString("Username", "")}"
        url += "&UserEmail=${preferences.getString("UserEmail", "")}"
        url += "&UserOrg=${preferences.getString("UserOrg", "")}"
        url += "&UserTeam=${preferences.getString("UserTeam", "")}"
        url += "&UserAvatar=${preferences.getString("UserAvatar", "")}"
        url += "&UserAlias=${preferences.getString("UserAlias", "NA")}"
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
                    // restart App (fab will be visible after
                    val i: Intent? = requireActivity().getPackageManager()
                        .getLaunchIntentForPackage(requireActivity().getPackageName())
                    i?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(i)
                    //  findNavController().navigate(R.id.action_registerStepTwoFragment_to_dashboard_fragment)
                }
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }



    fun validation() : Boolean {

        if(spTeam.selectedItem.toString().equals("Team wählen..")) {
                spTeam.requestFocus()
            return false
            }

        // TeamCode
        if(etRegCode.getText().toString().trim().isEmpty()){
            etRegCode.error = "Bitte den Zugangscode eingeben"
            etRegCode.requestFocus()
            return false
        }
        return true
    }
}