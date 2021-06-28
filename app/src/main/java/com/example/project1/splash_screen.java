package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;
    TextView logotext;
    Animation topAnimation, botAnimation;
    ImageView logosplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logotext = findViewById(R.id.logotext);
        TextPaint paint = logotext.getPaint();
        float width = paint.measureText(logotext.getText().toString());
        Shader textShader = new LinearGradient(0, 0, width, logotext.getTextSize(),
                new int[]{
                    Color.parseColor("#3DBDB0"),
                    Color.parseColor("#04B5CE"),
                }, null, Shader.TileMode.CLAMP);
        logotext.getPaint().setShader(textShader);
        logotext.setTextColor(Color.parseColor("#3DBDB0"));

        logosplash = findViewById(R.id.logodum);
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        botAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        logotext.setAnimation(botAnimation);
        logosplash.setAnimation(botAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash_screen.this, Login.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(logosplash, "logo_image");
                pairs[1] = new Pair<View, String>(logotext, "logo_text");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                        (splash_screen.this, pairs);
                finish();
                startActivity(intent, options.toBundle());
            }
        }, SPLASH_SCREEN);

    }
}