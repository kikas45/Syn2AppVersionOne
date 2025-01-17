package sync2app.com.syncapplive.additionalSettings.cloudAppsync.schedules;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.bumptech.glide.Glide;

import java.io.File;

import io.paperdb.Paper;
import sync2app.com.syncapplive.WebViewPage;
import sync2app.com.syncapplive.R;
import sync2app.com.syncapplive.additionalSettings.AdditionalSettingsActivity;
import sync2app.com.syncapplive.additionalSettings.autostartAppOncrash.Methods;
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.util.Common;
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.util.MethodsSchedule;
import sync2app.com.syncapplive.additionalSettings.utils.Constants;
import sync2app.com.syncapplive.databinding.ActivityScheduleMediaBinding;
import sync2app.com.syncapplive.databinding.CustomSchedulePassowrdBinding;

public class ScheduleMediaActivity extends AppCompatActivity {
    private ActivityScheduleMediaBinding binding;

    private SharedPreferences preferences;


    /// declear global variables
    private TextView textGoTowebview;
    private TextView addScheduleCard;
    private TextView allSchedulesCard;
    private TextView textScheduleIndicator;
    private ImageView closeBs;
    private ConstraintLayout parentConatiner;
    private SwitchCompat scheduleSwitch;

    @SuppressLint({"SetTextI18n", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScheduleMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedBiometri222c = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
        String getState = sharedBiometri222c.getString(Constants.ENABLE_LANDSCAPE_MODE, "").toString();
/*        if (getState.equals(Constants.ENABLE_LANDSCAPE_MODE)) {
            setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
        *//*    if (getState.isEmpty()) {
                setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }*//*
        }*/



        if (getState.equals(Constants.ENABLE_LANDSCAPE_MODE)) {
            setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        // init variables
        textGoTowebview = binding.textGoTowebview;
        addScheduleCard = binding.addScheduleCard;
        allSchedulesCard = binding.allSchedulesCard;
        textScheduleIndicator = binding.textScheduleIndicator;
        parentConatiner = binding.parentConatiner;
        scheduleSwitch = binding.scheduleSwitch;
        closeBs = binding.closeBs;


        // set up dark theme
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.getBoolean("darktheme", false)) {

            // set windows

            getWindow().setStatusBarColor(Color.parseColor("#171616"));

            getWindow().setNavigationBarColor(Color.parseColor("#171616"));

            WindowInsetsControllerCompat insetsController = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
            insetsController.setAppearanceLightStatusBars(false);
            insetsController.setAppearanceLightNavigationBars(false);

            parentConatiner.setBackgroundColor(getResources().getColor(R.color.dark_layout_for_ui));
            setDrawableColor(binding.closeBs, R.drawable.ic_baseline_arrow, R.color.white);
            setDrawableColor(binding.imageView40, R.drawable.ic_arrow_move_front, R.color.white);
            setDrawableColor(binding.imageView4, R.drawable.ic_arrow_move_front, R.color.white);
            setDrawableColor(binding.imageView47, R.drawable.ic_arrow_move_front, R.color.white);
            setDrawableColor(binding.imageView41, R.drawable.ic_schedule, R.color.deep_blue_light_extra);
            setDrawableColor(binding.imageView45, R.drawable.ic_schedule, R.color.deep_blue_light_extra);
            setDrawableColor(binding.imageView46, R.drawable.ic_schedule, R.color.deep_blue_light_extra);
            setDrawableColor(binding.imageView48, R.drawable.ic_app_or_tv_tv_24, R.color.deep_blue_light_extra);


            setTextColor(binding.textView42, R.color.white);
            setTextColor(binding.addScheduleCard, R.color.white);
            setTextColor(binding.allSchedulesCard, R.color.white);
            setTextColor(binding.textScheduleIndicator, R.color.white);
            setTextColor(binding.textGoTowebview, R.color.white);


            // Define colors for different states
            ColorStateList thumbColorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_checked},
                            new int[]{-android.R.attr.state_checked}
                    },
                    new int[]{
                            Color.LTGRAY,  // Color when checked
                            Color.LTGRAY   // Color when unchecked
                    }
            );

