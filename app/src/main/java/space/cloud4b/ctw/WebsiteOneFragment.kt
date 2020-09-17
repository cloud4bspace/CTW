package space.cloud4b.ctw

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.dashboard_fragment.*

import kotlinx.android.synthetic.main.webone_fragment.*

/**
 * Das WebsiteOneFragment dient der Darstellung einer Webseite mit Informationen und
 * Hilfe zur App
 */
class WebsiteOneFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val preferences = this.requireActivity().getSharedPreferences(
            "USR_INFO", Context.MODE_PRIVATE)
        if(preferences.getString("UserStatus", "") != "OK") {
            setHasOptionsMenu(true)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.webone_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wvWebpageOne.loadUrl("https://cloud4b.space/caketowork/help/")


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