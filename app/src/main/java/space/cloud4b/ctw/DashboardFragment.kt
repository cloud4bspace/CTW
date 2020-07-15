package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.entry_cell.view.*
import kotlinx.android.synthetic.main.entrylist_fragment.*
import kotlinx.android.synthetic.main.register_fragment.*
import space.cloud4b.ctw.model.Cakeboard
import space.cloud4b.ctw.services.CakeboardAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DashboardFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        println("Dashboard Fregment onCreateView")
        // Inflate the layout for this fragment
       setNextEntry()


        return inflater.inflate(R.layout.dashboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        view.findViewById<Button>(R.id.bu_back).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/

        llWatsNext.setOnClickListener() {
            findNavController().navigate(R.id.action_dashboard_fragment_to_additemFragment)
        }
        buListView.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_fragment_to_itemslistFragment)
        }
        buEntryListView.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_fragment_to_entrylistFragment)
        }
        buWebOne.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_fragment_to_websiteOneFragment)
        }
       // tvNextEvent.text = "17. Juni 2020"
        tvNextEvent.setOnClickListener {
            val text = "Hello toast!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(activity,text,  duration)
            toast.show()
        }
    }

    fun setNextEntry() {
        var url = "https://cloud4b.space/caketowork/ctwnextentry.php"
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        var teamAccessCode = preferences.getString("TeamAccessCode", "")
        val requestQueue = Volley.newRequestQueue(activity)
        url += "?TAC=$teamAccessCode"
        // define a request
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                println("Response: $response")
                var responseList = response.split("|")
                var date = LocalDate.parse(responseList[0])
                tvNextEvent.text = date.format(
                    DateTimeFormatter.ofLocalizedDate(
                        FormatStyle.LONG))
                tvVonWem.text = responseList[1]
                tvWarum.text = responseList[2]
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }
}