            ColorStateList trackColorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_enabled},
                            new int[]{-android.R.attr.state_enabled}
                    },
                    new int[]{
                            Color.DKGRAY,  // Color when enabled
                            Color.LTGRAY   // Color when disabled
                    }
            );


            scheduleSwitch.setThumbTintList(thumbColorStateList);
            scheduleSwitch.setTrackTintList(trackColorStateList);


            // for divider
            setDividerColor(binding.divider21, R.color.light_gray);
            setDividerColor(binding.divider32, R.color.light_gray);
            setDividerColor(binding.divider50, R.color.light_gray);
            setDividerColor(binding.divider34, R.color.light_gray);
            setDividerColor(binding.divider49, R.color.light_gray);


        }


        // Setup folder path

        SharedPreferences myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
        String company = myDownloadClass.getString(Constants.getFolderClo, "");
        String license = myDownloadClass.getString(Constants.getFolderSubpath, "");

        /// String company = "CLO";
        /// String license = "DE_MO_2021001";


        String finalFolderPath = "/" + company + "/" + license;
        String syn2AppLive = "Syn2AppLive";

        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/Download/" + syn2AppLive + finalFolderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Setup click listeners for the cards
        addScheduleCard.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddNewSchedule.class);
            startActivity(intent);
            finish();
        });

        allSchedulesCard.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ScheduleList.class);
            startActivity(intent);
            finish();
        });


        closeBs.setOnClickListener(view -> {
            funcloseActivity();
        });


        textGoTowebview.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), WebViewPage.class);
            startActivity(intent);
            finishAffinity();
            finish();
        });


        String savedState = Paper.book().read(Common.set_schedule_key, "Offline");

        if (Common.schedule_online.equals(savedState)) {
            textScheduleIndicator.setText("Use Online Schedule");
            scheduleSwitch.setChecked(true);
            Paper.book().write(Common.set_schedule_key, Common.schedule_online);

        } else {
            textScheduleIndicator.setText("Use Local Schedule");
            scheduleSwitch.setChecked(false);
            Paper.book().write(Common.set_schedule_key, Common.schedule_offline);
        }

        scheduleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Paper.book().write(Common.set_schedule_key, Common.schedule_online);
                textScheduleIndicator.setText("Use Online Schedule");
            } else {
                // is there no way to remove the key
                showPopChangePassowrdDialog();

            }
        });

        // initialize schedule settings
        MethodsSchedule.setPersistentDefaults();


        //add exception
        Methods.addExceptionHandler(this);


        // set up background image
        SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
        String get_imgToggleImageBackground = sharedBiometric.getString(Constants.imgToggleImageBackground, "");
        String get_imageUseBranding = sharedBiometric.getString(Constants.imageUseBranding, "");
        if (get_imgToggleImageBackground.equals(Constants.imgToggleImageBackground) && get_imageUseBranding.equals(Constants.imageUseBranding)) {
            loadBackGroundImage();
        }


    }

    private void setDividerColor(View divider, int colorId) {
        divider.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), colorId));
    }


    private void setTextColor(TextView textView, int colorId) {
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), colorId));
    }


    private void setDrawableColor(ImageView imageView, int drawableId, int colorId) {
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), drawableId);
        if (drawable != null) {
            drawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), colorId), PorterDuff.Mode.SRC_IN);
            imageView.setImageDrawable(drawable);
        }
    }


    private void loadBackGroundImage() {
        String fileTypes = "app_background.png";
        SharedPreferences myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
        String getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "");
        String getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "");

        String pathFolder = "/" + getFolderClo + "/" + getFolderSubpath + "/" + Constants.App + "/" + "Config";
        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" + Constants.Syn2AppLive + "/" + pathFolder;
        File file = new File(folder, fileTypes);

        if (file.exists()) {
            Glide.with(this).load(file).centerCrop().into(binding.backgroundImage);
        }
    }


    private void funcloseActivity() {
        Intent intent = new Intent(getApplicationContext(), AdditionalSettingsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        funcloseActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }


    @SuppressLint("MissingInflatedId")
    private void showPopChangePassowrdDialog() {

        CustomSchedulePassowrdBinding bindingCm = CustomSchedulePassowrdBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(bindingCm.getRoot());
        final AlertDialog alertDialog = builder.create();

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);


        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        }


        final EditText editTextInputUrl = bindingCm.eitTextEnterPassword;
        final TextView textContinuPassword = bindingCm.textContinuPassword;
        final ImageView imgCloseDialog2 = bindingCm.imgCloseDialogForegetPassword;
        final ImageView imgToggle = bindingCm.imgToggle;
        final ImageView imgToggleNzotVisible = bindingCm.imgToggleNzotVisible;
        final TextView textForGetPassword = bindingCm.textForGetPassword;
        final View divider2 = bindingCm.divider2;
        final ConstraintLayout consMainAlert_sub_layout = bindingCm.consMainAlertSubLayout;


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


        SharedPreferences simpleSavedPassword = getSharedPreferences(Constants.SIMPLE_SAVED_PASSWORD, Context.MODE_PRIVATE);
        String simpleAdminPassword = simpleSavedPassword.getString(Constants.mySimpleSavedPassword, "");

        String smPassowrd = simpleAdminPassword;

        textDetctor(smPassowrd, editTextInputUrl, divider2);



        imgToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgToggle.setVisibility(View.INVISIBLE);
                imgToggleNzotVisible.setVisibility(View.VISIBLE);
                editTextInputUrl.setTransformationMethod(null);
                editTextInputUrl.setSelection(editTextInputUrl.getText().length());
            }
        });

        imgToggleNzotVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgToggle.setVisibility(View.VISIBLE);
                imgToggleNzotVisible.setVisibility(View.INVISIBLE);
                editTextInputUrl.setTransformationMethod(new PasswordTransformationMethod());
                editTextInputUrl.setSelection(editTextInputUrl.getText().length());
            }
        });





        textContinuPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String editTextText = editTextInputUrl.getText().toString().trim();
                if (editTextText.equals(simpleAdminPassword)) {

                    Paper.book().write(Common.set_schedule_key, Common.schedule_offline);
                    binding.textScheduleIndicator.setText("Use Local Schedule");
                    binding.scheduleSwitch.setChecked(false);

                    alertDialog.dismiss();
                    hideKeyBoard(editTextInputUrl);

                } else {
                    editTextInputUrl.setError("Wrong Password");
                    binding.scheduleSwitch.setChecked(true);

                    Paper.book().write(Common.set_schedule_key, Common.schedule_online);
                    binding.textScheduleIndicator.setText("Use Online Schedule");
                }


            }
        });


        imgCloseDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                hideKeyBoard(editTextInputUrl);
                binding.scheduleSwitch.setChecked(true);

                Paper.book().write(Common.set_schedule_key, Common.schedule_online);
                binding.textScheduleIndicator.setText("Use Online Schedule");
            }
        });

        alertDialog.show();
    }


    private void textDetctor(final String smPassword, final EditText editTextText2, final View divider2) {
        try {
            editTextText2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // No implementation needed here
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // No implementation needed here
                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        String password = editTextText2.getText().toString().trim();

                        if (smPassword.equals(password)) {
                            editTextText2.setBackgroundColor(getResources().getColor(R.color.zxing_transparent));
                            editTextText2.setTextColor(getResources().getColor(R.color.deep_green));
                            divider2.setBackgroundColor(getResources().getColor(R.color.deep_green));
                        } else {
                            editTextText2.setBackgroundColor(getResources().getColor(R.color.zxing_transparent));
                            editTextText2.setTextColor(getResources().getColor(R.color.red));
                            divider2.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                    } catch (Exception e) {
                        // Handle exception if necessary
                    }
                }
            });
        } catch (Exception e) {
            // Handle exception if necessary
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


}
