package space.cloud4b.ctw

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.itemslist_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import space.cloud4b.ctw.services.CustomAdapterOne
import java.net.URL
import java.util.concurrent.Executors

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ItemslistFragment : Fragment() {
    var dataList = ArrayList<HashMap<String, String>>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        println("Register Fragment onCreateView: ItemslistFragment")
        // Inflate the layout for this fragment
        fetchJsonData().execute()
        return inflater.inflate(R.layout.itemslist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      /*  view.findViewById<Button>(R.id.bu_backtodashboard).setOnClickListener {
            findNavController().navigate(R.id.action_RegisterFragment_to_DashboardFragment)
        }*/
    }

    inner class fetchJsonData() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()

        }

        override fun doInBackground(vararg params: String?): String? {
            // TODO die Selektion muss dynamisch passieren..

          /*  return URL("https://cloud4b.space/caketowork/jsonlist03.php?TAC=1234567890")
                .readText(Charsets.UTF_8)*/
            return URL("https://cloud4b.space/caketowork/jsonlist03.php")
                .readText(Charsets.UTF_8)
        }

        // das hier gehört wohl zu URL-Auslesen
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)



            val jsonObj = JSONObject(result)
            val usersArr = jsonObj.getJSONArray("eintraege")
            println(usersArr.length())
            for (i in 0 until usersArr.length()) {
                val singleUser = usersArr.getJSONObject(i)

                val map = HashMap<String, String>()
                map["compbezeichnung"] = singleUser.getString("CompBezeichnung")
                map["membername"] = singleUser.getString("MemberName")
                map["listreason"] = singleUser.getString("ListReason")
                map["memberphoto"] = singleUser.getString("MemberPhoto")
                map["listdate"] = singleUser.getString("ListDate")
                dataList.add(map)
            }

            lvItemlist.adapter = activity?.let { CustomAdapterOne(it, dataList) }
        }
    }

}