package space.cloud4b.ctw

import android.app.DownloadManager
import android.content.ClipData.newIntent
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility.MODE_OUT
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.entrylist_fragment.*
import kotlinx.android.synthetic.main.itemslist_fragment.*
import kotlinx.android.synthetic.main.welcome_fragment.*
import org.jetbrains.anko.coroutines.experimental.asReference
import org.json.JSONObject
import space.cloud4b.ctw.model.Cakeboard
import space.cloud4b.ctw.model.CakeboardEntry
import space.cloud4b.ctw.model.User
import space.cloud4b.ctw.services.CakeboardAdapter
import space.cloud4b.ctw.services.CustomAdapterOne
import java.net.URL

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EntrylistFragment : Fragment() {

    var url = "https://cloud4b.space/caketowork/ctwboard.php"
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        var teamAccessCode = preferences.getString("TeamAccessCode", "")
        val requestQueue = Volley.newRequestQueue(activity)
        url += "?TAC=$teamAccessCode"
        // define a request
        val request = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                val entry : Cakeboard? = Klaxon().parse<Cakeboard>(response)
                val adapter = activity?.let {CakeboardAdapter(entry?.cakeboard!!, it) }
                lvCakeboardlist.adapter = adapter
                pbProgressBar.visibility = View.GONE
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.entrylist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lvCakeboardlist.setOnItemClickListener { parent , view, position, id ->

            val selectedEntry = lvCakeboardlist.getItemAtPosition(position)



            val duration = Toast.LENGTH_SHORT

           // val toast = Toast.makeText(activity,selectedEntry.toString(),  duration)
            val toast = Toast.makeText(activity, selectedEntry.toString(), duration)
            toast.show()

        }
      //  pbProgressBarI.visibility = View.GONE
    }



}


