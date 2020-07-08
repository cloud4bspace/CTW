package space.cloud4b.ctw.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import space.cloud4b.ctw.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class CustomAdapterOne(private val context: Context,
                       private val dataList: ArrayList<HashMap<String, String>>) : BaseAdapter() {
    private var mContext //instance variable
            : Context? = context

    private val inflater: LayoutInflater = this.mContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int { return dataList.size }
    override fun getItem(position: Int): Int { return position }
    override fun getItemId(position: Int): Long { return position.toLong() }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var dataitem = dataList[position]

        val rowView = inflater.inflate(R.layout.itemslist_row, parent, false)
        rowView.findViewById<TextView>(R.id.row_ctwwho).text = dataitem.get("membername")
        rowView.findViewById<TextView>(R.id.row_ctworg).text = dataitem.get("compbezeichnung")
        rowView.findViewById<TextView>(R.id.row_ctwreason).text = dataitem.get("listreason")
        var date = LocalDate.parse(dataitem.get("listdate"))
        rowView.findViewById<TextView>(R.id.row_ctwdate).text = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
        if(dataitem.get("memberphoto")!!.isNotEmpty())
            Picasso.get()
                .load(dataitem.get("memberphoto"))
                .resize(50, 50)
                .centerCrop()
                .into(rowView.findViewById<ImageView>(R.id.row_ctwimage))
        else
        // Display an avatar-image on image view from resource when no image in database
            rowView.findViewById<ImageView>(R.id.row_ctwimage).setImageResource(R.drawable.default_profile_pic_75)
        return rowView

    }
}