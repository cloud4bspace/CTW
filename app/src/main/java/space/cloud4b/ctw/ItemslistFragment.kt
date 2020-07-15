package space.cloud4b.ctw

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.itemslist_fragment.*
import kotlinx.android.synthetic.main.welcome_fragment.*
import org.json.JSONObject
import space.cloud4b.ctw.services.CustomAdapterOne
import java.net.URL

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

   fun onResume(view: View, savedInstanceState: Bundle?) {
       super.onResume()
       fetchJsonData().execute()
   }

    inner class fetchJsonData() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()

        }

        override fun doInBackground(vararg params: String?): String? {
            // TODO die Selektion muss dynamisch passieren..

          /*  return URL("https://cloud4b.space/caketowork/jsonlist03.php?TAC=1234567890")
                .readText(Charsets.UTF_8)*/
            val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
            println("Read Pref TeamAccessCode = " + preferences.getString("TeamAccessCode", ""))
            return URL("https://cloud4b.space/caketowork/jsonlist03.php?TAC=" + preferences.getString("TeamAccessCode", ""))
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


