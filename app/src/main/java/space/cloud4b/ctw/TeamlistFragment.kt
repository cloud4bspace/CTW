package space.cloud4b.ctw

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.entrylist_fragment.*
import kotlinx.android.synthetic.main.itemslist_fragment.*
import kotlinx.android.synthetic.main.teamlist_fragment.*
import kotlinx.android.synthetic.main.teamlist_fragment.pbProgressBar
import kotlinx.android.synthetic.main.welcome_fragment.*
import org.json.JSONObject
import space.cloud4b.ctw.model.Cakeboard
import space.cloud4b.ctw.model.Team
import space.cloud4b.ctw.services.CakeboardAdapter
import space.cloud4b.ctw.services.CustomAdapterOne
import space.cloud4b.ctw.services.TeamlistAdapter
import java.net.URL

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TeamlistFragment : Fragment() {

    var url = "https://cloud4b.space/caketowork/ctwteammembers.php"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        var teamAccessCode = preferences.getString("TeamAccessCode", "")
        val requestQueue = Volley.newRequestQueue(activity)
        url += "?TAC=$teamAccessCode"
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val entry : Team? = Klaxon().parse<Team>(response)
                val adapter = activity?.let { TeamlistAdapter(entry?.teamlist!!, it) }
                lvTeamlist.adapter = adapter
                pbProgressBar.visibility = View.GONE
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.teamlist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lvTeamlist.setOnItemClickListener { parent , view, position, id ->

            val selectedEntry = lvTeamlist.getItemAtPosition(position)
            val duration = Toast.LENGTH_SHORT

            // val toast = Toast.makeText(activity,selectedEntry.toString(),  duration)
            val toast = Toast.makeText(activity, selectedEntry.toString(), duration)
            toast.show()

        }
        //  pbProgressBarI.visibility = View.GONE
    }

}


