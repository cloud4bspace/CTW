package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.welcome_fragment.*


/**
 * Das Welcome Fragment dient als Einstiegspunkt, wenn der User noch nicht registriert ist.
 * Wenn der User bereits registriert ist, wird er zum Dashboard weitergeleitet.
 */
class WelcomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        println("Welcome Fragment onCreateView")
        setHasOptionsMenu(true)
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
        ibWelcomeNewTeam.setOnClickListener()
        {
            findNavController().navigate(R.id.action_welcome_fragment_to_addTeamFragment)
        }

        val preferences = this.requireActivity().getSharedPreferences(
            "USR_INFO", Context.MODE_PRIVATE)
        if(preferences.getString("UserStatus", "") != "OK") {
           //
        } else {
            findNavController().navigate(R.id.action_WelcomeFragment_to_DashboardFragment)
        }
    }

    /**
     * In diesem Fragment soll das Optionsmenu nicht angzeigt werden.
     * Unter onCreateView ist zusätzlich setHasOptionsMenu(true) anzubringen
     * @param menu
     */
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }
}