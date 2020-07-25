package space.cloud4b.ctw

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.email_fragment.*
import kotlinx.android.synthetic.main.userinfo_fragment.*

class EmailFragment : Fragment() {

    companion object {
        fun newInstance() = EmailFragment()
    }

    private lateinit var viewModel: EmailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.email_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EmailViewModel::class.java)
        // TODO: Use the ViewModel
        val preferences = requireActivity().getSharedPreferences("USR_INFO", Context.MODE_PRIVATE)
        var message = "Liebe/r Kolleg/in"
        message += "\n\nMit dem Zugangs-Code \"" + preferences.getString("TeamAccessCode", "").toString()
        message += "\" <b>kannst Du Dich für unser Team \"" + preferences.getString("UserTeam", "").toString() + "\" anmelden."
        message += "\n\nLiebe Grüsse " + preferences.getString("Username", "").toString() +
                " (Alias " + preferences.getString("UserAlias", "").toString() + ")"
        etEmailMessage.setText(message)
        etEmailSubject.setText("Einladung für Cake To Work (von " + preferences.getString("UserAlias", "").toString() + ")")

        buShareTAC.setOnClickListener() {
            val recipient = etEmailRecipient.text.toString().trim()
            val subject = etEmailSubject.text.toString().trim()
            val message = etEmailMessage.text.toString().trim()
            sendEmail(recipient, subject, message)
        }
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try{
            startActivity((Intent.createChooser(mIntent, "Choose EMail Client")))
        } catch(e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
        }
    }

}