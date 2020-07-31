package space.cloud4b.ctw

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import kotlinx.android.synthetic.main.userinfo_fragment.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UserinfoFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.userinfo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        etProfileUserName.setText(preferences.getString("Username", ""))
        etProfileUserEmail.setText(preferences.getString("UserEmail", ""))
        etProfileAlias.setText(preferences.getString("UserAlias", ""))
        etProfileUserOrg.setText(preferences.getString("UserOrg", ""))
        etProfileUserTeam.setText(preferences.getString("UserTeam", ""))
        etProfileTAC.setText(preferences.getString("TeamAccessCode", ""))
        var avatar = preferences.getString("UserAvatar", "icn_male")

        etProfileUserName.setCompoundDrawablesWithIntrinsicBounds(this.resources.getIdentifier("space.cloud4b.ctw:drawable/rf_$avatar",null,null), 0, 0, 0)
         buShareDialog.setOnClickListener() {
             findNavController().navigate(R.id.action_userinfoFragment_to_emailFragment)
         }
         buReset.setOnClickListener() {
             // TODO aus den Datenbanken muss auch alles gelöscht werden (z.B. An-/Abmeldungen für Termine)
             val builder = AlertDialog.Builder(activity)
             builder.setTitle("Profil zurücksetzen")
             builder.setMessage("Soll Dein Profil wirklich gelöscht werden?")

             builder.setPositiveButton("Ja") { dialog, which ->
                 Toast.makeText(activity,
                     "Ja", Toast.LENGTH_SHORT).show()
                 val editor = this.requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE).edit()
                 editor.putString("UserStatus", "DEL")
                 editor.apply()
                 // restart App (fab will be invisible after
                 val i: Intent? = requireActivity().getPackageManager()
                     .getLaunchIntentForPackage(requireActivity().getPackageName())
                 i?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                 startActivity(i)
                // findNavController().navigate(R.id.action_userinfoFragment_to_welcome_fragment)
             }

             builder.setNegativeButton("Nein") { dialog, which ->
                 Toast.makeText(activity,
                     "Nein", Toast.LENGTH_SHORT).show()
             }

             builder.show()

         }

    }


}