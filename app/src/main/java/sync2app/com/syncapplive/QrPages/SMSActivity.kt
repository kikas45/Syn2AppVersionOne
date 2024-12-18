package sync2app.com.syncapplive.QrPages

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import sync2app.com.syncapplive.WebViewPage
import sync2app.com.syncapplive.R
import sync2app.com.syncapplive.additionalSettings.utils.Constants
import sync2app.com.syncapplive.databinding.ActivitySmsactivityBinding
import java.io.File


class SMSActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySmsactivityBinding

    private val sharedBiometric: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SHARED_BIOMETRIC,
            Context.MODE_PRIVATE
        )
    }
    private val myDownloadClass: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.MY_DOWNLOADER_CLASS,
            Context.MODE_PRIVATE
        )
    }

    private var preferences: SharedPreferences? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmsactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        binding.apply {
            if (preferences!!.getBoolean("darktheme", false)) {


                // Set status bar color
                window?.statusBarColor = Color.parseColor("#171616")
                // Set navigation bar color
                window?.navigationBarColor = Color.parseColor("#171616")

                // Ensure the text and icons are white
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = false



                parentContainer.setBackgroundColor(resources.getColor(R.color.dark_layout_for_ui))

                // set text view

                textTitle.setTextColor(resources.getColor(R.color.white))
                textDisplayText.setTextColor(resources.getColor(R.color.white))

                textPgoneNumbers.setTextColor(resources.getColor(R.color.white))

                textSendMySms.setTextColor(resources.getColor(R.color.white))
                textSendMySms.setBackgroundResource(R.drawable.card_design_darktheme_outline)

                textDoNothing.setTextColor(resources.getColor(R.color.white))
                textDoNothing.setBackgroundResource(R.drawable.card_design_darktheme_outline)

                textCopySms.setTextColor(resources.getColor(R.color.white))
                textCopySms.setBackgroundResource(R.drawable.card_design_darktheme_outline)



                // fir back button
                val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_arrow)
                drawable?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.white), PorterDuff.Mode.SRC_IN)
                closeBs.setImageDrawable(drawable)



                //  for divider i..n
                divider21.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))


                val drawable_imageView20 = ContextCompat.getDrawable(applicationContext, R.drawable.ic_email_read_24)
                drawable_imageView20?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.deep_blue_light_extra), PorterDuff.Mode.SRC_IN)
                imageView20.setImageDrawable(drawable_imageView20)




            }
        }






        val phoneNumber: String = sharedBiometric.getString("phoneNumber", "").toString()
        val message: String = sharedBiometric.getString("message", "").toString()


        val get_imgToggleImageBackground = sharedBiometric.getString(Constants.imgToggleImageBackground, "")
        val get_imageUseBranding = sharedBiometric.getString(Constants.imageUseBranding, "")
        if (get_imgToggleImageBackground.equals(Constants.imgToggleImageBackground) && get_imageUseBranding.equals(Constants.imageUseBranding) ){
            loadBackGroundImage()
        }




        binding.textSendMySms.setOnClickListener {
            sendSMS(phoneNumber, message)
        }


        binding.apply {
            textPgoneNumbers.text = "To: $phoneNumber"
            textDisplayText.text = message
        }


        binding.textCopySms.setOnClickListener {
            copyWifiPasswordRawData(message)
        }



        binding.closeBs.setOnClickListener {
            endMyActivity()
        }


        binding.textDoNothing.setOnClickListener {
            endMyActivity()
        }

    }


    private fun loadBackGroundImage() {

        val fileTypes = "app_background.png"
        val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
        val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()

        val pathFolder = "/" + getFolderClo + "/" + getFolderSubpath + "/" + Constants.App + "/" + "Config"
        val folder =
            Environment.getExternalStorageDirectory().absolutePath + "/Download/${Constants.Syn2AppLive}/" + pathFolder
        val file = File(folder, fileTypes)

        if (file.exists()) {
            Glide.with(this).load(file).centerCrop().into(binding.backgroundImage)

        }
    }



    private fun sendSMS(phoneNumber: String?, message: String?) {
        val smsIntent = Intent(Intent.ACTION_SENDTO)
        smsIntent.data = Uri.parse("smsto:" + Uri.encode(phoneNumber))
        smsIntent.putExtra("sms_body", message)
        try {
            startActivity(smsIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No SMS app found", Toast.LENGTH_SHORT).show()
        }
    }



    private fun copyWifiPasswordRawData(messageState: String) {
        val clipboard = applicationContext.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.text = messageState
    }


    override fun onBackPressed() {
        endMyActivity()
    }

    private fun endMyActivity() {
        val editor = sharedBiometric.edit()
        editor.remove("phoneNumber")
        editor.remove("message")
        editor.apply()
        startActivity(Intent(applicationContext, WebViewPage::class.java))
        finish()

    }

}