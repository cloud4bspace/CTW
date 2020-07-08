package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.register_fragment.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UserinfoFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("Register Fragment onCreateView")
        bu_saveregister.setOnClickListener {
            val editor = this.requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE).edit()
            editor.putString("Username", etUsername.text.toString().trim())
            editor.putString("UserEmail", etUserEmail.text.toString().trim())
            editor.putString("UserOrg", spOrg.selectedItem.toString())
            editor.putString("UserTeam", spTeam.selectedItem.toString())
            editor.apply()
        }

    }
}