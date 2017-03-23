package com.cetinje.bozo.cetinjevodic;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    ImageView next;
    ViewPager quizPager;
    TextView counterVal;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        next = (ImageView) findViewById(R.id.quiz_button);
        quizPager = (ViewPager) findViewById(R.id.question_pager);
        counterVal = (TextView) findViewById(R.id.counter_val);

        db = new DatabaseHelper(getApplicationContext());

        ArrayList<Quiz> quizzes = db.getAllQuizes();
        final String qstNumber = Integer.toString(quizzes.size());

        quizPager.setAdapter( new QuizPagerAdapter(getApplicationContext(), quizzes) );
        counterVal.setText( Integer.toString(quizPager.getCurrentItem() + 1) + "/" + qstNumber);
        //
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(next, "scaleX", 1f, 1.3f);
                ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(next, "scaleY", 1f, 1.3f);
                ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(next, "scaleX", 1.3f, 1f);
                ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(next, "scaleY", 1.3f, 1f);

                AnimatorSet set = new AnimatorSet();
                set.play(scaleX1).with(scaleY1);
                set.play(scaleY1).before(scaleX2);
                set.play(scaleX2).with(scaleY2);

                scaleX1.setDuration(150);
                scaleY1.setDuration(150);
                scaleX2.setDuration(150);
                scaleY2.setDuration(150);

                scaleX1.setInterpolator(new AccelerateInterpolator());
                scaleY1.setInterpolator(new AccelerateInterpolator());
                scaleX2.setInterpolator(new DecelerateInterpolator());
                scaleY2.setInterpolator(new DecelerateInterpolator());

                set.start();

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        quizPager.setCurrentItem(quizPager.getCurrentItem() + 1, true);
                        QuizPagerAdapter.CURRENT_ANSWER = null;
                        counterVal.setText( Integer.toString(quizPager.getCurrentItem() + 1) + "/" + qstNumber );
                    }
                }, 300);

            }
        });
    }
}
