package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.additem_stepfour_fragment.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AdditemStepFourFragment : Fragment() {
    val args: AdditemStepFourFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Register Fragment onCreateView")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.additem_stepfour_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var newEntryArray = args.newEntryArray

        tvDatumTageszeit.text = newEntryArray[0] + " | " + newEntryArray[1]
        tvGrund.text = newEntryArray[2]
        tvFoodAndBeverages.text = newEntryArray[3]
        buAddItemSave.setOnClickListener {
            postNewEntry(newEntryArray)
        }



    }

    fun postNewEntry(newEntryArray: Array<String>) {
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        val tac = preferences.getString("TeamAccessCode", "")
        val userEmail = preferences.getString("UserEmail", "")
        var url = "https://cloud4b.space/caketowork/addnewitem.php"
        url += "?TAC=$tac"
        url += "&UserEmail=$userEmail"
        url += "&Date=${newEntryArray[0]}"
        url += "&Time=${newEntryArray[1]}"
        url += "&Reason=${newEntryArray[2]}"
        url += "&Beverages=${newEntryArray[3]}"
        Log.i("SQL", url)

        val requestQueue = Volley.newRequestQueue(activity)
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
               Log.i("Response", response)
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("******VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)

    }



}


