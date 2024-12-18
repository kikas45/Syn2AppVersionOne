package sync2app.com.syncapplive.additionalSettings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import sync2app.com.syncapplive.additionalSettings.utils.Constants

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import sync2app.com.syncapplive.R
import sync2app.com.syncapplive.SplashKT
import sync2app.com.syncapplive.additionalSettings.autostartAppOncrash.Methods

import sync2app.com.syncapplive.databinding.ActivityRequiredBioBinding
import java.io.File


class RequiredBioActivity : AppCompatActivity() {

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

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var binding: ActivityRequiredBioBinding
    private var preferences: SharedPreferences? = null

    @SuppressLint("SourceLockedOrientationActivity", "SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequiredBioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getState = sharedBiometric.getString(Constants.ENABLE_LANDSCAPE_MODE, "").toString()
        if (getState == Constants.ENABLE_LANDSCAPE_MODE){
            requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }else{
            if (getState.isNullOrEmpty()){
                requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }else{
                requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }



        applyDarkTheme()
        Methods.addExceptionHandler(this)

        if (shouldLoadBackgroundImage()) {
            loadBackGroundImage()
        }

        setupBiometricAuthentication()
    }

    private fun applyDarkTheme() {
        preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        if (preferences!!.getBoolean("darktheme", false)) {
            // Set status bar color
            window?.statusBarColor = Color.parseColor("#171616")
            // Set navigation bar color
            window?.navigationBarColor = Color.parseColor("#171616")

            // Ensure the text and icons are white
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = false


            binding.parentContainer.setBackgroundColor(resources.getColor(R.color.dark_layout_for_ui))
            binding.textView2.setTextColor(resources.getColor(R.color.white))
            binding.textView3.setTextColor(resources.getColor(R.color.white))
            binding.textView.setTextColor(resources.getColor(R.color.white))

            val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_fingerprint)
            drawable?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.deep_blue_light_extra), PorterDuff.Mode.SRC_IN)
            binding.imageView3.setImageDrawable(drawable)
        }
    }

    private fun shouldLoadBackgroundImage(): Boolean {
        val get_imgToggleImageBackground = sharedBiometric.getString(Constants.imgToggleImageBackground, "")
        val get_imageUseBranding = sharedBiometric.getString(Constants.imageUseBranding, "")
        return get_imgToggleImageBackground == Constants.imgToggleImageBackground && get_imageUseBranding == Constants.imageUseBranding
    }

    private fun setupBiometricAuthentication() {
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.d("BiometricPrompt", "Authentication error: $errString")
                displayMessage("Authentication error: $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d("BiometricPrompt", "Authentication succeeded")
                displayMessage("Authentication succeeded!")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.d("BiometricPrompt", "Authentication failed")
                displayMessage("Authentication failed")
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Cancel")
            .build()

        binding.imageView3.setOnClickListener {
            Log.d("RequiredBioActivity", "imageView3 clicked")

            val biometricManager = BiometricManager.from(this)
            when (biometricManager.canAuthenticate()) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    try {
                        biometricPrompt.authenticate(promptInfo)
                    } catch (e: Exception) {
                        Log.e("BiometricPrompt", "Error initiating biometric authentication", e)
                        displayMessage("Error initiating biometric authentication: ${e.message}")
                    }
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> displayMessage("This device doesn't support biometric authentication")
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> displayMessage("Biometric authentication is currently unavailable")
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    redirectToEnrollBiometrics()
                }
            }
        }
    }

    private fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        if (message == "Authentication succeeded!") {
            val intent = Intent(this, SplashKT::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun redirectToEnrollBiometrics() {
        Toast.makeText(this, "Please enroll biometric credentials in your device settings.", Toast.LENGTH_LONG).show()
        val enrollIntent = Intent(Settings.ACTION_SECURITY_SETTINGS)
        startActivity(enrollIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        val getValue = sharedBiometric.getString(Constants.imgAllowFingerPrint, "")
        if (getValue != Constants.imgAllowFingerPrint) {
            startActivity(Intent(applicationContext, SplashKT::class.java))
            finish()
        }
    }

    private fun loadBackGroundImage() {
        val fileTypes = "app_background.png"
        val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
        val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
        val pathFolder = "/$getFolderClo/$getFolderSubpath/${Constants.App}/Config"
        val folder = Environment.getExternalStorageDirectory().absolutePath + "/Download/${Constants.Syn2AppLive}/$pathFolder"
        val file = File(folder, fileTypes)

        if (file.exists()) {
            Glide.with(this).load(file).centerCrop().into(binding.backgroundImage)
        }
    }
}
