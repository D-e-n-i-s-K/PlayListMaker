package com.practium.playlistmaker

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backToMainMenu = findViewById<TextView>(R.id.activity_setting_header)
        val shareAppButton = findViewById<Button>(R.id.activity_settings_share_app_button)
        val sendEMailToSupportButton = findViewById<Button>(R.id.activity_settings_mail_to_support_button)
        val usersAgreementButton = findViewById<Button>(R.id.activity_settings_user_agree_button)
        val themeSwitcher = findViewById<SwitchCompat>(R.id.themeSwitcher)

        themeSwitcher.isChecked = (applicationContext as App).darkTheme

        themeSwitcher.setOnCheckedChangeListener {  switcher, checked ->
            (applicationContext as App).switchTheme(checked)

            val userPreferences = SharedPreferencesConstants.USER_PREFERENCES
            val sharedPreferences = getSharedPreferences(userPreferences, MODE_PRIVATE)
            sharedPreferences.edit()
                .putBoolean(SharedPreferencesConstants.DARK_THEME_KEY, checked)
                .apply()

        }

        backToMainMenu.setOnClickListener {
            finish()
        }

        shareAppButton.setOnClickListener {

            val shareAppDisplayIntent = Intent(Intent.ACTION_SEND)
            shareAppDisplayIntent.type = "text/plain"
            val androidDeveloperUrl = getString(R.string.android_developer_url)
            shareAppDisplayIntent.putExtra(Intent.EXTRA_TEXT, androidDeveloperUrl)

            try {
                startActivity(shareAppDisplayIntent)
            } catch (e: ActivityNotFoundException) {
                val text = getString(R.string.startActivity_shareAppDisplayIntent_catch_message)
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        }

        sendEMailToSupportButton.setOnClickListener{

            val testMailAddress = getString(R.string.test_mail_address)

            val mailToAddress = arrayOf(testMailAddress)
            val mailSubject = getString(R.string.support_mail_subject)
            val mailText = getString(R.string.support_mail_text)

            val sendEmailToSupportIntent = Intent(Intent.ACTION_SENDTO)
            sendEmailToSupportIntent.data = Uri.parse("mailto:")
            sendEmailToSupportIntent.putExtra(Intent.EXTRA_EMAIL, mailToAddress)
            sendEmailToSupportIntent.putExtra(Intent.EXTRA_TEXT, mailText)
            sendEmailToSupportIntent.putExtra(Intent.EXTRA_SUBJECT, mailSubject)

            try {
                startActivity(sendEmailToSupportIntent)
            } catch (e: ActivityNotFoundException) {
                val text = getString(R.string.startActivity_sendEmailToSupportIntent_catch_message)
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

        }

        usersAgreementButton.setOnClickListener {
            val urlAgreement = getString(R.string.user_agreement_url)
            val  usersAgreementIntent = Intent(Intent.ACTION_VIEW)
            usersAgreementIntent.data = Uri.parse(urlAgreement)


            try {
                startActivity(usersAgreementIntent)
            } catch (e: ActivityNotFoundException) {
                val text = getString(R.string.startActivity_usersAgreementIntent_catch_message)
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

        }


    }
}