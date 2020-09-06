package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.teamlist_fragment.*
import space.cloud4b.ctw.model.Team
import space.cloud4b.ctw.services.TeamlistAdapter


/**
 * Fragment zeigt eine Liste aller Teammitglieder
 */
class TeamlistFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TAC aus den Shared Preferences holen
        val preferences =
            requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        var teamAccessCode = preferences.getString("TeamAccessCode", "")

        // url zur Mitgliederliste im JSON-Format
        var url = "https://cloud4b.space/caketowork/ctwteammembers.php"
        url += "?TAC=$teamAccessCode"
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val entry : Team? = Klaxon().parse<Team>(response)
                val adapter = activity?.let {
                    TeamlistAdapter(entry?.teamlist!!, it) }
                lvTeamlist.adapter = adapter
                pbProgressBarTLF.visibility = View.GONE
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(activity,
                    "Teammitglieder werden aufgelistet", duration)
                toast.show()
            },
            Response.ErrorListener {
                it.message?.let { it1 ->
                    Log.e("VOLLEYERROR: Teamliste konnte nicht geladen werden", it1) }
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(activity, "da hat etwas nicht geklappt",
                    duration)
                toast.show()
            })

        // RequestQueue erstellen und Aufruf hinzufügen
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(request)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.teamlist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}


