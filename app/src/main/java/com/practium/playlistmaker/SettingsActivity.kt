package com.practium.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backToMainMenu = findViewById<TextView>(R.id.activity_setting_header)
        val shareAppButton = findViewById<Button>(R.id.activity_settings_share_app_button)
        val sendEMailToSupportButton = findViewById<Button>(R.id.activity_settings_mail_to_support_button)
        val usersAgreementButton = findViewById<Button>(R.id.activity_settings_user_agree_button)

        backToMainMenu.setOnClickListener {
            finish()
        }

        shareAppButton.setOnClickListener {

            val shareAppDisplay = Intent(Intent.ACTION_SEND)
            shareAppDisplay.type = "text/plain"
            shareAppDisplay.putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.ru/android-developer/?from=catalog")
            startActivity(shareAppDisplay)
        }

        sendEMailToSupportButton.setOnClickListener{

            val mailToAddress = arrayOf("kondratyev_dv@mail.ru")
            val mailSubject = "Сообщение разработчикам и разработчицам приложения Playlist Maker"
            val mailText = "Спасибо разработчикам и разработчицам за крутое приложение!"

            val sendEmailToSupportIntent = Intent(Intent.ACTION_SENDTO)
            sendEmailToSupportIntent.data = Uri.parse("mailto:")
            sendEmailToSupportIntent.putExtra(Intent.EXTRA_EMAIL, mailToAddress)
            sendEmailToSupportIntent.putExtra(Intent.EXTRA_TEXT, mailText)
            sendEmailToSupportIntent.putExtra(Intent.EXTRA_SUBJECT, mailSubject)
            startActivity(sendEmailToSupportIntent)
        }

        usersAgreementButton.setOnClickListener {
            val urlAgreement = "https://yandex.ru/legal/practicum_offer/"
            val  usersAgreementIntent = Intent(Intent.ACTION_VIEW)
            usersAgreementIntent.data = Uri.parse(urlAgreement)
            startActivity(usersAgreementIntent)

        }


    }
}