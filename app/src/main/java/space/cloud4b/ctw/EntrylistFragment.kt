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
import space.cloud4b.ctw.model.Cakeboard
import space.cloud4b.ctw.model.CakeboardEntry
import space.cloud4b.ctw.model.ChatMessage
import space.cloud4b.ctw.services.CakeboardAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EntrylistFragment : Fragment() {
    var entryItemArray = Array<String>(13){"9999"}

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
            var adapter = parent.getAdapter()
            var cakeboardEntry : CakeboardEntry = adapter.getItem(position) as CakeboardEntry
            // TODO ich muss das hier casten damit ich auf der ChatMEssage-Klasse die methoden aufrufen kann!
            // val toast = Toast.makeText(activity, selectedEntry.toString(), duration)
            val toast = Toast.makeText(activity, "öffne Eintrag # ${cakeboardEntry.ListId}", duration)
            toast.show()
            entryItemArray[0] = cakeboardEntry.ListId
            entryItemArray[1] = cakeboardEntry.ListDate
            entryItemArray[2] = cakeboardEntry.ListDaytime
            entryItemArray[3] = cakeboardEntry.ListReason
            entryItemArray[4] = cakeboardEntry.ListDescription
            entryItemArray[5] = cakeboardEntry.ListFoodAndBev
            entryItemArray[6] = cakeboardEntry.MemberName
            entryItemArray[7] = cakeboardEntry.MemberAlias
            entryItemArray[8] = cakeboardEntry.MemberAvatar
            entryItemArray[9] = cakeboardEntry.ListAngemeldet
            entryItemArray[10] = cakeboardEntry.ListAbgemeldet
            entryItemArray[11] = cakeboardEntry.ListUnentschlossen
            entryItemArray[12] = cakeboardEntry.MemberEmail

            val action = EntrylistFragmentDirections.actionEntrylistFragmentToEntryItemFragment(entryItemArray)

            findNavController().navigate(action)

        }
      //  pbProgressBarI.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        lvCakeboardlist.invalidate()

    }



}


