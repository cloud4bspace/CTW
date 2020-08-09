package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.additem_steptwo_fragment.*
import kotlinx.android.synthetic.main.chatlist_fragment.*
import kotlinx.android.synthetic.main.entrylist_fragment.*
import kotlinx.android.synthetic.main.entrylist_fragment.pbProgressBar
import kotlinx.android.synthetic.main.participantslist_fragment.*
import space.cloud4b.ctw.model.Cakeboard
import space.cloud4b.ctw.model.CakeboardEntry
import space.cloud4b.ctw.model.ChatMessage
import space.cloud4b.ctw.model.Doodle
import space.cloud4b.ctw.services.CakeboardAdapter
import space.cloud4b.ctw.services.DoodleAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ParticipantslistFragment : Fragment() {
    var entryItemArray = Array<String>(13){"9999"}


   // https://cloud4b.space/caketowork/ctwdoodle.php?ItemId=17

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val requestQueue = Volley.newRequestQueue(activity)
        var url = "https://cloud4b.space/caketowork/ctwdoodle.php"
        url += "?ItemId=17" // TODO das muss dynamisch sein dann..
        // define a request
        val request = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                val entry : Doodle? = Klaxon().parse<Doodle>(response)
                val adapter = activity?.let { DoodleAdapter(entry?.doodle!!, it) }
                lvParticipantsList.adapter = adapter
               // pbProgressBar.visibility = View.GONE // TODO progressbar wäre schön hier
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.participantslist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //  pbProgressBarI.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()


    }



}


