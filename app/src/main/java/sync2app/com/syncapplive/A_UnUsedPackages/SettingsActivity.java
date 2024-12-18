
/// un comment the code and add the Activity back in the manifest if needed

/*
package sync2app.com.syncapplive.UnUsedPackages;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;


import com.bumptech.glide.Glide;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import sync2app.com.syncapplive.MyApplication;
import sync2app.com.syncapplive.R;
import sync2app.com.syncapplive.SettingsActivityKT;
import sync2app.com.syncapplive.Splash;
import sync2app.com.syncapplive.additionalSettings.AdditionalSettingsActivity;
import sync2app.com.syncapplive.additionalSettings.MainHelpers.GMailSender;
import sync2app.com.syncapplive.additionalSettings.MaintenanceActivity;
import sync2app.com.syncapplive.additionalSettings.PasswordActivity;
import sync2app.com.syncapplive.additionalSettings.ReSyncActivity;
import sync2app.com.syncapplive.additionalSettings.TvActivityOrAppMode;
import sync2app.com.syncapplive.additionalSettings.autostartAppOncrash.Methods;
import sync2app.com.syncapplive.additionalSettings.myService.OnChangeApiServiceSync;
import sync2app.com.syncapplive.additionalSettings.myService.SyncInterval;
import sync2app.com.syncapplive.additionalSettings.myService.OnChnageService;
import sync2app.com.syncapplive.additionalSettings.utils.Constants;
import sync2app.com.syncapplive.constants;
import sync2app.com.syncapplive.databinding.CustomConfirmExitDialogBinding;
import sync2app.com.syncapplive.databinding.CustomEmailSucessLayoutBinding;
import sync2app.com.syncapplive.databinding.CustomForgetPasswordEmailLayoutBinding;
import sync2app.com.syncapplive.databinding.CustomRedirectEmailLayoutBinding;
import sync2app.com.syncapplive.databinding.CustomSettingsPageBinding;
import sync2app.com.syncapplive.databinding.ProgressDialogLayoutBinding;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences preferences;

    String Admin_Password = "1234";
    Handler handler = new Handler();

    private Dialog customProgressDialog;

    private ImageView backgroundImage;
    private ImageView close_bs;
    private TextView textView42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            preferences = PreferenceManager.getDefaultSharedPreferences(this);
            if (preferences.getBoolean("darktheme", false)) {
                setTheme(R.style.DarkThemeSettings);
            }
        } catch (Exception e) {
        }
        setContentView(R.layout.settings_activity);


        try {
            showExitConfirmationDialog();
        } catch (Exception e) {
        }


        try {

            MyApplication.incrementRunningActivities();

            //add exception
            Methods.addExceptionHandler(this);


            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);

            String get_imgToggleImageBackground = sharedBiometric.getString(Constants.imgToggleImageBackground, "");
            String get_imageUseBranding = sharedBiometric.getString(Constants.imageUseBranding, "");
            if (get_imgToggleImageBackground.equals(Constants.imgToggleImageBackground) && get_imageUseBranding.equals(Constants.imageUseBranding)) {
                loadBackGroundImage();
            }


            close_bs = findViewById(R.id.close_bs);
            textView42 = findViewById(R.id.textView42);


            textView42.setOnClickListener(v->{
                startActivity(new Intent(getApplicationContext(), SettingsActivityKT.class));
                finish();
            });



            if (preferences.getBoolean("darktheme", false)) {
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_arrow);
                if (drawable != null) {
                    drawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                    close_bs.setImageDrawable(drawable);
                }
                setStatusBarColor(Color.parseColor("#171616"));

            } else {


                setStatusBarColor(Color.parseColor("#FFFFFF"));


            }


            close_bs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), WebActivity.class));
                    finish();
                }
            });


            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();

            // This service should only run if web view page is visible
            stopService(new Intent(getApplicationContext(), OnChangeApiServiceSync.class));

        } catch (Exception e) {
        }


    }


    private void setStatusBarColor(int color) {
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(color);
            }

            // For translucent status bar in older versions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.decrementRunningActivities();

        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            customProgressDialog.dismiss();
        }

    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), WebActivity.class));
        finish();
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;

        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }


    private void sendMessage(final String reciverEmail, final String myMessage) {


        showCustomProgressDialog("Sending Email");

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(Constants.Sender_email_Address, Constants.Sender_email_Password);
                    sender.sendMail(Constants.Subject, "Your Password is\n" + myMessage, Constants.Sender_name, reciverEmail);
                    Log.d("mylog", "Email Sent Successfully");

                    // Show toast message on UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            show_Pop_Up_Email_Sent_Sucessful("Email sent", "Kindly check email to view password");
                            customProgressDialog.dismiss();
                        }
                    });


                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());

                    // Show toast message on UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            show_Pop_Up_Email_Sent_Sucessful("Failed!", "Unable to send email");
                            customProgressDialog.dismiss();
                        }
                    });
                }
            }
        });
        sender.start();
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void showCustomProgressDialog(String message) {
        try {
            customProgressDialog = new Dialog(this);
            ProgressDialogLayoutBinding binding = ProgressDialogLayoutBinding.inflate(LayoutInflater.from(this));
            customProgressDialog.setContentView(binding.getRoot());
            customProgressDialog.setCancelable(false);
            customProgressDialog.setCanceledOnTouchOutside(false);
            customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            binding.textLoading.setText(message);

            binding.imgCloseDialog.setVisibility(View.GONE);

            ConstraintLayout consMainAlert_sub_layout = binding.consMainAlertSubLayout;
            ImageView imagSucessful = binding.imagSucessful;
            TextView textLoading = binding.textLoading;


            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if (preferences.getBoolean("darktheme", false)) {


                consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout);
                textLoading.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));

                Drawable drawable_imagSucessful = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_email_read_24);
                if (drawable_imagSucessful != null) {
                    drawable_imagSucessful.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_gray_pop), PorterDuff.Mode.SRC_IN);
                    imagSucessful.setImageDrawable(drawable_imagSucessful);
                }

            }


            customProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBackGroundImage() {


        backgroundImage = findViewById(R.id.backgroundImage);
        SharedPreferences sharedP = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
        String getFolderClo = sharedP.getString(Constants.getFolderClo, "").toString();
        String getFolderSubpath = sharedP.getString(Constants.getFolderSubpath, "").toString();

        String pathFolder = "/" + getFolderClo + "/" + getFolderSubpath + "/" + Constants.App + "/" + "Config";
        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" + Constants.Syn2AppLive + "/" + pathFolder;

        String fileTypes = "app_background.png";
        File file = new File(folder, fileTypes);

        if (file.exists()) {
            Glide.with(this).load(file).centerCrop().into(backgroundImage);
        }
    }

    private void setDrawableColor(ImageView imageView, int drawableId, int colorId) {
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), drawableId);
        if (drawable != null) {
            drawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), colorId), PorterDuff.Mode.SRC_IN);
            imageView.setImageDrawable(drawable);
        }
    }


    @SuppressLint("InflateParams")
    private void showExitConfirmationDialog() {

        CustomConfirmExitDialogBinding binding = CustomConfirmExitDialogBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(binding.getRoot());

        final AlertDialog alertDialog = builder.create();

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        // Set the background of the AlertDialog to be transparent
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        @SuppressLint("CommitPrefEdits")
        SharedPreferences simpleSavedPassword = getSharedPreferences(Constants.SIMPLE_SAVED_PASSWORD, Context.MODE_PRIVATE);


        EditText editTextText2 = binding.editTextText2;

        TextView textHome = binding.textHome;
        TextView textView4 = binding.textView4;
        TextView textLoginAdmin2 = binding.textLoginAdmin2;
        TextView textLogoutButton = binding.textLogoutButton;
        TextView textExit = binding.textExit;
        TextView textSettings = binding.textAppSettings;
        TextView textAppAdmin = binding.textAppAdmin;
        TextView textReSync = binding.textReSync;
        TextView textLaunchOnline = binding.textLaunchOnline;
        TextView textLaunchOffline = binding.textLaunchOffline;
        TextView textForgetPassword = binding.textForgetPasswordHome;
        TextView textCanCellDialog = binding.textCanCellDialog;
        TextView textAppSettings = binding.textAppSettings;


        TextView textForgetPasswordHome = binding.textForgetPasswordHome;


        ImageView imagePassowrdSettings = binding.imagePassowrdSettings;
        ImageView imgClearCatch = binding.imgClearCatch;
        ImageView imgWifi = binding.imgWifi;
        ImageView imgMaintainace = binding.imgMaintainace;
        View divider2 = binding.divider2;
        ConstraintLayout consMainAlert_sub_layout = binding.consMainAlertSubLayout;


        ImageView imgToggle = binding.imgToggle;
        ImageView imgToggleNzotVisible = binding.imgToggleNzotVisible;


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (preferences.getBoolean("darktheme", false)) {


            //  consMainAlert_dn.setBackgroundColor(getResources().getColor(R.color.dark_layout_for_ui));
            //  consMainAlert_sub_layout.setBackgroundColor(getResources().getColor(R.color.dark_layout_for_ui));
            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout);
            textHome.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textView4.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textLoginAdmin2.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textLogoutButton.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textExit.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textSettings.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textAppAdmin.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textReSync.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textLaunchOnline.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textLaunchOffline.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textForgetPassword.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textCanCellDialog.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            editTextText2.setHintTextColor(getResources().getColor(R.color.light_gray));
            editTextText2.setTextColor(getResources().getColor(R.color.white));


            textForgetPasswordHome.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);
            textCanCellDialog.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);
            textLaunchOnline.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);
            textLaunchOffline.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);
            textAppSettings.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);
            textHome.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);
            textReSync.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);
            textAppAdmin.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);
            textExit.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);
            textLogoutButton.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);
            textLoginAdmin2.setBackgroundResource(R.drawable.card_design_darktheme_outline_pop_layout);

            //   card_design_buy_gift_card_extra_dark_black
            //   card_design_darktheme_outline_pop_layout

            setDrawableColor(imagePassowrdSettings, R.drawable.ic_app_settings, R.color.dark_light_gray_pop);
            setDrawableColor(imgMaintainace, R.drawable.ic_maintence_24, R.color.dark_light_gray_pop);
            setDrawableColor(imgWifi, R.drawable.error_wifi, R.color.dark_light_gray_pop);
            setDrawableColor(imgClearCatch, R.drawable.ic_clear_catch, R.color.dark_light_gray_pop);

            divider2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_gray_pop));


            Drawable drawable_imgToggle = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_icon_visibile);
            if (drawable_imgToggle != null) {
                drawable_imgToggle.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_gray_pop), PorterDuff.Mode.SRC_IN);
            }
            imgToggle.setImageDrawable(drawable_imgToggle);


            Drawable drawable_imgToggleNzotVisible = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_visibility_24);
            if (drawable_imgToggleNzotVisible != null) {
                drawable_imgToggleNzotVisible.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_gray_pop), PorterDuff.Mode.SRC_IN);
            }
            imgToggleNzotVisible.setImageDrawable(drawable_imgToggleNzotVisible);


        }


        SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedBiometric.edit();

        String imgLaunch = sharedBiometric.getString(Constants.imgAllowLunchFromOnline, "");

        String imgEnablePassword = sharedBiometric.getString(Constants.imgEnablePassword, "");
        String did_user_input_passowrd = sharedBiometric.getString(Constants.Did_User_Input_PassWord, "");
        String simpleAdminPassword = simpleSavedPassword.getString(Constants.simpleSavedPassword, "");


        imgToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgToggle.setVisibility(View.INVISIBLE);
                imgToggleNzotVisible.setVisibility(View.VISIBLE);
                editTextText2.setTransformationMethod(null);
                editTextText2.setSelection(editTextText2.length());
            }
        });

        imgToggleNzotVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgToggle.setVisibility(View.VISIBLE);
                imgToggleNzotVisible.setVisibility(View.INVISIBLE);
                editTextText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                editTextText2.setSelection(editTextText2.length());
            }
        });


        textCanCellDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedBiometric = getApplicationContext().getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
                String getTvMode = sharedBiometric.getString(Constants.MY_TV_OR_APP_MODE, "");

                startActivity(new Intent(getApplicationContext(), WebActivity.class));
                finish();


            }
        });


        if (imgEnablePassword.equals(Constants.imgEnablePassword)) {
            editTextText2.setText(simpleAdminPassword);
            editTextText2.setEnabled(false);
        } else if (did_user_input_passowrd.equals(Constants.Did_User_Input_PassWord)) {
            editTextText2.setEnabled(true);
            editTextText2.setText(simpleAdminPassword);
        } else {
            editTextText2.setEnabled(true);
        }


        textReSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String simpleAdminPassword = simpleSavedPassword.getString(Constants.simpleSavedPassword, "");

                String editTextText = editTextText2.getText().toString().trim();
                if (imgEnablePassword.equals(Constants.imgEnablePassword) || editTextText.equals(simpleAdminPassword)) {
                    hideKeyBoard(editTextText2);

                    editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);

                    editor.putString(Constants.SAVE_NAVIGATION, Constants.SettingsPage);
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), ReSyncActivity.class));
                    finish();
                    alertDialog.dismiss();

                } else {
                    hideKeyBoard(editTextText2);
                    showToastMessage("Wrong password");
                    editTextText2.setError("Wrong password");
                }
            }
        });


        imagePassowrdSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String simpleAdminPassword = simpleSavedPassword.getString(Constants.simpleSavedPassword, "");

                String editTextText = editTextText2.getText().toString().trim();
                if (imgEnablePassword.equals(Constants.imgEnablePassword) || editTextText.equals(simpleAdminPassword)) {
                    hideKeyBoard(editTextText2);
                    startActivity(new Intent(getApplicationContext(), PasswordActivity.class));
                    editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);
                    editor.apply();

                   // alertDialog.dismiss();

                } else {
                    hideKeyBoard(editTextText2);
                    showToastMessage("Wrong password");
                    editTextText2.setError("Wrong password");
                }
            }
        });


        imgWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String simpleAdminPassword = simpleSavedPassword.getString(Constants.simpleSavedPassword, "");

                String editTextText = editTextText2.getText().toString().trim();
                if (imgEnablePassword.equals(Constants.imgEnablePassword) || editTextText.equals(simpleAdminPassword)) {
                    hideKeyBoard(editTextText2);
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(intent);
                    editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);
                    editor.apply();
                   // alertDialog.dismiss();

                } else {
                    hideKeyBoard(editTextText2);
                    showToastMessage("Wrong password");
                    editTextText2.setError("Wrong password");
                }
            }
        });


        imgClearCatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String simpleAdminPassword = simpleSavedPassword.getString(Constants.simpleSavedPassword, "");

                String editTextText = editTextText2.getText().toString().trim();

                if (imgEnablePassword.equals(Constants.imgEnablePassword) || editTextText.equals(simpleAdminPassword)) {
                    hideKeyBoard(editTextText2);
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);

                    editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);
                    editor.apply();

                  //  alertDialog.dismiss();

                } else {
                    hideKeyBoard(editTextText2);
                    showToastMessage("Wrong password");
                    editTextText2.setError("Wrong password");
                }
            }
        });


        textSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String simpleAdminPassword = simpleSavedPassword.getString(Constants.simpleSavedPassword, "");
                String editTextText = editTextText2.getText().toString().trim();
                if (imgEnablePassword.equals(Constants.imgEnablePassword) || editTextText.equals(simpleAdminPassword)) {
                    alertDialog.dismiss();
                    hideKeyBoard(editTextText2);
                    editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);
                    editor.apply();
                    alertDialog.dismiss();
                } else {
                    editTextText2.setError("Wrong password");
                }


            }
        });


        textAppAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String simpleAdminPassword = simpleSavedPassword.getString(Constants.simpleSavedPassword, "");
                String editTextText = editTextText2.getText().toString().trim();

                if (imgEnablePassword.equals(Constants.imgEnablePassword) || editTextText.equals(simpleAdminPassword)) {


                    hideKeyBoard(editTextText2);
                    editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);

                    editor.putString(Constants.SAVE_NAVIGATION, Constants.SettingsPage);
                    editor.apply();

                    Intent myactivity = new Intent(SettingsActivity.this, AdditionalSettingsActivity.class);
                    startActivity(myactivity);
                    finish();

                    alertDialog.dismiss();


                } else {

                    hideKeyBoard(editTextText2);
                    showToastMessage("Wrong password");
                    editTextText2.setError("Wrong password");
                }
            }
        });


        imgMaintainace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String simpleAdminPassword = simpleSavedPassword.getString(Constants.simpleSavedPassword, "");
                String editTextText = editTextText2.getText().toString().trim();

                if (imgEnablePassword.equals(Constants.imgEnablePassword) || editTextText.equals(simpleAdminPassword)) {


                    hideKeyBoard(editTextText2);
                    editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);

                    editor.putString(Constants.SAVE_NAVIGATION, Constants.SettingsPage);
                    editor.apply();

                    Intent myactivity = new Intent(SettingsActivity.this, MaintenanceActivity.class);
                    startActivity(myactivity);
                    finish();

                   // alertDialog.dismiss();


                } else {

                    hideKeyBoard(editTextText2);
                    showToastMessage("Wrong password");
                    editTextText2.setError("Wrong password");
                }
            }
        });


        textExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                stopService(new Intent(SettingsActivity.this, SyncInterval.class));
                stopService(new Intent(SettingsActivity.this, OnChnageService.class));
                stopService(new Intent(SettingsActivity.this, OnChangeApiServiceSync.class));

                SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = my_DownloadClass.edit();
                editor.remove(Constants.SynC_Status);
                editor.remove(Constants.SAVED_CN_TIME);
                editor.apply();

                second_cancel_download();
                alertDialog.dismiss();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        finishAndRemoveTask();
                       // android.os.Process.killProcess(android.os.Process.myPid());
                      //  System.exit(1);
                        // user friendly kill
                        android.os.Process.killProcess(android.os.Process.myTid());
                    }
                }, 300);


            }
        });

        String isSavedEmail = simpleSavedPassword.getString(Constants.isSavedEmail, "");
        textForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyBoard(editTextText2);
                if (!isSavedEmail.isEmpty() && isValidEmail(isSavedEmail)) {
                    if (isConnected()) {
                        showPopChangePassowrdDialog();
                        alertDialog.dismiss();

                    } else {
                        showToastMessage("No internet Connection");
                    }

                } else {
                    showPopRedirectuser();
                    alertDialog.dismiss();
                }

            }
        });


        SharedPreferences mydownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorED = mydownloadClass.edit();

        textLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

*/
/*                SharedPreferences mydownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editorED = mydownloadClass.edit();
                editorED.clear();
                editorED.apply();

                SharedPreferences SIMPLE_SAVED_PASSWORD = getSharedPreferences(Constants.SIMPLE_SAVED_PASSWORD, Context.MODE_PRIVATE);
                SharedPreferences.Editor editorEDSIMPLE_SAVED_PASSWORD = SIMPLE_SAVED_PASSWORD.edit();
                editorEDSIMPLE_SAVED_PASSWORD.clear();
                editorEDSIMPLE_SAVED_PASSWORD.apply();*//*


                SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_sharedBiometric = sharedBiometric.edit();
                editor_sharedBiometric.remove(Constants.MY_TV_OR_APP_MODE);
                editor_sharedBiometric.remove(Constants.FIRST_TIME_APP_START);
                editor_sharedBiometric.apply();

                stopService(new Intent(SettingsActivity.this, SyncInterval.class));
                stopService(new Intent(SettingsActivity.this, OnChnageService.class));
                stopService(new Intent(SettingsActivity.this, OnChangeApiServiceSync.class));
                second_cancel_download();
                hideKeyBoard(editTextText2);

                Handler handler1 = new Handler(Looper.getMainLooper());

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent myactivity = new Intent(SettingsActivity.this, TvActivityOrAppMode.class);
                        startActivity(myactivity);
                        finish();

                    }
                }, 200);
            }
        });


        textLaunchOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(editTextText2);

                String simpleAdminPassword = simpleSavedPassword.getString(Constants.simpleSavedPassword, "");

                String editTextText = editTextText2.getText().toString().trim();

                SharedPreferences sharedBiometric = getApplicationContext().getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
                String getTvMode = sharedBiometric.getString(Constants.MY_TV_OR_APP_MODE, "");


                if (imgEnablePassword.equals(Constants.imgEnablePassword) || editTextText.equals(simpleAdminPassword)) {

                    if (getTvMode.equals(Constants.TV_Mode)) {

                        editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);

                        editorED.putString(Constants.Tapped_OnlineORoffline, Constants.tapped_launchOnline);
                        //  editorED.remove(Constants.getFolderClo);
                        // editorED.remove(Constants.getFolderSubpath);
                        //   editorED.remove(Constants.syncUrl);
                        editorED.apply();


                        editor.putString(Constants.imgAllowLunchFromOnline, "imgAllowLunchFromOnline");
                        editor.apply();


                        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                        startActivity(intent);
                        finish();

                        alertDialog.dismiss();

                    } else {


                        editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);

                        editor.remove(Constants.imgAllowLunchFromOnline);
                        editor.apply();

                        editorED.remove(Constants.Tapped_OnlineORoffline);
                        //  editorED.remove(Constants.getFolderClo);
                        // editorED.remove(Constants.getFolderSubpath);
                        //   editorED.remove(Constants.syncUrl);
                        editorED.apply();

                        Intent intent = new Intent(getApplicationContext(), Splash.class);
                        startActivity(intent);
                        finish();

                        alertDialog.dismiss();


                    }


                } else {
                    hideKeyBoard(editTextText2);
                    editTextText2.setError("Wrong password");
                    showToastMessage("Wrong password");
                }

            }
        });

        textLaunchOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyBoard(editTextText2);

                String simpleAdminPassword = simpleSavedPassword.getString(Constants.simpleSavedPassword, "");

                String editTextText = editTextText2.getText().toString().trim();

                if (imgEnablePassword.equals(Constants.imgEnablePassword) || editTextText.equals(simpleAdminPassword)) {


                    editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);

                    editorED.putString(Constants.Tapped_OnlineORoffline, Constants.tapped_launchOffline);
                    //  editorED.remove(Constants.getFolderClo);
                    // editorED.remove(Constants.getFolderSubpath);
                    //   editorED.remove(Constants.syncUrl);
                    editorED.apply();

                    editor.remove(Constants.imgAllowLunchFromOnline);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                    startActivity(intent);
                    finish();

                    alertDialog.dismiss();


                } else {
                    hideKeyBoard(editTextText2);
                    editTextText2.setError("Wrong password");
                    showToastMessage("Wrong password");
                }

            }
        });


        String get_AppMode = sharedBiometric.getString(Constants.MY_TV_OR_APP_MODE, "");


        textHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(editTextText2);
                moveTaskToBack(true);

            }
        });


        textLoginAdmin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(editTextText2);

                String simpleAdminPassword = simpleSavedPassword.getString(Constants.simpleSavedPassword, "");
                String editTextText = editTextText2.getText().toString().trim();


                if (editTextText.equals(simpleAdminPassword)) {
                    alertDialog.dismiss();
                    hideKeyBoard(editTextText2);

                    editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);
                    editor.apply();

                } else if (imgEnablePassword.equals(Constants.imgEnablePassword)) {
                    alertDialog.dismiss();
                    hideKeyBoard(editTextText2);

                    editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);
                    editor.apply();

                } else {
                    hideKeyBoard(editTextText2);
                    editTextText2.setError("Wrong password");
                }


            }
        });


        alertDialog.show();
    }


    @SuppressLint("MissingInflatedId")
    private void showPopRedirectuser() {

        CustomRedirectEmailLayoutBinding binding = CustomRedirectEmailLayoutBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(binding.getRoot());
        final AlertDialog alertDialog = builder.create();

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);


        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        final TextView textForGetPassword = binding.textGoToPassowrdPage;
        final TextView textTitle = binding.textTitle;
        final TextView textDescription = binding.textDescription;
        final TextView textOkayBtn = binding.textOkayBtn;
        final ImageView imgCloseDialog2 = binding.imgCloseDialogForegetPassword;
        final View divider2 = binding.divider56;
        final ConstraintLayout consMainAlert_sub_layout = binding.consMainAlertSubLayout;


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (preferences.getBoolean("darktheme", false)) {


            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout);
            textTitle.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textDescription.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textForGetPassword.setTextColor(getResources().getColor(R.color.white));


            //  textLogoutButton.setBackgroundResource(R.drawable.card_design_darktheme_outline_pop_layout);
            textOkayBtn.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);

            setDrawableColor(imgCloseDialog2, R.drawable.ic_close_24, R.color.dark_light_gray_pop);

            divider2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_gray_pop));


        }


        SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedBiometric.edit();

        textOkayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString(Constants.SAVE_NAVIGATION, Constants.SettingsPage);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                startActivity(intent);
                finish();

            }
        });


        imgCloseDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showExitConfirmationDialog();
                // hideKeyBoard(editTextInputUrl);
            }
        });

        alertDialog.show();
    }


    @SuppressLint("MissingInflatedId")
    private void showPopChangePassowrdDialog() {

        CustomForgetPasswordEmailLayoutBinding binding = CustomForgetPasswordEmailLayoutBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(binding.getRoot());
        final AlertDialog alertDialog = builder.create();

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);


        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        final TextView editTextInputUrl = binding.eitTextEnterPassword;
        final TextView textContinuPassword = binding.textContinuPassword;
        final ImageView imgCloseDialog2 = binding.imgCloseDialogForegetPassword;
        final TextView textForGetPassword = binding.textForGetPassword;
        final View divider2 = binding.divider2;
        final ConstraintLayout consMainAlert_sub_layout = binding.consMainAlertSubLayout;


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (preferences.getBoolean("darktheme", false)) {


            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout);
            textForGetPassword.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textContinuPassword.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            editTextInputUrl.setTextColor(getResources().getColor(R.color.white));


            //  textLogoutButton.setBackgroundResource(R.drawable.card_design_darktheme_outline_pop_layout);
            textContinuPassword.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);

            setDrawableColor(imgCloseDialog2, R.drawable.ic_close_24, R.color.dark_light_gray_pop);

            divider2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_gray_pop));


        }


        SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
        String imgIsemailVisbile = sharedBiometric.getString(Constants.imagEnableEmailVisisbility, "");

        SharedPreferences simpleSavedPassword = getSharedPreferences(Constants.SIMPLE_SAVED_PASSWORD, Context.MODE_PRIVATE);
        String isSavedEmail = simpleSavedPassword.getString(Constants.isSavedEmail, "");


        if (imgIsemailVisbile.equals(Constants.imagEnableEmailVisisbility)) {
            if (!isSavedEmail.isEmpty()) {
                editTextInputUrl.setText(isSavedEmail + "");
                // textForGetPassword.setText("Default Email");
                divider2.setVisibility(View.VISIBLE);
                editTextInputUrl.setVisibility(View.VISIBLE);
            }
        } else {
            editTextInputUrl.setEnabled(true);
            //   textForGetPassword.setText("Default Email");
            divider2.setVisibility(View.GONE);
            editTextInputUrl.setVisibility(View.GONE);
        }


        String simpleAdminPassword = simpleSavedPassword.getString(Constants.simpleSavedPassword, "");

        textContinuPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSavedEmail.isEmpty() && isValidEmail(isSavedEmail)) {
                    if (isConnected()) {
                        sendMessage(isSavedEmail, simpleAdminPassword);
                        alertDialog.dismiss();

                    } else {
                        showToastMessage("No internet Connection");
                    }

                } else {
                    showToastMessage("Default Email Reminder not found");
                }
            }
        });


        imgCloseDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showExitConfirmationDialog();
                // hideKeyBoard(editTextInputUrl);
            }
        });

        alertDialog.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Start the WebviewActivity
                Intent intent = new Intent(this, WebActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private void second_cancel_download() {
        try {
            SharedPreferences myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE);
            long download_ref = myDownloadClass.getLong(Constants.downloadKey, -15);


            if (download_ref != -15) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(download_ref);
                Cursor c = ((DownloadManager) getSystemService(DOWNLOAD_SERVICE)).query(query);
                if (c.moveToFirst()) {
                    DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    manager.remove(download_ref);
                    SharedPreferences.Editor editor = myDownloadClass.edit();
                    editor.remove(Constants.downloadKey);
                    editor.apply();
                }
            }
        } catch (Exception ignored) {
        }
    }


    @SuppressLint("MissingInflatedId")
    private void show_Pop_Up_Email_Sent_Sucessful(String title, String body) {
        // Inflate the custom layout
        CustomEmailSucessLayoutBinding binding = CustomEmailSucessLayoutBinding.inflate(getLayoutInflater());

        // Create AlertDialog Builder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(binding.getRoot());
        alertDialogBuilder.setCancelable(false);

        // Create the AlertDialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Set background drawable to be transparent
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Apply actions to views in the binding

        TextView textEmailSendOkayBtn = binding.textEmailSendOkayBtn;
        TextView textBodyMessage = binding.textBodyMessage;
        TextView textSucessful = binding.textSucessful;
        ImageView imagSucessful = binding.imagSucessful;
        ConstraintLayout consMainAlert_sub_layout = binding.consMainAlertSubLayout;


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (preferences.getBoolean("darktheme", false)) {


            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout);
            textSucessful.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textBodyMessage.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));


            textEmailSendOkayBtn.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
            textEmailSendOkayBtn.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);

            Drawable drawable_imagSucessful = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_email_read_24);
            if (drawable_imagSucessful != null) {
                drawable_imagSucessful.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_gray_pop), PorterDuff.Mode.SRC_IN);
                imagSucessful.setImageDrawable(drawable_imagSucessful);
            }

        }


        binding.textEmailSendOkayBtn.setOnClickListener(view -> {

            alertDialog.dismiss();
            showExitConfirmationDialog();
        });

        binding.textSucessful.setText(title);
        binding.textBodyMessage.setText(body);

        // Show the AlertDialog
        alertDialog.show();
    }


    private void showToastMessage(String messages) {
        try {
            Toast.makeText(getApplicationContext(), messages, Toast.LENGTH_SHORT).show();
        } catch (Exception ignored) {
        }
    }

    private void hideKeyBoard(EditText editText) {
        try {
            editText.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);


            SwitchPreference swipe_switch = findPreference(Constants.swiperefresh);
            EditTextPreference serverUrl_field = findPreference(Constants.surl);


            SwitchPreference location_switch = findPreference(Constants.geolocation);
            SwitchPreference dark_switch = findPreference(Constants.darktheme);
            SwitchPreference night_switch = findPreference(Constants.nightmode);
            SwitchPreference fullscr_switch = findPreference(Constants.fullscreen);
            SwitchPreference nativ_loader_switch = findPreference(Constants.nativeload);
            SwitchPreference blockAds_switch = findPreference(Constants.blockAds);
            SwitchPreference immersive_switch = findPreference(Constants.immersive_mode);
            SwitchPreference permissions_switch = findPreference(Constants.permission_query);
            SwitchPreference lastpage_switch = findPreference(Constants.loadLastUrl);
            SwitchPreference autoToolbar_switch = findPreference(Constants.autohideToolbar);
            SwitchPreference appModeOrTvMode = findPreference(Constants.appModeOrTvMode);
            SwitchPreference hideQRCode = findPreference(Constants.hideQRCode);

            SwitchPreference shwoFloatingButton = findPreference(Constants.shwoFloatingButton);
            SwitchPreference hide_bottom_switch = findPreference(Constants.hidebottombar);
            SwitchPreference hide_drawer_icon = findPreference(Constants.hide_drawer_icon);
            SwitchPreference useOfflineFolderOrNot = findPreference(Constants.useOfflineFolderOrNot);
            SwitchPreference enableCacheMode = findPreference(Constants.enableCacheMode);

            Preference additionalSettingsPreference = findPreference(Constants.additional_settings_key);




            SharedPreferences sharedBiometric = getContext().getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
            SharedPreferences myDownloadClass = getContext().getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedBiometric.edit();
            SharedPreferences.Editor editor3333 = myDownloadClass.edit();


            String get_AppMode = sharedBiometric.getString(Constants.MY_TV_OR_APP_MODE, "");
            String get_hideQRCode = sharedBiometric.getString(Constants.HIDE_QR_CODE, "");

            String get_drawerMenu = sharedBiometric.getString(Constants.HIDE_DRAWER_ICON, "");

            String get_showFloatingButton = sharedBiometric.getString(Constants.SHOW_FLOATING_BAR, "");
            String get_auto_hide_toolbar = sharedBiometric.getString(Constants.AUTO_HIDE_TOOL_BAR, "");
            String get_hide_bottomBar = sharedBiometric.getString(Constants.HIDE_BOTTOM_BAR, "");
            String enable_CacheMode = sharedBiometric.getString(Constants.Enable_CacheMode, "");

            String get_useOfflineFolderOrNot = sharedBiometric.getString(Constants.USE_OFFLINE_FOLDER, "");


            SharedPreferences preferencesAppSettings = PreferenceManager.getDefaultSharedPreferences(requireContext());
            SharedPreferences.Editor editorPref = preferencesAppSettings.edit();

            if (get_AppMode.equals(Constants.TV_Mode)) {

                appModeOrTvMode.setChecked(true);
                appModeOrTvMode.setTitle("TV App Mode");
                editor.putString(Constants.MY_TV_OR_APP_MODE, Constants.TV_Mode);
                editor.apply();


                // App setting

        */
