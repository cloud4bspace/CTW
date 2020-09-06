package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.register_stepone_fragment.*


/**
 * Fragment stellt dem User ein Anmeldeformular zur Verfügung (Schritt 1/2)
 */
class RegisterStepOneFragment : Fragment() {

    // Default Icon für Avatar
    var avatar: String = "icn_male"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Optionsmenu soll hier nicht angezeigt werden
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register_stepone_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Request definieren, um den Spinner für die Auswahl der Firma zu befüllen
        activity?.let { fillOrgSpinner(it) }

        // alle Avatar-ImageButtons abdunkeln, ausser dem Ersten
        for (i in 1 until glAvatarSelection.childCount) {
            var imageButton: ImageButton = glAvatarSelection.getChildAt(i) as ImageButton
            imageButton.setColorFilter(R.color.black, android.graphics.PorterDuff.Mode.MULTIPLY)
        }

        // für alle Avatar-ImageButtons einen OnClick-Listener setzen
        for (i in 0 until glAvatarSelection.childCount) {
            var immageButton: ImageButton = glAvatarSelection.getChildAt(i) as ImageButton
            immageButton.setOnClickListener {
                immageButton.clearColorFilter()
                avatar = immageButton.contentDescription.toString()
                etUsername.setCompoundDrawablesWithIntrinsicBounds(
                    this.resources.getIdentifier(
                        "space.cloud4b.ctw:drawable/rf_$avatar",
                        null, null
                    ), 0, 0, 0
                )
                for (j in 0 until glAvatarSelection.childCount) {
                    if (glAvatarSelection.getChildAt(j) != immageButton) {
                        var otherImageButton: ImageButton = glAvatarSelection.getChildAt(j)
                                as ImageButton
                        otherImageButton.setColorFilter(
                            R.color.black,
                            android.graphics.PorterDuff.Mode.MULTIPLY
                        )
                    }
                }
            }
        }

        buRegisterStepTwo.setOnClickListener {
            // User-Eingaben in den SharedPreferences speichern
            if (validation()) {
                val editor =
                    this.requireActivity().getSharedPreferences(
                        "USR_INFO",
                        Context.MODE_PRIVATE
                    ).edit()
                editor.putString("Username", etUsername.text.toString().trim())
                editor.putString("UserEmail", etUserEmail.text.toString().trim())
                editor.putString("UserOrg", spOrg.selectedItem.toString())
                editor.putString("UserAlias", etAlias.text.toString().trim())
                editor.putString("UserAvatar", avatar.trim())
                editor.apply()
                findNavController().navigate(
                    R.id.action_registerStepOneFragment_to_registerStepTwoFragment
                )
            }
        }

        spOrg.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                view.hideKeyboard()
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == "Neue Firma hinzufügen..") {
                    findNavController().navigate(
                        R.id.action_registerStepOneFragment_to_addCompanyFragment
                    )
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    /**
     * In diesem Fragment soll das Optionsmenu nicht angzeigt werden.
     * Unter onCreateView ist zusätzlich setHasOptionsMenu(true) anzubringen
     * @param menu
     */
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }


    fun validation(): Boolean {

        // Username
        if (etUsername.text.toString().trim().isEmpty()) {
            etUsername.error = "Bitte gib Deinen Namen an"
            etUsername.requestFocus()
            return false
        }

        // Alias
        if (etAlias.text.toString().trim().isEmpty()) {
            etAlias.error = "Bitte einen Nickname eingeben"
            etAlias.requestFocus()
            return false
        }

        // E-Mail-Adresse
        if (etUserEmail.text.toString().trim().isEmpty()) {
            etUserEmail.error = "Bitte gib Deine E-Mail-Adresse an"
            etUserEmail.requestFocus()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etUserEmail.text).matches()) {
            etUserEmail.error = "Das ist aber keine gültige E-Mail-Adresse"
            etUserEmail.requestFocus()
            return false
        }
        return true
    }

    private fun fillOrgSpinner(x: Context) {
        // Url für Abruf der Firmenliste als String
        var url = "https://cloud4b.space/caketowork/compstringlist.php"

        // Request definieren
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                // Firmenliste mit zwei Einträgen ergänzen
                var orgSpinnerString = "Firma wählen..|Neue Firma hinzufügen..|$response"

                // String auftrennen und ArrayList befüllen
                var orgSpinnerList = orgSpinnerString.split("|")
                        as ArrayList<String>

                // Den Spinner mit den Einträge füllen
                spOrg.adapter = ArrayAdapter<String>( x, android.R.layout.simple_spinner_item,
                    orgSpinnerList)
            },
            Response.ErrorListener {
                it.message?.let { it1 ->
                    Log.e(
                        "VOLLEYERROR: RegisterStepOne-fillOrgSpinner",
                        it1
                    )
                }
            })

        // RequestQueue definieren und Request hinzufügen
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(request)
    }

    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

}