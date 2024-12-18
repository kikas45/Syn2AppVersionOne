package sync2app.com.syncapplive.additionalSettings

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import sync2app.com.syncapplive.MyApplication
import sync2app.com.syncapplive.R
import sync2app.com.syncapplive.SettingsActivityKT
import sync2app.com.syncapplive.additionalSettings.autostartAppOncrash.Methods
import sync2app.com.syncapplive.additionalSettings.utils.Constants
import sync2app.com.syncapplive.databinding.ActivityMaintenanceBinding
import sync2app.com.syncapplive.databinding.CustomCrashReportBinding
import sync2app.com.syncapplive.databinding.CustomDefineRefreshTimeBinding
import sync2app.com.syncapplive.databinding.CustomDefinedTimeIntervalsBinding
import java.io.File
import android.content.res.Configuration


class MaintenanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaintenanceBinding



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

    private var getTimeDefined_Prime = ""
    private var Refresh_Time = "Refresh Time: "
    private var T_3_HR = 3L
    private var T_4_HR = 4L
    private var T_5_HR = 5L
    private var T_6_HR = 6L
    private var T_7_HR = 7L
    private var T_8_HR = 8L
    private var T_9_HR = 9L
    private var T_10_HR = 10L
    private var T_11_HR = 11L
    private var T_12_HR = 12L



    @SuppressLint("SetTextI18n", "SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaintenanceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // set up drak theme layout
        preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        binding.apply {
            if (preferences!!.getBoolean("darktheme", false)) {

                // set windows
                // Set status bar color
                window?.statusBarColor = Color.parseColor("#171616")
                // Set navigation bar color
                window?.navigationBarColor = Color.parseColor("#171616")

                // Ensure the text and icons are white
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = false


                parentContainer.setBackgroundColor(resources.getColor(R.color.dark_layout_for_ui))

                // set text view

                textView42.setTextColor(resources.getColor(R.color.white))
                textHardwarePage.setTextColor(resources.getColor(R.color.white))
                textBranding.setTextColor(resources.getColor(R.color.white))
                textCrashPage.setTextColor(resources.getColor(R.color.white))
                textFileManger.setTextColor(resources.getColor(R.color.white))
                textCheckDownloadStatus2.setTextColor(resources.getColor(R.color.white))
                textShowOnlineStatus.setTextColor(resources.getColor(R.color.white))
                textShowAppRestartTvMode.setTextColor(resources.getColor(R.color.white))


                // fir back button
                val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_arrow)
                drawable?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.white), PorterDuff.Mode.SRC_IN)
                closeBs.setImageDrawable(drawable)


                // for all forward icon
                val drawable_imageView6 = ContextCompat.getDrawable(applicationContext, R.drawable.ic_arrow_move_front)
                drawable_imageView6?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.white), PorterDuff.Mode.SRC_IN)
                imageView40.setImageDrawable(drawable_imageView6)
                imageView22.setImageDrawable(drawable_imageView6)
                imageView36.setImageDrawable(drawable_imageView6)
                imageView4.setImageDrawable(drawable_imageView6)






                // for nav icons
                val drawable_imageView41 = ContextCompat.getDrawable(applicationContext, R.drawable.ic_branding)
                drawable_imageView41?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.deep_blue_light_extra), PorterDuff.Mode.SRC_IN)
                imageView41.setImageDrawable(drawable_imageView41)

                val drawable_imageView9 = ContextCompat.getDrawable(applicationContext, R.drawable.ic_branding)
                drawable_imageView9?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.deep_blue_light_extra), PorterDuff.Mode.SRC_IN)
                imageView9.setImageDrawable(drawable_imageView9)

                val drawable_imageView21 = ContextCompat.getDrawable(applicationContext, R.drawable.ic_crash_report)
                drawable_imageView21?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.deep_blue_light_extra), PorterDuff.Mode.SRC_IN)
                imageView21.setImageDrawable(drawable_imageView21)

                val drawable_imageView33 = ContextCompat.getDrawable(applicationContext, R.drawable.ic_folder_icon)
                drawable_imageView33?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.deep_blue_light_extra), PorterDuff.Mode.SRC_IN)
                imageView33.setImageDrawable(drawable_imageView33)

                val drawable_imageView39 = ContextCompat.getDrawable(applicationContext, R.drawable.ic_download_24)
                drawable_imageView39?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.deep_blue_light_extra), PorterDuff.Mode.SRC_IN)
                imageView39.setImageDrawable(drawable_imageView39)

                val drawable_imageView23 = ContextCompat.getDrawable(applicationContext, R.drawable.ic_show_online_status)
                drawable_imageView23?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.deep_blue_light_extra), PorterDuff.Mode.SRC_IN)
                imageView23.setImageDrawable(drawable_imageView23)

                val drawable_imageView37 = ContextCompat.getDrawable(applicationContext, R.drawable.ic_crash_report)
                drawable_imageView37?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.deep_blue_light_extra), PorterDuff.Mode.SRC_IN)
                imageView37.setImageDrawable(drawable_imageView37)





                // Define colors for different states
                val thumbColorStateList = ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_checked),
                        intArrayOf(-android.R.attr.state_checked)
                    ),
                    intArrayOf(
                        Color.LTGRAY,  // Color when checked
                        Color.LTGRAY   // Color when unchecked
                    )
                )

                val trackColorStateList = ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_enabled),
                        intArrayOf(-android.R.attr.state_enabled)
                    ),
                    intArrayOf(
                        Color.DKGRAY,  // Color when enabled
                        Color.LTGRAY   // Color when disabled
                    )
                )

                // Apply the color state lists
                imagEnableDownloadStatus.thumbTintList = thumbColorStateList
                imagEnableDownloadStatus.trackTintList = trackColorStateList

                imagShowOnlineStatus.thumbTintList = thumbColorStateList
                imagShowOnlineStatus.trackTintList = trackColorStateList

                imgStartAppRestartOnTvMode.thumbTintList = thumbColorStateList
                imgStartAppRestartOnTvMode.trackTintList = trackColorStateList



                //  for divider i..n
                divider21.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider32.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider33.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider42.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider38.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider29.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider41.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider34.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider40.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))





            }
        }




        val editor = sharedBiometric.edit()

        Handler(Looper.getMainLooper()).postDelayed({
            getCarshReports()
        }, 700)


        val get_imgToggleImageBackground = sharedBiometric.getString(Constants.imgToggleImageBackground, "")
        val get_imageUseBranding = sharedBiometric.getString(Constants.imageUseBranding, "")
        if (get_imgToggleImageBackground.equals(Constants.imgToggleImageBackground) && get_imageUseBranding.equals(Constants.imageUseBranding) ){
            loadBackGroundImage()
        }


        binding.apply {

            val getState = sharedBiometric.getString(Constants.ENABLE_LANDSCAPE_MODE, "").toString()
            imgSetOrientationMode.isChecked = getState.equals(Constants.ENABLE_LANDSCAPE_MODE)

            if (getState == Constants.ENABLE_LANDSCAPE_MODE){
                requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                textSetOrientationMode.text = "Landscape Mode Enabled"
            }else{

                if (getState.isNullOrEmpty()){
                    requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    textSetOrientationMode.text = "Landscape Mode Enabled"
                }else{
                    requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    textSetOrientationMode.text = "Portrait Mode Enabled"
                }
            }


            // Set an OnClickListener to toggle orientation mode
            imgSetOrientationMode.setOnCheckedChangeListener { compoundButton, isValued -> // we are putting the values into SHARED PREFERENCE
                if (compoundButton.isChecked) {

                    // Switch to landscape mode
                    requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

                    // Save the landscape mode setting
                    editor.putString(Constants.ENABLE_LANDSCAPE_MODE, Constants.ENABLE_LANDSCAPE_MODE)
                    editor.apply()

                    // Update UI elements
                    textSetOrientationMode.text = "Landscape Mode Enabled"

                } else {
                    // Switch to portrait mode
                    requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                    // Remove the landscape mode setting
                    editor.putString(Constants.ENABLE_LANDSCAPE_MODE, Constants.ENABLE_POTRAIT_MODE)
                    editor.apply()

                    // Update UI elements
                    textSetOrientationMode.text = "Portrait Mode Enabled"


            }
        }




        }


        binding.textBranding.setOnClickListener {
            startActivity(Intent(applicationContext, BrandingActivity::class.java))
            finish()
        }


        //add exception
        Methods.addExceptionHandler(this)


        // show online Status

        binding.apply {


            imagShowOnlineStatus.setOnCheckedChangeListener { compoundButton, isValued -> // we are putting the values into SHARED PREFERENCE
                if (compoundButton.isChecked) {
                    editor.remove(Constants.imagShowOnlineStatus)
                    editor.apply()
                    binding.textShowOnlineStatus.text = "Show Online Indicator"

                } else {
                    editor.putString(Constants.imagShowOnlineStatus, "imagShowOnlineStatus")
                    editor.apply()
                    binding.textShowOnlineStatus.text = "Hide Online Indicator"
                }
            }




            val get_indicator_satate = sharedBiometric.getString(Constants.img_Make_OnlineIndicator_Default_visible, "").toString()

            if (get_indicator_satate.isNullOrEmpty()){
                editor.putString(Constants.img_Make_OnlineIndicator_Default_visible, Constants.img_Make_OnlineIndicator_Default_visible)
                editor.remove(Constants.imagShowOnlineStatus)
                editor.apply()
                binding.textShowOnlineStatus.text = "Show Online Indicator"
                binding.imagShowOnlineStatus.isChecked = true

            }else{

                val get_imagShowOnlineStatus = sharedBiometric.getString(Constants.imagShowOnlineStatus, "").toString()

                imagShowOnlineStatus.isChecked = get_imagShowOnlineStatus.equals(Constants.imagShowOnlineStatus)

                if (get_imagShowOnlineStatus.equals(Constants.imagShowOnlineStatus)) {

                    binding.textShowOnlineStatus.text = "Hide Online Indicator"
                    binding.imagShowOnlineStatus.isChecked = false

                } else {
                    binding.textShowOnlineStatus.text = "Show Online Indicator"
                    binding.imagShowOnlineStatus.isChecked = true


                }



            }


        }




        binding.apply {





            /// enable the Auto Boot
            imagEnableDownloadStatus.setOnCheckedChangeListener { compoundButton, isValued -> // we are putting the values into SHARED PREFERENCE
                if (compoundButton.isChecked) {

                    editor.putString(Constants.showDownloadSyncStatus, "showDownloadSyncStatus")
                    editor.apply()
                    binding.textCheckDownloadStatus2.text = "Show Download Status"

                } else {
                    editor.remove(Constants.showDownloadSyncStatus)
                    editor.apply()
                    binding.textCheckDownloadStatus2.text = "Hide Download Status"
                }
            }


            val get_imagEnableDownloadStatus = sharedBiometric.getString(Constants.showDownloadSyncStatus, "")


            imagEnableDownloadStatus.isChecked = get_imagEnableDownloadStatus.equals(Constants.showDownloadSyncStatus)

            if (get_imagEnableDownloadStatus.equals(Constants.showDownloadSyncStatus)) {

                binding.textCheckDownloadStatus2.text = "Show Download Status"

            } else {

                binding.textCheckDownloadStatus2.text = "Hide Download Status"

            }




            closeBs.setOnClickListener {

                val get_navigationS2222 = sharedBiometric.getString(Constants.SAVE_NAVIGATION, "")

                if (get_navigationS2222.equals(Constants.SettingsPage)) {
                    val intent = Intent(applicationContext, SettingsActivityKT::class.java)
                    startActivity(intent)
                    finish()
                } else if (get_navigationS2222.equals(Constants.AdditionNalPage)) {
                    val intent =
                        Intent(applicationContext, AdditionalSettingsActivity::class.java)
                    startActivity(intent)
                    finish()
                }


            }


            textHardwarePage.setOnClickListener {
                val intent = Intent(applicationContext, SystemInfoActivity::class.java)
                startActivity(intent)


            }




            textFileManger.setOnClickListener {
                startActivity(Intent(applicationContext, FileExplorerActivity::class.java))
            }



            textCrashPage.setOnClickListener {

                val sharedCrashReport = getSharedPreferences(Constants.SHARED_SAVED_CRASH_REPORT, MODE_PRIVATE)
                val crashInfo = sharedCrashReport.getString(Constants.crashInfo, "")
                if (!crashInfo.isNullOrEmpty()) {
                    showPopCrashReport(crashInfo.toString())
                } else {
                    showToastMessage("No crash report yet")
                }


            }

        }

        binding.apply {


            // Restart app on Tv or Mobile mode
            imgStartAppRestartOnTvMode.setOnCheckedChangeListener { compoundButton, isValued -> // we are putting the values into SHARED PREFERENCE
                if (compoundButton.isChecked) {

                    editor.putString(Constants.imgStartAppRestartOnTvMode, Constants.imgStartAppRestartOnTvMode)
                    editor.apply()
                    binding.textShowAppRestartTvMode.text = "Restart On Crash Enabled"

                } else {
                    editor.remove(Constants.imgStartAppRestartOnTvMode)
                    editor.apply()
                    binding.textShowAppRestartTvMode.text = "Restart On Crash Disabled"
                }
            }


            val get_imgStartAppRestartOnTvMode = sharedBiometric.getString(Constants.imgStartAppRestartOnTvMode, "")


            imgStartAppRestartOnTvMode.isChecked = get_imgStartAppRestartOnTvMode.equals(Constants.imgStartAppRestartOnTvMode)

            if (get_imgStartAppRestartOnTvMode.equals(Constants.imgStartAppRestartOnTvMode)) {

                binding.textShowAppRestartTvMode.text = "Restart On Crash Enabled"

            } else {

                binding.textShowAppRestartTvMode.text = "Restart On Crash Disabled"
            }




            closeBs.setOnClickListener {

                val get_navigationS2222 = sharedBiometric.getString(Constants.SAVE_NAVIGATION, "")

                if (get_navigationS2222.equals(Constants.SettingsPage)) {
                    val intent = Intent(applicationContext, SettingsActivityKT::class.java)
                    startActivity(intent)
                    finish()
                } else if (get_navigationS2222.equals(Constants.AdditionNalPage)) {
                    val intent =
                        Intent(applicationContext, AdditionalSettingsActivity::class.java)
                    startActivity(intent)
                    finish()
                }


            }


            textHardwarePage.setOnClickListener {
                val intent = Intent(applicationContext, SystemInfoActivity::class.java)
                startActivity(intent)


            }




            textFileManger.setOnClickListener {
                startActivity(Intent(applicationContext, FileExplorerActivity::class.java))
            }



            textCrashPage.setOnClickListener {

                val sharedCrashReport =
                    getSharedPreferences(Constants.SHARED_SAVED_CRASH_REPORT, MODE_PRIVATE)
                val crashInfo = sharedCrashReport.getString(Constants.crashInfo, "")
                if (!crashInfo.isNullOrEmpty()) {
                    showPopCrashReport(crashInfo.toString())
                } else {
                    showToastMessage("No crash report yet")
                }


            }

        }




        binding.textRefreshTimer.setOnClickListener {
            definedTimeIntervalsForRefresh()
        }


        // Init Refresh Time
        val d_time = myDownloadClass.getLong(Constants.get_Refresh_Timer, 0L)
        d_time.let {itLong->
            if (itLong != 0L) {
                binding.textRefreshTimer.text ="Refresh Time : $itLong Hours"
            }
        }



    }


    @SuppressLint("InflateParams", "SuspiciousIndentation", "SetTextI18n")
    private fun definedTimeIntervalsForRefresh() {
        val bindingCm: CustomDefineRefreshTimeBinding = CustomDefineRefreshTimeBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(bindingCm.getRoot())
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.setCancelable(true)

        // Set the background of the AlertDialog to be transparent
        if (alertDialog.window != null) {
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }







        bindingCm.apply {

            imageCrossClose.setOnClickListener {
                alertDialog.dismiss()
            }



            closeBs.setOnClickListener {
                alertDialog.dismiss()
            }



            textTwoMinutes.setOnClickListener {

                binding.textRefreshTimer.text = "$Refresh_Time$T_3_HR Hours"
                val editor = myDownloadClass.edit()
                editor.putLong(Constants.get_Refresh_Timer, Constants.T_3_HR)
                editor.apply()
                alertDialog.dismiss()

            }


            text55minutes.setOnClickListener {

                binding.textRefreshTimer.text = "$Refresh_Time$T_4_HR Hours"
                val editor = myDownloadClass.edit()
                editor.putLong(Constants.get_Refresh_Timer, Constants.T_4_HR)
                editor.apply()
                alertDialog.dismiss()

            }

            text100minutes2.setOnClickListener {

                binding.textRefreshTimer.text = "$Refresh_Time$T_5_HR Hours"
                val editor = myDownloadClass.edit()
                editor.putLong(Constants.get_Refresh_Timer, Constants.T_5_HR)
                editor.apply()
                alertDialog.dismiss()

            }


            text1500minutes.setOnClickListener {

                binding.textRefreshTimer.text = "$Refresh_Time$T_6_HR Hours"
                val editor = myDownloadClass.edit()
                editor.putLong(Constants.get_Refresh_Timer, Constants.T_6_HR)
                editor.apply()
                alertDialog.dismiss()

            }



            text3000minutes2.setOnClickListener {

                binding.textRefreshTimer.text = "$Refresh_Time$T_7_HR Hours"
                val editor = myDownloadClass.edit()
                editor.putLong(Constants.get_Refresh_Timer, Constants.T_7_HR)
                editor.apply()

                alertDialog.dismiss()
            }



            text6000minutes.setOnClickListener {

                    binding.textRefreshTimer.text = "$Refresh_Time$T_8_HR Hours"
                    val editor = myDownloadClass.edit()
                    editor.putLong(Constants.get_Refresh_Timer, Constants.T_8_HR)
                    editor.apply()

                    alertDialog.dismiss()
            }


            textOneTwentyMinutes.setOnClickListener {

                binding.textRefreshTimer.text = "$Refresh_Time$T_9_HR Hours"
                val editor = myDownloadClass.edit()
                editor.putLong(Constants.get_Refresh_Timer, Constants.T_9_HR)
                editor.apply()


                alertDialog.dismiss()

            }



            text10Hours.setOnClickListener {

                binding.textRefreshTimer.text = "$Refresh_Time$T_10_HR Hours"
                val editor = myDownloadClass.edit()
                editor.putLong(Constants.get_Refresh_Timer, Constants.T_10_HR)
                editor.apply()

                alertDialog.dismiss()


            }


            text11Hours.setOnClickListener {
                binding.textRefreshTimer.text = "$Refresh_Time$T_11_HR Hours"
                val editor = myDownloadClass.edit()
                editor.putLong(Constants.get_Refresh_Timer, Constants.T_11_HR)
                editor.apply()
                alertDialog.dismiss()

            }

            text12Hours.setOnClickListener {

                binding.textRefreshTimer.text = "$Refresh_Time$T_12_HR Hours"
                val editor = myDownloadClass.edit()
                editor.putLong(Constants.get_Refresh_Timer, Constants.T_12_HR)
                editor.apply()

                alertDialog.dismiss()

            }


        }


        alertDialog.show()
    }







    private fun getCarshReports() {
        val sharedCrashReport =
            getSharedPreferences(Constants.SHARED_SAVED_CRASH_REPORT, MODE_PRIVATE)
        val crashInfo = sharedCrashReport.getString(Constants.crashInfo, "")
        val crashCalled = sharedCrashReport.getString(Constants.crashCalled, "")
        if (!crashCalled.isNullOrEmpty()) {
            showPopCrashReport(crashInfo + "")
        }
    }


    @SuppressLint("MissingInflatedId")
    private fun showPopCrashReport(message: String) {
        val bindingCm: CustomCrashReportBinding =
            CustomCrashReportBinding.inflate(LayoutInflater.from(this))
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(bindingCm.getRoot())

        val alertDialog = alertDialogBuilder.create()
        // Set the background of the AlertDialog to be transparent
        if (alertDialog.window != null) {
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }

        alertDialog.setCanceledOnTouchOutside(true)

        bindingCm.apply {




            val preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)

            if (preferences.getBoolean("darktheme", false)) {
                consMainAlertSubLayout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)

                textDisplayResults.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                textTitles.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                textContinuPassword2.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                textContinuPassword2.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black)

            }


        }





        bindingCm.textDisplayResults.setText(message)
        bindingCm.textContinuPassword2.setOnClickListener { view ->
            val sharedCrashReport = getSharedPreferences(
                Constants.SHARED_SAVED_CRASH_REPORT,
                MODE_PRIVATE
            )
            val editor = sharedCrashReport.edit()
            editor.remove(Constants.crashCalled)
            editor.apply()
            sendCrashReport(message)
            alertDialog.dismiss()
        }

        // Show the AlertDialog
        alertDialog.show()
    }


    private fun showToastMessage(messages: String) {

        try {
            Toast.makeText(applicationContext, messages, Toast.LENGTH_SHORT).show()
        } catch (_: Exception) {
        }
    }

    fun sendCrashReport(crashMessage: String?) {
        //  val email = "kola@moreadvice.co.uk"
        val email = "support@syn2app.com"
        val subject = "Crash Report"

        val uriText = "mailto:" + Uri.encode(email) +
                "?subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(crashMessage)

        val uri = Uri.parse(uriText)

        // Intent for sending text
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, crashMessage)
        sendIntent.type = "text/plain"

        // Intent for sending email
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = uri

        // Create a chooser with both intents
        val chooserIntent = Intent.createChooser(sendIntent, "Send message via:")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(emailIntent))

        try {
            startActivity(chooserIntent)
        } catch (e: ActivityNotFoundException) {
            // Handle case where no email app is available
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onBackPressed() {
        val get_navigationS2222 = sharedBiometric.getString(Constants.SAVE_NAVIGATION, "")

        if (get_navigationS2222.equals(Constants.SettingsPage)) {
            val intent = Intent(applicationContext, SettingsActivityKT::class.java)
            startActivity(intent)
            finish()
        } else if (get_navigationS2222.equals(Constants.AdditionNalPage)) {
            val intent =
                Intent(applicationContext, AdditionalSettingsActivity::class.java)
            startActivity(intent)
            finish()
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



}