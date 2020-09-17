package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
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
 * Neues Team erfassen
 * User kann zu einer bestehenden Organisation ein neues Team erfassen.
 * User erhält anschliessend den für den Zugriff notwendigen TeamAccessCode TAC
 */
class AddTeamFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

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
            if(validation()) {
                 trySaveNewTeam()
            }
        }

        buAddTeamWelcome.setOnClickListener() {
            findNavController().navigate(R.id.action_addTeamFragment_to_welcome_fragment)
        }
    }

    fun validation() : Boolean {

        // Team
        if(etAddTeamTeam.text.toString().trim().isEmpty()){
            etAddTeamTeam.error = "Bitte Teambezeichnung eingeben"
            etAddTeamTeam.requestFocus()
            return false
        }

        return true
    }

    /** In diesem Fragment soll das Optionsmenu nicht angzeigt werden.
     * Unter onCreateView ist zusätzlich setHasOptionsMenu(true) anzubringen
     * @param menu
     */
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
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