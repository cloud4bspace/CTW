package space.cloud4b.ctw

import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.additem_fragment.*
import kotlinx.android.synthetic.main.additem_steptwo_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.register_fragment.*
import org.jetbrains.anko.childrenRecursiveSequence
import org.jetbrains.anko.forEachChild
import org.jetbrains.anko.forEachChildWithIndex
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AdditemStepTwoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Register Fragment onCreateView")


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.additem_steptwo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buAddItemGotoStepThree.setOnClickListener {
            findNavController().navigate(R.id.action_additemStepTwoFragment_to_additemStepThreeFragment)
        }

        // alle ImageButtons abdunkeln
        for(i in 0 until glReasonIcns.childCount) {
            var immageButton : ImageButton = glReasonIcns.getChildAt(i) as ImageButton
            immageButton.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        // für alle ImageButtons einen OnClick-Listener setzen
        for(i in 0 until glReasonIcns.childCount) {
            var immageButton : ImageButton = glReasonIcns.getChildAt(i) as ImageButton
            immageButton.setOnClickListener() {
                // Toggle zwischen ausgewählt und nicht ausgewählt
                if(tvReason.text.toString().equals(immageButton.contentDescription))  {
                    immageButton.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                    tvReason.setText("")
                } else {
                    immageButton.clearColorFilter()
                    tvReason.setText(immageButton.contentDescription)
                    for(j in 0 until glReasonIcns.childCount) {
                        if(glReasonIcns.getChildAt(j) != immageButton) {
                            var otherImageButton : ImageButton = glReasonIcns.getChildAt(j) as ImageButton
                            otherImageButton.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY)
                        }
                    }
                }
            }
        }
    }



}


