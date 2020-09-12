package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.chatlist_fragment.*
import kotlinx.android.synthetic.main.entrylist_fragment.pbProgressBar
import space.cloud4b.ctw.model.Chat
import space.cloud4b.ctw.model.ChatMessage
import space.cloud4b.ctw.services.ChatlistAdapter
import space.cloud4b.ctw.services.OnSwipeTouchListener
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Bereitstellung der Chatlist (wurde nicht realisiert)
 */
class ChatlistFragment : Fragment() {
    var anzMessages : Int = 0
    var url = "https://cloud4b.space/caketowork/ctwChatJson.php"


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val preferences = requireActivity().getSharedPreferences(
            "USR_INFO", Context.MODE_PRIVATE)
        var teamAccessCode = preferences.getString("TeamAccessCode", "")
        val requestQueue = Volley.newRequestQueue(activity)
        url += "?TAC=$teamAccessCode"
        // define a request
        val request = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                val entry : Chat? = Klaxon().parse<Chat>(response)
                val adapter = activity?.let { ChatlistAdapter(entry?.chatmessages!!, it) }
                lvChatlist.adapter = adapter
                anzMessages = lvChatlist.adapter.getCount()
                Log.i("Msgs", anzMessages.toString())
                pbProgressBar.visibility = View.GONE
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.chatlist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivSendMessage.setOnClickListener() {
            it.hideKeyboard()
            if(validation()) {
                postChatMessage()
            }

        }
        etChatMessageInput.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View?, keyCode: Int, keyevent: KeyEvent): Boolean {
                //If the keyevent is a key-down event on the "enter" button
                return if (keyevent.action === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    view?.hideKeyboard()
                    if(validation()) {
                         postChatMessage()
                    }
                    true
                } else false
            }
        })
        context?.let {
            lvChatlist.setOnTouchListener(object: OnSwipeTouchListener(it) {
                override fun onSwipeLeft() {
                    Log.i("onSwipeLeft", it.toString())
                    // TODO das nützt so nichts...
                }

                override fun onSwipeRight() {
                    Log.i("onSwipeRight", ">es")
                }
            })
        }
        lvChatlist.setOnItemClickListener { parent , view, position, id ->

            val selectedEntry = lvChatlist.getItemAtPosition(position)
            val duration = Toast.LENGTH_SHORT
           // val toast = Toast.makeText(activity,selectedEntry.toString(),  duration)
            var adapter = parent.getAdapter()
            var chatMessage : ChatMessage = adapter.getItem(position) as ChatMessage
            // TODO ich muss das hier casten damit ich auf der ChatMEssage-Klasse die methoden aufrufen kann!
           // val toast = Toast.makeText(activity, selectedEntry.toString(), duration)
            val toast = Toast.makeText(activity, chatMessage.ChatMessage, duration)
            toast.show()

        }
      //  pbProgressBarI.visibility = View.GONE
    }

    fun postChatMessage() {

        ivSendMessage.setEnabled(false);
        val requestQueue = Volley.newRequestQueue(activity)
        val chatMessage = etChatMessageInput.text.toString().trim()
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        var userEmail = preferences.getString("UserEmail", "")
        var teamAccessCode = preferences.getString("TeamAccessCode", "")
        var url = "https://cloud4b.space/caketowork/addnewchatmessage.php"
        url += "?TAC=$teamAccessCode"
        url += "&UserEmail=$userEmail"
        url += "&ChatMessage=${etChatMessageInput.text.toString().trim()}"
        val request = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                if(response.toInt() > 0) {
                    addObject(response, etChatMessageInput.text.toString().trim())
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(activity, "Message #$response wurde zugestellt", duration)
                    toast.show()
                    etChatMessageInput.setText("")
                } else {
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(activity, "Da hat etwas nicht funkioniert", duration)
                    toast.show()

                }

                ivSendMessage.setEnabled(true)
            },
            Response.ErrorListener {
                it.message?.let { it1 -> Log.e("VOLLEYERROR", it1) }
            })
        //add the call to the request queue
        requestQueue.add(request)
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun addObject(chatID : String, messageText : String) {
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        var userEmail = preferences.getString("UserEmail", "")
        var timeStamp = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
        var adapter : ChatlistAdapter = lvChatlist.adapter as ChatlistAdapter
        adapter.entries.add(ChatMessage(chatID, preferences.getString("UserEmail", "").toString(), preferences.getString("UserName", "").toString(), preferences.getString("UserAlias", "").toString(), preferences.getString("UserAvatar", "").toString(), messageText, timeStamp, preferences.getString("UserAvatar", "").toString()))
        adapter.notifyDataSetChanged()
    }

    fun validation() : Boolean {
        // Username
        if(etChatMessageInput.getText().toString().trim().isEmpty()){
            etChatMessageInput.error = "da muss man voher schon etwas eingeben..."
            etChatMessageInput.requestFocus()
            return false
        } else {
            return true
        }

    }


}




