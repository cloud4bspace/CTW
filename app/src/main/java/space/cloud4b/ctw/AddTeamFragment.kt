package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.add_company_fragment.*
import kotlinx.android.synthetic.main.add_team_fragment.*

import kotlinx.android.synthetic.main.register_stepone_fragment.*
import kotlinx.android.synthetic.main.register_steptwo_fragment.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 *
 * in einem ersten Schritt soll hier Username, EMail und Firma abgefragt werden
 * --> gibt es die Kompination vo User-Email und Firma schon? -> kein neuer User
 * -->    sonst User neu anlegen..
 * in einem zweiten Schritt dann das Team mit AccessCode oder ein neues Team erfassen
 */
class AddTeamFragment : Fragment() {
    var avatar : String = "icn_male"



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_team_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Spinner mit Firmennamen aus der DB füllen


        // define a request
        activity?.let { fillOrgSpinner(it) }

        spAddTeamOrg.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == "Neue Firma hinzufügen..") {
                    Log.i("debug", "neue Firma hinzufügen..")
                    findNavController().navigate(R.id.action_addTeamFragment_to_addCompanyFragment)
                }
                if (selectedItem != "Neue Firma hinzufügen.." && selectedItem != "Firma wählen..") {
                    etAddTeamTeam.isEnabled = true
                    etAddTeamTeam.requestFocus()
                }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        buAddTeamSave.setOnClickListener() {
            it.hideKeyboard()
            trySaveNewTeam()
        }

        buAddTeamWelcome.setOnClickListener() {
            findNavController().navigate(R.id.action_addTeamFragment_to_welcome_fragment)
        }
    }

    fun validation() : Boolean {

        // RadioGroup Gender
        // TODO löschen
        /*
        if (!rbMale.isChecked && !rbFemale.isChecked) {
            rbMale.error = "Du musst Dich entscheiden"
            rbMale.requestFocus()
            return false
        }*/

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
                var responseNeu = "Firma wählen..|Neue Firma hinzufügen..|" + response
                var list = responseNeu.split("|") as ArrayList<String>
                val aa = ArrayAdapter<String>(x,android.R.layout.simple_spinner_item,list)
                spAddTeamOrg.adapter = aa
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }

    fun trySaveNewTeam() {
        var url = "https://cloud4b.space/caketowork/addnewteam.php"
        url += "?CompName=" + spAddTeamOrg.selectedItem.toString()
        url += "&TeamName=" + etAddTeamTeam.text.toString().trim()
        Log.i("Debug", "trySaveNewTeam")
        val requestQueue = Volley.newRequestQueue(activity)
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.i("Response from URL", response)
                if(response.equals("NOK")) {
                    etAddTeamTeam.error = "dieses Team existiert bereits"
                } else {
                    val toast = Toast.makeText(activity,
                        "Team wurde erfolgreich hinzugefügt!", Toast.LENGTH_LONG)
                    toast.show()
                    val editor =
                        this.requireActivity().getSharedPreferences("USR_INFO",
                            Context.MODE_PRIVATE).edit()
                    editor.putString("TeamAccessCode", response)
                    editor.putString("UserOrg", spAddTeamOrg.selectedItem.toString())
                    editor.putString("UserTeam", etAddTeamTeam.text.toString().trim())
                    editor.apply()
                    spAddTeamOrg.isEnabled = false
                    etAddTeamRegCode.visibility = View.VISIBLE
                    etAddTeamRegCode.setText(response.toString().trim())
                    etAddTeamRegCode.isEnabled = false
                    etAddTeamTeam.isEnabled = false
                    tvAddTeamInfo.visibility = View.VISIBLE
                    tvAddTeamInfo.setText("Teile den TeamAccessCode ($response) mit Deinen " +
                            "Kolleg*inndn, damit sie sich für Dein Team registrieren können!")
                    buAddTeamSave.visibility = View.GONE
                    buAddTeamWelcome.visibility = View.VISIBLE


                }
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }

    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

}