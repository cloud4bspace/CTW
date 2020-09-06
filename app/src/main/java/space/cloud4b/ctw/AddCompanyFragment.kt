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


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 *
 * in einem ersten Schritt soll hier Username, EMail und Firma abgefragt werden
 * --> gibt es die Kompination vo User-Email und Firma schon? -> kein neuer User
 * -->    sonst User neu anlegen..
 * in einem zweiten Schritt dann das Team mit AccessCode oder ein neues Team erfassen
 */
class AddCompanyFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_company_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Spinner mit Firmennamen aus der DB füllen

        buAddCompany.setOnClickListener() {
            if(validation()) {
                it.hideKeyboard()
                trySaveNewComp()
               // Log.i("debug", etNewCompany.error.toString())
               // findNavController().navigate(R.id.action_addCompanyFragment_to_welcome_fragment)
            }
        }

        buAddCompanyGoBack.setOnClickListener(){
             findNavController().navigate(R.id.action_addCompanyFragment_to_welcome_fragment)
        }
    }

    /** In diesem Fragment soll das Optionsmenu nicht angzeigt werden.
    * Unter onCreateView ist zusätzlich setHasOptionsMenu(true) anzubringen
    * @param menu
    */
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    fun validation() : Boolean {
        // Company
        if(etNewCompany.getText().toString().trim().isEmpty()){
            etNewCompany.error = "Firmennamen eingeben"
            etNewCompany.requestFocus()
            return false
        }
        return true
    }

    fun trySaveNewComp() {
        var url = "https://cloud4b.space/caketowork/addnewcompany.php"
        url += "?CompName=" + etNewCompany.text.toString().trim()
        Log.i("Debug", "newCompanyIsValid")
        val requestQueue = Volley.newRequestQueue(activity)
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.i("Response from URL", response)
                if(response.equals("NOK")) {
                    etNewCompany.error = "diese Firma existiert schon"
                } else {
                    val toast = Toast.makeText(activity, "Firma wurde erfolgreich hinzugefügt!", Toast.LENGTH_LONG)
                    toast.show()
                    buAddCompany.visibility = View.GONE
                    buAddCompanyGoBack.visibility = View.VISIBLE
                    etNewCompany.isEnabled = false

                }
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

}