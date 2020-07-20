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
import android.widget.RadioButton
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.entrylist_fragment.*
import kotlinx.android.synthetic.main.register_fragment.*
import kotlinx.android.synthetic.main.register_fragment.buRegisterStepTwo
import kotlinx.android.synthetic.main.register_fragment.etUserEmail
import kotlinx.android.synthetic.main.register_fragment.etUsername
import kotlinx.android.synthetic.main.register_fragment.spOrg
import kotlinx.android.synthetic.main.register_stepone_fragment.*
import org.json.JSONObject
import space.cloud4b.ctw.model.Cakeboard
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
class RegisterStepOneFragment : Fragment() {



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register_stepone_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Spinner mit Firmennamen aus der DB füllen


        // define a request
        activity?.let { fillOrgSpinner(it) }

        rgSex.setOnCheckedChangeListener {_, _ ->
            rbFemale.error = null
            rbMale.error = null
        }

        buRegisterStepTwo.setOnClickListener {
            if(validation()) {
                val editor =
                    this.requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
                        .edit()
                editor.putString("Username", etUsername.text.toString().trim())
                editor.putString("UserEmail", etUserEmail.text.toString().trim())
                editor.putString("UserOrg", spOrg.selectedItem.toString())
                editor.putString("UserAlias", etAlias.text.toString().trim())
                if (rbMale.isChecked) {
                    editor.putString("UserSex", "male")
                } else {
                    editor.putString("UserSex", "female")
                }
                editor.apply()
                findNavController().navigate(R.id.action_registerStepOneFragment_to_registerStepTwoFragment)
            }
        }
    }

    fun validation() : Boolean {

        // RadioGroup Gender
        if (!rbMale.isChecked && !rbFemale.isChecked) {
            rbMale.error = "Du musst Dich entscheiden"
            rbMale.requestFocus()
            return false
        }

        // Username
        if(etUsername.getText().toString().trim().isEmpty()){
            etUsername.error = "Bitte gib Deinen Namen an"
            etUsername.requestFocus()
            return false
        }

        // Alias
        if(etAlias.getText().toString().trim().isEmpty()){
            etAlias.error = "Bitte einen Nickname eingeben"
            etAlias.requestFocus()
            return false
        }

        // E-Mail-Adresse
        if(etUserEmail.getText().toString().trim().isEmpty()){
            etUserEmail.error = "Bitte gib Deine E-Mail-Adresse an"
            etUserEmail.requestFocus()
            return false
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(etUserEmail.text).matches()) {
            etUserEmail.error = "Das ist aber keine gültige E-Mail-Adresse"
            etUserEmail.requestFocus()
            return false
        }

        return true
    }

    fun fillOrgSpinner(x : Context) {
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
                val aa = ArrayAdapter<String>(x,android.R.layout.simple_spinner_item,list)
                spOrg.adapter = aa
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }

}