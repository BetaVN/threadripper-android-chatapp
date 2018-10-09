package com.chatapp.threadripper;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    TextView title;
    ImageView btnImgBack;

    boolean doubleBackToExitPressedOnce = false;


    public final void changeTitle(int toolbarId, String titlePage) {
        toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText(titlePage);

        getSupportActionBar().setTitle("");
    }

    public final void setupToolbar(int toolbarId, String titlePage) {
        changeTitle(toolbarId, titlePage);
    }

    public void setupToolbarWithBackButton(int toolbarId, String titlePage) {
        setupToolbar(toolbarId, titlePage);

        btnImgBack = (ImageView) findViewById(R.id.btnImgBack);
        btnImgBack.setVisibility(View.VISIBLE);
        btnImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void loadFonts() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public void setupDoubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
