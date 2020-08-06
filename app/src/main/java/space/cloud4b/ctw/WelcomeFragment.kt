package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.welcome_fragment.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WelcomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        println("Welcome Fragment onCreateView")


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.welcome_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ibWelcomeLogin.setOnClickListener() {
            findNavController().navigate(R.id.action_welcome_fragment_to_registerStepOneFragment)
        }
        ibWelcomeInfo.setOnClickListener() {
            findNavController().navigate(R.id.action_welcome_fragment_to_websiteOneFragment)
        }
        ibWelcomeNewComp.setOnClickListener() {
            findNavController().navigate(R.id.action_welcome_fragment_to_addCompanyFragment)
        }
        val preferences = this.requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        if(preferences.getString("UserStatus", "") != "OK") {
           //
        } else {
            findNavController().navigate(R.id.action_WelcomeFragment_to_DashboardFragment)
        }
    }
}