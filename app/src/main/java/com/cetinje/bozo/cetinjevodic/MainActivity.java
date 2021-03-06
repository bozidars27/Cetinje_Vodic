package com.cetinje.bozo.cetinjevodic;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.Manifest;
import com.bumptech.glide.Glide;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnShowcaseEventListener {
    SharedPreferences prefs = null;
    int tutorialPosition;
    ViewTarget target;
    ImageView background;
    ImageView banner;
    RelativeLayout middle;
    RelativeLayout bottom;

    ImageView route;
    ImageView objects;
    ImageView gallery;
    ImageView restaurants;
    ImageView news;
    ImageView quiz;

    ImageView borderUp;
    ImageView borderDown;

    ImageView left;
    ImageView right;
    ImageView start;

    ImageView currentlyUp; //Indikator koji sluzi kako bi znali koje je od polja uvecano

    DatabaseHelper db; // Lokalna baza
    DataBaseHandler dbH; //Sinhronizacija sa serverskom bazom

    public static final String BarcodeObject = "Barcode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("com.cetinje.bozo.cetinjevodic", MODE_PRIVATE);

        db = new DatabaseHelper(getApplicationContext());
        dbH = new DataBaseHandler(getApplicationContext(), db);
        Intent intent = getIntent();
        //Toast.makeText(getApplicationContext(), String.valueOf(intent.getStringExtra(BarcodeObject)), Toast.LENGTH_SHORT).show();
        if(!intent.getStringExtra(BarcodeObject).equals("0"))
        {
            //Toast.makeText(getApplicationContext(), String.valueOf(intent.getStringExtra(BarcodeObject)), Toast.LENGTH_SHORT).show();
            dbH.readFromDatabase(intent.getStringExtra(BarcodeObject));
        }
        //Toast.makeText(getApplicationContext(), String.valueOf(db.getAllCountrys().size()), Toast.LENGTH_SHORT).show();

        background = (ImageView) findViewById(R.id.bg);
        banner = (ImageView) findViewById(R.id.banner);
        background.setAlpha(0.5f);

        middle = (RelativeLayout) findViewById(R.id.middle);
        bottom = (RelativeLayout) findViewById(R.id.bottom);

        borderUp = (ImageView) findViewById(R.id.border_up);
        borderDown = (ImageView) findViewById(R.id.border_down);

        route = (ImageView) findViewById(R.id.route);
        objects = (ImageView) findViewById(R.id.objects);
        gallery = (ImageView) findViewById(R.id.gallery);
        restaurants = (ImageView) findViewById(R.id.restaurants);
        news = (ImageView) findViewById(R.id.news);
        quiz = (ImageView) findViewById(R.id.quiz);

        left = (ImageView) findViewById(R.id.left);
        right = (ImageView) findViewById(R.id.right);
        left.setAlpha(0.5f);
        right.setAlpha(0.5f);

        start = (ImageView) findViewById(R.id.start);

        //Loading slika pomoću Glide biblioteke
        Glide.with(getBaseContext()).load(R.drawable.cetinjski_manastir).into(banner);
        //Glide.with(getBaseContext()).load(R.drawable.bg_transparent).into(background);

        Glide.with(getBaseContext()).load(R.drawable.map).into(route);
        Glide.with(getBaseContext()).load(R.drawable.museum).into(objects);
        Glide.with(getBaseContext()).load(R.drawable.mountains).into(gallery);
        Glide.with(getBaseContext()).load(R.drawable.whiskey).into(restaurants);
        Glide.with(getBaseContext()).load(R.drawable.news).into(news);
        Glide.with(getBaseContext()).load(R.drawable.brain).into(quiz);

        Glide.with(getBaseContext()).load(R.drawable.play).into(start);
        //

        route.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && currentlyUp != route) {
                    animateIt(route, currentlyUp, true);
                    currentlyUp = route;
                }
                return true;
            }
        });
        objects.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && currentlyUp != objects) {
                    animateIt(objects, currentlyUp, true);
                    currentlyUp = objects;
                }
                return true;
            }
        });
        gallery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && currentlyUp != gallery) {
                    animateIt(gallery, currentlyUp, true);
                    currentlyUp = gallery;
                }
                return true;
            }
        });
        restaurants.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && currentlyUp != restaurants) {
                    animateIt(restaurants, currentlyUp, true);
                    currentlyUp = restaurants;
                }
                return true;
            }
        });
        news.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && currentlyUp != news) {
                    animateIt(news, currentlyUp, true);
                    currentlyUp = news;
                }
                return true;
            }
        });
        quiz.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && currentlyUp != quiz) {
                    animateIt(quiz, currentlyUp, true);
                    currentlyUp = quiz;
                }
                return true;
            }
        });
        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    animateIt(start, start, false);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP && currentlyUp == route){

                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getBaseContext(), MapActivity.class);
                            startActivity(i);
                        }
                    }, 400);

                }
                else if (event.getAction() == MotionEvent.ACTION_UP && currentlyUp == objects){

                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getBaseContext(), CulturalHeritageActivity.class);
                            startActivity(i);
                        }
                    }, 400);

                }
                else if (event.getAction() == MotionEvent.ACTION_UP && currentlyUp == gallery){

                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getBaseContext(), Gallery.class);
                            startActivity(i);
                        }
                    }, 400);

                }
                else if (event.getAction() == MotionEvent.ACTION_UP && currentlyUp == restaurants){

                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getBaseContext(), RestaurantActivity.class);
                            startActivity(i);
                        }
                    }, 400);

                }
                else if (event.getAction() == MotionEvent.ACTION_UP && currentlyUp == news){

                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getBaseContext(), EventActivity.class);
                            startActivity(i);
                        }
                    }, 400);

                }
                else if (event.getAction() == MotionEvent.ACTION_UP && currentlyUp == quiz){

                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getBaseContext(), QuizActivity.class);
                            startActivity(i);
                        }
                    }, 400);

                }
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            target = new ViewTarget(R.id.route, this);
            new ShowcaseView.Builder(this)
                    .withMaterialShowcase()
                    .setTarget(target)
                    .setContentTitle(getResources().getString(R.string.route_title))
                    .setContentText(getResources().getString(R.string.route_description))
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .setShowcaseEventListener(this)
                    .build();
            tutorialPosition = 1;
            //prefs.edit().putBoolean("firstrun", false).commit();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, QRcodeActivity.class);
        startActivity(i);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //animateIt(route, null, true);
        //currentlyUp = route;

        //Dinamicko postavljanje margina
        int iconMargHor = getHorMargins();
        int iconMargVer = getVertMargins();

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(route.getLayoutParams());
        lp.setMargins(iconMargHor, iconMargVer, 0, 0);
        route.setLayoutParams(lp);

        lp = new RelativeLayout.LayoutParams(objects.getLayoutParams());
        lp.setMargins(route.getWidth()+2*iconMargHor, iconMargVer, 0, 0);
        objects.setLayoutParams(lp);

        lp = new RelativeLayout.LayoutParams(gallery.getLayoutParams());
        lp.setMargins(2*route.getWidth()+3*iconMargHor, iconMargVer, 0, 0);
        gallery.setLayoutParams(lp);

        lp = new RelativeLayout.LayoutParams(restaurants.getLayoutParams());
        lp.setMargins(iconMargHor, route.getHeight()+2*iconMargVer, 0, 0);
        restaurants.setLayoutParams(lp);

        lp = new RelativeLayout.LayoutParams(news.getLayoutParams());
        lp.setMargins(route.getWidth()+2*iconMargHor, route.getHeight()+2*iconMargVer, 0, 0);
        news.setLayoutParams(lp);

        lp = new RelativeLayout.LayoutParams(quiz.getLayoutParams());
        lp.setMargins(2*route.getWidth()+3*iconMargHor, route.getHeight()+2*iconMargVer, 0, 0);
        quiz.setLayoutParams(lp);
        //

    }

    public int getHorMargins() {
        return (middle.getWidth() - 3*route.getWidth())/4;
    }

    public int getVertMargins() {
        return (middle.getHeight() - 2*route.getHeight())/3;
    }

    public void animateIt(ImageView iv1, ImageView iv2, boolean sync) {
        //Animira ikonice. iv1 je ikonica koja se povecava, a iv2 ona koja se smanjuje. Ukoliko zelimo samo
        //da povecamo prvu ikonu, kao drugi argument se prosledjuje "null". Treci argument govori da li se povecanje prvog
        //i smanjenje drugog objekta obavljaju sinhrono ili jedno nakon drugog

        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(iv1, "scaleX", 1f, 1.3f);
        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(iv1, "scaleY", 1f, 1.3f);

        scaleX1.setDuration(200);
        scaleY1.setDuration(200);

        scaleX1.setInterpolator(new OvershootInterpolator());
        scaleY1.setInterpolator(new OvershootInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.play(scaleX1).with(scaleY1);

        if (iv2 != null) {

            ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(iv2, "scaleX", 1.3f, 1f);
            ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(iv2, "scaleY", 1.3f, 1f);

            scaleX2.setDuration(200);
            scaleY2.setDuration(200);

            scaleX2.setInterpolator(new DecelerateInterpolator());
            scaleY2.setInterpolator(new DecelerateInterpolator());

            if (sync) {
                set.play(scaleX1).with(scaleX2);
            } else {
                set.play(scaleX1).before(scaleX2);
            }

            set.play(scaleX2).with(scaleY2);

        }

        set.start();

    }

    @Override
    public void onShowcaseViewHide(ShowcaseView showcaseView) {

    }

    @Override
    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
        switch(tutorialPosition) {
            case 1 :
                target = new ViewTarget(R.id.objects, this);
                showcaseView.setTarget(target);
                showcaseView.setContentTitle(getResources().getString(R.string.objects_title));
                showcaseView.setContentText(getResources().getString(R.string.objects_description));
                Log.e("e", String.valueOf(showcaseView.isShown()));
                showcaseView.show();
                Log.e("e", String.valueOf(showcaseView.isShown()));
                tutorialPosition++;
                break; // optional

            case 2 :
                target = new ViewTarget(R.id.gallery, this);
                showcaseView.setTarget(target);
                showcaseView.setContentTitle(getResources().getString(R.string.gallery_title));
                showcaseView.setContentText(getResources().getString(R.string.gallery_description));
                showcaseView.show();
                tutorialPosition++;
                break;
            case 3 :
                target = new ViewTarget(R.id.restaurants, this);
                showcaseView.setTarget(target);
                showcaseView.setContentTitle(getResources().getString(R.string.restaurant_title));
                showcaseView.setContentText(getResources().getString(R.string.restaurant_description));
                showcaseView.show();
                tutorialPosition++;
                break;
            case 4 :
                target = new ViewTarget(R.id.news, this);
                showcaseView.setTarget(target);
                showcaseView.setContentTitle(getResources().getString(R.string.news_title));
                showcaseView.setContentText(getResources().getString(R.string.news_description));
                showcaseView.show();
                tutorialPosition++;
                break;
            case 5 :
                target = new ViewTarget(R.id.quiz, this);
                showcaseView.setTarget(target);
                showcaseView.setContentTitle(getResources().getString(R.string.quiz_title));
                showcaseView.setContentText(getResources().getString(R.string.quiz_description));
                showcaseView.show();
                tutorialPosition++;
                break;
        }
    }

    @Override
    public void onShowcaseViewShow(ShowcaseView showcaseView) {

    }

    @Override
    public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {
    }
}