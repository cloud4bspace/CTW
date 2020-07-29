package space.cloud4b.ctw

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.additem_steptwo_fragment.*
import kotlinx.android.synthetic.main.register_stepone_fragment.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.image

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AdditemStepTwoFragment : Fragment() {
    val args: AdditemStepTwoFragmentArgs by navArgs()
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
        var newEntryArray = args.newEntryArray
        buAddItemGotoStepThree.setOnClickListener {
            if(validation()) {
                newEntryArray[3] = etInfos.text.toString().trim()
                val action = AdditemStepTwoFragmentDirections.actionAdditemStepTwoFragmentToAdditemStepThreeFragment(newEntryArray)
                findNavController().navigate(action)
            }
        }

        // alle ImageButtons abdunkeln
        for(i in 0 until glReasonIcns.childCount) {
            var imageButton : ImageButton = glReasonIcns.getChildAt(i) as ImageButton

            imageButton.setColorFilter(R.color.black,android.graphics.PorterDuff.Mode.MULTIPLY);
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

                    newEntryArray[2] = immageButton.contentDescription.toString()
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

    fun validation() : Boolean {
        // Username
        if(tvReason.getText().toString().trim().isEmpty()){
            tvReason.error = "Bitte eine Kachel auswählen"
            return false
        }

        return true
    }

}