/*        if (preferencesAppSettings.getBoolean(Constants.hidebottombar, false)) {
                    hide_bottom_switch.setChecked(true);
                }

                if (preferencesAppSettings.getBoolean(Constants.immersive_mode, false)) {
                    immersive_switch.setChecked(true);
                }

                if (preferencesAppSettings.getBoolean(Constants.fullscreen, false)) {
                    fullscr_switch.setChecked(true);
                }
*//*



            } else {

                //   WebActivity.ChangeListener = true;
                appModeOrTvMode.setChecked(false);
                appModeOrTvMode.setTitle("Mobile App Mode");
                editor.putString(Constants.MY_TV_OR_APP_MODE, Constants.App_Mode);
                editor.apply();


                // App setting

 */
/*                if (preferencesAppSettings.getBoolean(Constants.hidebottombar, false)) {
                     hide_bottom_switch.setChecked(false);
                 }

                 if (preferencesAppSettings.getBoolean(Constants.immersive_mode, false)) {
                     immersive_switch.setChecked(false);
                 }

                 if (preferencesAppSettings.getBoolean(Constants.fullscreen, false)) {
                     fullscr_switch.setChecked(false);
                 }
*//*


            }


            if (enable_CacheMode.equals(Constants.Enable_CacheMode)) {

                enableCacheMode.setTitle("Disable Cache Mode");
            } else {
                enableCacheMode.setTitle("Enable Cache Mode");
            }


            if (get_auto_hide_toolbar.equals(Constants.AUTO_HIDE_TOOL_BAR)) {
                autoToolbar_switch.setTitle("Auto Hide Toolbar ON");
            } else {
                autoToolbar_switch.setTitle("Auto Hide Toolbar Off");
            }


            if (get_hide_bottomBar.equals(Constants.HIDE_BOTTOM_BAR)) {
                hide_bottom_switch.setTitle("Hide Bottom Bar ( TV  or TouchScreen Mode)");
            } else {
                hide_bottom_switch.setTitle("Show Bottom Bar ( TV  or TouchScreen Mode)");
            }


            if (get_hideQRCode.equals(Constants.HIDE_QR_CODE)) {
                hideQRCode.setTitle("Hide QR Code");
            } else {
                hideQRCode.setTitle("Show QR Code");
            }


            if (get_useOfflineFolderOrNot.equals(Constants.USE_OFFLINE_FOLDER)) {
                useOfflineFolderOrNot.setTitle("If not connected use App offline Folder");

            } else {
                useOfflineFolderOrNot.setTitle("If not connected use App offline Page");


            }


            if (get_showFloatingButton.equals(Constants.SHOW_FLOATING_BAR)) {
                shwoFloatingButton.setTitle("Hide Floating Menu (TV or TouchScreen Mode)");
            } else {

                shwoFloatingButton.setTitle("Show Floating Menu (TV or TouchScreen Mode)");

            }


            if (get_drawerMenu.equals(Constants.HIDE_DRAWER_ICON)) {
                hide_drawer_icon.setTitle("Show Bottom Menu Icon (TV or TouchScreen mode)");
            } else {

                hide_drawer_icon.setTitle("Hide Bottom Menu Icon (TV or TouchScreen mode)");

            }


            if (appModeOrTvMode != null) {


                appModeOrTvMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                    @Override
                    public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                        boolean isItemOn = (Boolean) isChanged;
                        if (isItemOn) {

                            WebActivity.ChangeListener = true;
                            appModeOrTvMode.setTitle("TV App Mode");

                            editor.putString(Constants.imgStartAppRestartOnTvMode, Constants.imgStartAppRestartOnTvMode);
                            editor.putString(Constants.MY_TV_OR_APP_MODE, Constants.TV_Mode);
                            editor.apply();


                            showPopForTestComfirmantion();


                        } else {

                            appModeOrTvMode.setTitle("Mobile App Mode");
                            WebActivity.ChangeListener = true;

                            editor.putString(Constants.MY_TV_OR_APP_MODE, Constants.App_Mode);
                            editor.remove(Constants.imgStartAppRestartOnTvMode);
                            editor.apply();

                        }
                        return true;
                    }
                });

            }


            if (additionalSettingsPreference != null) {
                additionalSettingsPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {

                        Intent intent = new Intent(getActivity(), AdditionalSettingsActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                        editor.putString(Constants.Did_User_Input_PassWord, Constants.Did_User_Input_PassWord);
                        editor.putString(Constants.SAVE_NAVIGATION, Constants.SettingsPage);
                        editor.apply();

                        return true;
                    }
                });
            }


            if (autoToolbar_switch != null) {
                autoToolbar_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                    @Override
                    public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                        boolean isItemOn = (Boolean) isChanged;
                        if (isItemOn) {
                            WebActivity.ChangeListener = true;
                            autoToolbar_switch.setTitle("Auto Hide Toolbar ON");
                            editor.putString(Constants.AUTO_HIDE_TOOL_BAR, Constants.AUTO_HIDE_TOOL_BAR);
                            editor.apply();


                        } else {
                            WebActivity.ChangeListener = true;
                            autoToolbar_switch.setTitle("Auto Hide Toolbar Off");

                            editor.remove(Constants.AUTO_HIDE_TOOL_BAR);
                            editor.apply();

                        }
                        return true;
                    }
                });

            }


            if (enableCacheMode != null) {
                enableCacheMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                    @Override
                    public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                        boolean isItemOn = (Boolean) isChanged;
                        if (isItemOn) {

                            enableCacheMode.setTitle("Disable Cache Mode");
                            editor.putString(Constants.Enable_CacheMode, Constants.Enable_CacheMode);
                            editor.apply();

                        } else {
                            enableCacheMode.setTitle("Enable Cache Mode");
                            editor.remove(Constants.Enable_CacheMode);
                            editor.apply();

                        }

                        return true;
                    }
                });

            }


            if (useOfflineFolderOrNot != null) {
                useOfflineFolderOrNot.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                    @Override
                    public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                        boolean isItemOn = (Boolean) isChanged;
                        if (isItemOn) {

                            WebActivity.ChangeListener = true;
                            useOfflineFolderOrNot.setTitle("If not connected use App offline Folder");
                            editor.putString(Constants.USE_OFFLINE_FOLDER, Constants.USE_OFFLINE_FOLDER);
                            editor.apply();


                        } else {

                            WebActivity.ChangeListener = true;
                            useOfflineFolderOrNot.setTitle("If not connected use App offline Page");
                            editor.remove(Constants.USE_OFFLINE_FOLDER);
                            editor.apply();


                        }
                        return true;
                    }
                });

            }


            if (hide_drawer_icon != null) {
                hide_drawer_icon.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                    @Override
                    public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                        boolean isItemOn = (Boolean) isChanged;
                        if (isItemOn) {

                            hide_drawer_icon.setTitle("Show Bottom Menu Icon (TV or TouchScreen mode)");
                            editor.putString(Constants.HIDE_DRAWER_ICON, Constants.HIDE_DRAWER_ICON);
                            editor.apply();

                        } else {
                            hide_drawer_icon.setTitle("Hide Bottom Menu Icon (TV or TouchScreen mode)");

                            editor.remove(Constants.HIDE_DRAWER_ICON);
                            editor.apply();
                        }
                        return true;
                    }
                });

            }


            /// start of qr code logic

            if (hideQRCode != null) {
                hideQRCode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                    @Override
                    public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                        boolean isItemOn = (Boolean) isChanged;
                        if (isItemOn) {
                            // WebActivity.ChangeListener = true;
                            hideQRCode.setTitle("Hide QR Code");
                            editor.putString(Constants.HIDE_QR_CODE, Constants.HIDE_QR_CODE);
                            editor.apply();

                        } else {
                            // WebActivity.ChangeListener = true;
                            hideQRCode.setTitle("Show QR Code");
                            editor.remove(Constants.HIDE_QR_CODE);
                            editor.apply();
                        }
                        return true;
                    }
                });


                if (shwoFloatingButton != null) {
                    shwoFloatingButton.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                        @Override
                        public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                            boolean isItemOn = (Boolean) isChanged;
                            if (isItemOn) {
                                WebActivity.ChangeListener = true;

                                shwoFloatingButton.setTitle("Hide Floating Menu (TV or TouchScreen Mode)");


                                editor.putString(Constants.SHOW_FLOATING_BAR, Constants.SHOW_FLOATING_BAR);
                                editor.apply();

                            } else {
                                WebActivity.ChangeListener = true;

                                shwoFloatingButton.setTitle("Show Floating Menu (TV or TouchScreen Mode)");

                                editor.remove(Constants.SHOW_FLOATING_BAR);
                                editor.apply();
                            }
                            return true;
                        }
                    });


                    /// the end of qr code logic

                    if (hide_bottom_switch != null) {
                        hide_bottom_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                            @Override
                            public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                                boolean isItemOn = (Boolean) isChanged;
                                if (isItemOn) {

                                    WebActivity.ChangeListener = true;

                                    editor.putString(Constants.HIDE_BOTTOM_BAR, Constants.HIDE_BOTTOM_BAR);
                                    editor.apply();
                                    hide_bottom_switch.setTitle("Hide Bottom Bar ( TV or TouchScreen Mode)");


                                } else {

                                    hide_bottom_switch.setTitle("Show Bottom Bar ( TV or TouchScreen Mode)");
                                    WebActivity.ChangeListener = true;
                                    editor.remove(Constants.HIDE_BOTTOM_BAR);
                                    editor.apply();

                                }
                                return true;
                            }
                        });

                        if (swipe_switch != null) {
                            swipe_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                                @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                                @Override
                                public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                                    boolean isItemOn = (Boolean) isChanged;
                                    if (isItemOn) {
                                        WebActivity.ChangeListener = true;
                                    } else {
                                        WebActivity.ChangeListener = true;
                                    }
                                    return true;
                                }
                            });

                            if (lastpage_switch != null) {
                                lastpage_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                                    @Override
                                    public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                                        boolean isItemOn = (Boolean) isChanged;
                                        if (isItemOn) {
                                            WebActivity.ChangeListener = true;
                                        } else {
                                            WebActivity.ChangeListener = true;
                                        }
                                        return true;
                                    }
                                });
                            }

                            if (location_switch != null) {
                                location_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                                    @Override
                                    public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                                        boolean isItemOn = (Boolean) isChanged;
                                        if (isItemOn) {
                                            WebActivity.ChangeListener = true;
                                        } else {
                                            WebActivity.ChangeListener = true;
                                        }
                                        return true;
                                    }
                                });
                            }


                            if (dark_switch != null) {
                                dark_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                                    @Override
                                    public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                                        boolean isItemOn = (Boolean) isChanged;
                                        if (isItemOn) {
                                            WebActivity.ChangeListener = true;

                                        } else {
                                            WebActivity.ChangeListener = true;
                                        }
                                        return true;
                                    }
                                });
                            }

                            if (night_switch != null) {
                                night_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                                    @Override
                                    public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                                        boolean isItemOn = (Boolean) isChanged;
                                        if (isItemOn) {
                                            WebActivity.ChangeListener = true;
                                        } else {
                                            WebActivity.ChangeListener = true;
                                        }
                                        return true;
                                    }
                                });
                                if (fullscr_switch != null) {
                                    fullscr_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                                        @Override
                                        public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                                            boolean isItemOn = (Boolean) isChanged;
                                            if (isItemOn) {
                                                WebActivity.ChangeListener = true;
                                            } else {
                                                WebActivity.ChangeListener = true;
                                            }
                                            return true;
                                        }
                                    });


                                    if (nativ_loader_switch != null) {
                                        nativ_loader_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                                            @Override
                                            public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                                                boolean isItemOn = (Boolean) isChanged;
                                                if (isItemOn) {
                                                    WebActivity.ChangeListener = true;
                                                } else {
                                                    WebActivity.ChangeListener = true;
                                                }
                                                return true;
                                            }
                                        });
                                        if (blockAds_switch != null) {
                                            blockAds_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                                                @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                                                @Override
                                                public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                                                    boolean isItemOn = (Boolean) isChanged;
                                                    if (isItemOn) {
                                                        WebActivity.ChangeListener = true;
                                                    } else {
                                                        WebActivity.ChangeListener = true;
                                                    }
                                                    return true;
                                                }
                                            });

                                            if (immersive_switch != null) {
                                                immersive_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                                                    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                                                    @Override
                                                    public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                                                        boolean isItemOn = (Boolean) isChanged;
                                                        if (isItemOn) {
                                                            WebActivity.ChangeListener = true;
                                                        } else {
                                                            WebActivity.ChangeListener = true;
                                                        }
                                                        return true;
                                                    }
                                                });

                                                if (permissions_switch != null) {
                                                    permissions_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                                                        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                                                        @Override
                                                        public boolean onPreferenceChange(Preference arg0, Object isChanged) {
                                                            boolean isItemOn = (Boolean) isChanged;
                                                            if (isItemOn) {
                                                                WebActivity.ChangeListener = true;
                                                            } else {
                                                                WebActivity.ChangeListener = true;

                                                            }
                                                            return true;
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }



                    if (!constants.ShowServerUrlSetUp) {

                        try {
                            serverUrl_field.setVisible(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    if (!constants.ShowToolbar) {

                        try {
                            autoToolbar_switch.setVisible(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (!constants.ShowBottomBar) {

                            try {
                                hide_bottom_switch.setVisible(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }


            }
        }

        @SuppressLint("InflateParams")
        private void showPopForTestComfirmantion() {


            CustomSettingsPageBinding binding = CustomSettingsPageBinding.inflate(getLayoutInflater());
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

            builder.setView(binding.getRoot());

            final AlertDialog alertDialog = builder.create();

            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);

            // Set the background of the AlertDialog to be transparent
            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            final ConstraintLayout consMainAlert_sub_layout = binding.consMainAlertSubLayout;
            final TextView textForGetPassword = binding.textLoginAdmin2;
            final TextView textYourUrlTest = binding.textYourUrlTest;
            final TextView textYourUrlTest2 = binding.textYourUrlTest2;
            final TextView textYourUrlTest3 = binding.textYourUrlTest3;
            final TextView textYourUrlTest4 = binding.textYourUrlTest4;
            final TextView textYourUrlTest5 = binding.textYourUrlTest5;
            final TextView textYourUrlTest6 = binding.textYourUrlTest6;
            final TextView textYourUrlTest7 = binding.textYourUrlTest7;
            final TextView textYourUrlTest8 = binding.textYourUrlTest8;
            final TextView textYourUrlTest9 = binding.textYourUrlTest9;
            final TextView textLoginAdmin2 = binding.textLoginAdmin2;
            final ImageView imageView32 = binding.imageView32;
            final ImageView imageView31 = binding.imageView31;
            final ImageView imageView30 = binding.imageView30;
            final ImageView imageView26 = binding.imageView26;
            final ImageView imageView27 = binding.imageView27;
            final ImageView imageView28 = binding.imageView28;
            final ImageView imageView29 = binding.imageView29;


            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());

            if (preferences.getBoolean("darktheme", false)) {


                consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout);
                textForGetPassword.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
                textYourUrlTest.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
                textYourUrlTest2.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
                textYourUrlTest3.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
                textYourUrlTest4.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
                textYourUrlTest5.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
                textYourUrlTest6.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
                textYourUrlTest7.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
                textYourUrlTest8.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));
                textYourUrlTest9.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));


                //  textLogoutButton.setBackgroundResource(R.drawable.card_design_darktheme_outline_pop_layout);
                textLoginAdmin2.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);


                setDrawableColor22(imageView32, R.drawable.ic_arrow_move_front, R.color.dark_light_gray_pop);
                setDrawableColor22(imageView31, R.drawable.ic_arrow_move_front, R.color.dark_light_gray_pop);
                setDrawableColor22(imageView30, R.drawable.ic_arrow_move_front, R.color.dark_light_gray_pop);
                setDrawableColor22(imageView26, R.drawable.ic_arrow_move_front, R.color.dark_light_gray_pop);
                setDrawableColor22(imageView27, R.drawable.ic_arrow_move_front, R.color.dark_light_gray_pop);
                setDrawableColor22(imageView28, R.drawable.ic_arrow_move_front, R.color.dark_light_gray_pop);
                setDrawableColor22(imageView29, R.drawable.ic_arrow_move_front, R.color.dark_light_gray_pop);


            }


            textForGetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();

                }
            });

            alertDialog.show();
        }


        private void setDrawableColor22(ImageView imageView, int drawableId, int colorId) {
            Drawable drawable = ContextCompat.getDrawable(requireContext(), drawableId);
            if (drawable != null) {
                drawable.setColorFilter(ContextCompat.getColor(requireContext(), colorId), PorterDuff.Mode.SRC_IN);
                imageView.setImageDrawable(drawable);
            }
        }

    }

}

*/
