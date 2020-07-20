package space.cloud4b.ctw.services

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.entry_cell.view.*
import kotlinx.android.synthetic.main.entry_cell.view.tvInfos
import kotlinx.android.synthetic.main.teammember_cell.view.*
import org.jetbrains.anko.coroutines.experimental.asReference
import space.cloud4b.ctw.R
import space.cloud4b.ctw.model.CakeboardEntry
import space.cloud4b.ctw.model.TeamMember
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.absoluteValue

class TeamlistAdapter(var entries: MutableList<TeamMember>, var context: Context) : BaseAdapter() {

    var layoutInflater : LayoutInflater
    init {
        layoutInflater = LayoutInflater.from(context)
    }
    override fun getView(index: Int, oldView: View?, viewGroup: ViewGroup?): View {
        var view : View
        if(oldView == null) {
            view = layoutInflater.inflate(R.layout.teammember_cell, null)
        } else {
            view = oldView
        }
        val entry:TeamMember = getItem(index)
        view.tvMemberName.text = entry.MemberName
        view.tvMemberEmail.text = entry.MemberEmail

        var icnName = IconMapper().getIcnName("2")
        view.ivMemberIcn.setImageResource(context.getResources().getIdentifier("space.cloud4b.ctw:drawable/${IconMapper().getIcnName(entry.MemberSex)}",null,null))
        val lp = LinearLayout.LayoutParams(60, 60)
        lp.setMargins(10, 10, 10, 10)
        view.ivMemberIcn.setLayoutParams(lp)

        view.tvAlias.text = entry.MemberAlias

        return view
    }

    override fun getItem(index: Int): TeamMember {
        return entries.get(index)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return entries.size
    }

    fun getUserName() : MutableList<TeamMember> {
        return entries
    }
}