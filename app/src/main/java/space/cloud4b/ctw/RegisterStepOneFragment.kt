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

import kotlinx.android.synthetic.main.register_stepone_fragment.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 *
 * in einem ersten Schritt soll hier Username, EMail und Firma abgefragt werden
 * --> gibt es die Kompination vo User-Email und Firma schon? -> kein neuer User
 * -->    sonst User neu anlegen..
 * in einem zweiten Schritt dann das Team mit AccessCode oder ein neues Team erfassen
 */
class RegisterStepOneFragment : Fragment() {
    var avatar : String = "icn_male"



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


        // alle ImageButtons abdunkeln, ausser den ersten
        for(i in 1 until glAvatarSelection.childCount) {
            var imageButton : ImageButton = glAvatarSelection.getChildAt(i) as ImageButton
            imageButton.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        // für alle ImageButtons einen OnClick-Listener setzen
        for(i in 0 until glAvatarSelection.childCount) {
            var immageButton : ImageButton = glAvatarSelection.getChildAt(i) as ImageButton
            immageButton.setOnClickListener() {
                    immageButton.clearColorFilter()
                    avatar = immageButton.contentDescription.toString()
                //context.getResources().getIdentifier("space.cloud4b.ctw:drawable/$icnName",null,null)
                    etUsername.setCompoundDrawablesWithIntrinsicBounds(this.resources.getIdentifier("space.cloud4b.ctw:drawable/rf_$avatar",null,null), 0, 0, 0)
                    for(j in 0 until glAvatarSelection.childCount) {
                        if(glAvatarSelection.getChildAt(j) != immageButton) {
                            var otherImageButton : ImageButton = glAvatarSelection.getChildAt(j) as ImageButton
                            otherImageButton.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                        }
                    }
                }
        }
        // TODO wenn in den Preferences schon was eingetragen ist, dass soll es bereits in den Eingabefldern angezeigt werden..
        buRegisterStepTwo.setOnClickListener {
            if(validation()) {
                val editor =
                    this.requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
                        .edit()
                editor.putString("Username", etUsername.text.toString().trim())
                editor.putString("UserEmail", etUserEmail.text.toString().trim())
                editor.putString("UserOrg", spOrg.selectedItem.toString())
                editor.putString("UserAlias", etAlias.text.toString().trim())
                editor.putString("UserAvatar", avatar.trim())
                editor.apply()
                findNavController().navigate(R.id.action_registerStepOneFragment_to_registerStepTwoFragment)
            }
        }
        spOrg.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                view.hideKeyboard()
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == "Neue Firma hinzufügen..") {
                    Log.i("debug", "neue Firma hinzufügen..")
                    findNavController().navigate(R.id.action_registerStepOneFragment_to_addCompanyFragment)
                }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    // TODO interessante Codestelle -> validation
    fun validation() : Boolean {
        
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
                spOrg.adapter = aa
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