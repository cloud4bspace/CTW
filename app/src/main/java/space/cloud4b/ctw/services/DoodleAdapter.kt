package space.cloud4b.ctw.services

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.entry_cell.view.*
import kotlinx.android.synthetic.main.entry_cell.view.tvEntryDate
import kotlinx.android.synthetic.main.participant_cell.view.*
import org.jetbrains.anko.coroutines.experimental.asReference
import space.cloud4b.ctw.R
import space.cloud4b.ctw.model.DoodleResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.absoluteValue

/**
 * Die Klasse stellt einen Adapter zur Verfügung, um die ListView (participantslist_fragment.xml)
 * mit Daten zu befüllen.
 *
 * @author Serge Kaulitz & Bernhard Kämpf
 */
class DoodleAdapter(var entries: MutableList<DoodleResponse>, var context: Context) : BaseAdapter() {

    var layoutInflater : LayoutInflater
    init {
        layoutInflater = LayoutInflater.from(context)
    }
    override fun getView(index: Int, oldView: View?, viewGroup: ViewGroup?): View {
        var view : View
        if(oldView == null) {
            view = layoutInflater.inflate(R.layout.participant_cell, null)
        } else {
            view = oldView
        }

        val entry : DoodleResponse = getItem(index)
        view.tvParticipantName.text = entry.MemberName
        view.ivParticipantAvatar.setImageResource(context.getResources().getIdentifier(
            "space.cloud4b.ctw:drawable/${entry.MemberAvatar}",null,null))
        val lp = LinearLayout.LayoutParams(60, 60)
        lp.setMargins(10, 10, 10, 10)
        view.ivParticipantAvatar.setLayoutParams(lp)

        var icnName = "icn_good"
        if(entry.RegistrationAngemeldet == "1") {
            icnName = "icn_good"
        }
        if(entry.RegistrationAbgemeldet == "1") {
            icnName = "icn_bad"
        }

        view.ivParticipantStatus.setImageResource(context.getResources().getIdentifier(
            "space.cloud4b.ctw:drawable/$icnName",null,null))
        view.ivParticipantStatus.setLayoutParams(lp)
        return view
    }

    override fun getItem(index: Int): DoodleResponse {
        return entries.get(index)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return entries.size
    }

    fun getUserName() : MutableList<DoodleResponse> {
        return entries
    }
}