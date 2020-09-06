package space.cloud4b.ctw

import android.app.PendingIntent.getActivity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var organisationsList = ArrayList<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val preferences = getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {

            R.id.action_help -> {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_global_websiteOneFragment)
                true
            }
            R.id.action_dashboard -> {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_global_dashboard_fragment)
                true
            }
            R.id.action_userinfo -> {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_global_userinfoFragment)
                true
            }
            R.id.action_invite -> {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_global_emailFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }





}