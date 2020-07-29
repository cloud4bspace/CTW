package space.cloud4b.ctw.services

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.chatlist_cell.view.*
import kotlinx.android.synthetic.main.entry_cell.view.*
import org.jetbrains.anko.coroutines.experimental.asReference
import space.cloud4b.ctw.R

import space.cloud4b.ctw.model.ChatMessage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.absoluteValue

class ChatlistAdapter(var entries: MutableList<ChatMessage>, var context: Context) : BaseAdapter() {

    var layoutInflater : LayoutInflater
    init {
        layoutInflater = LayoutInflater.from(context)
    }
    override fun getView(index: Int, oldView: View?, viewGroup: ViewGroup?): View {
        var view : View
        view = layoutInflater.inflate(R.layout.chatlist_cell, null)
     /*   if(oldView == null) {
            view = layoutInflater.inflate(R.layout.chatlist_cell, null)
        } else {
            view = oldView
        }*/
        val entry:ChatMessage = getItem(index)
        view.tvChatcellAlias.setText(entry.MemberAlias)
        view.tvChatcellMessage.setText(entry.ChatMessage)
        view.tvMessageId.setText("#" + entry.ChatID)
        view.tvChatInfos.setText(entry.ChatTimestamp)

        var icnName = entry.MemberAvatar
        view.ivChatcellAvatar.setImageResource(context.getResources().getIdentifier("space.cloud4b.ctw:drawable/rf_$icnName",null,null))


        return view
    }

    override fun getItem(index: Int): ChatMessage {
        return entries.get(index)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return entries.size
    }

    fun getUserName() : MutableList<ChatMessage> {
        return entries
    }
}