package space.cloud4b.ctw

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    var organisationsList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            //TODO das Fragment wird über die aktuelle Ansicht gelegt.... unbrauchbar so..
           val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, AdditemFragment())
            transaction.addToBackStack("test")
            transaction.commit()
            //  findNavController(view.id).navigate(R.id.action_FloatingActionButton_to_AddEntryFragement)

        }

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
            R.id.action_settings -> {
                Log.i("MENU", "Settings ausgewählt")

                true}
            R.id.action_additem -> {
                val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.activityContentMain, AdditemFragment())
                transaction.addToBackStack(null)
                transaction.commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }





}