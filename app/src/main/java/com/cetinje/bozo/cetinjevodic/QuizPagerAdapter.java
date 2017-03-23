package com.cetinje.bozo.cetinjevodic;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


class QuizPagerAdapter extends PagerAdapter {

    public static View CURRENT_ANSWER;

    private Context context;
    private ArrayList<Quiz> quizzes;


    QuizPagerAdapter(Context context, ArrayList<Quiz> quizzes) {
        this.context = context;
        this.quizzes = quizzes;
    }

    @Override
    public int getCount() {
        return quizzes.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object); //Izvršava validaciju. Provjerava da li je pozvani View zaista instanca kreiranog layout-a
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Quiz currentQuiz = quizzes.get(position);

        View view;
        TextView question;
        //EditText answer;

        ArrayList<String> answers = new ArrayList<>();
        answers.add(currentQuiz.getTans());
        answers.add(currentQuiz.getFans1());
        answers.add(currentQuiz.getFans2());
        answers.add(currentQuiz.getFans3());

        long seed = System.nanoTime();
        Collections.shuffle(answers, new Random(seed));

        view = layoutInflater.inflate(R.layout.quiz_question_layout, container, false);
        question = (TextView) view.findViewById(R.id.question);
        final TextView answerA = (TextView) view.findViewById(R.id.answer_a);
        final TextView answerB = (TextView) view.findViewById(R.id.answer_b);
        final TextView answerC = (TextView) view.findViewById(R.id.answer_c);
        final TextView answerD = (TextView) view.findViewById(R.id.answer_d);

        final RelativeLayout answerAContainer = (RelativeLayout) view.findViewById(R.id.answer_a_container);
        final RelativeLayout answerBContainer = (RelativeLayout) view.findViewById(R.id.answer_b_container);
        final RelativeLayout answerCContainer = (RelativeLayout) view.findViewById(R.id.answer_c_container);
        final RelativeLayout answerDContainer = (RelativeLayout) view.findViewById(R.id.answer_d_container);

        if (currentQuiz.getType() == 1) {

            answerAContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CURRENT_ANSWER != answerAContainer) {
                        onTouchAnimation(answerAContainer, true);
                        onTouchAnimation(CURRENT_ANSWER, false);
                        CURRENT_ANSWER = answerAContainer;
                    }
                }
            });

            answerBContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CURRENT_ANSWER != answerBContainer) {
                        onTouchAnimation(answerBContainer, true);
                        onTouchAnimation(CURRENT_ANSWER, false);
                        CURRENT_ANSWER = answerBContainer;
                    }
                }
            });

            answerCContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CURRENT_ANSWER != answerCContainer) {
                        onTouchAnimation(answerCContainer, true);
                        onTouchAnimation(CURRENT_ANSWER, false);
                        CURRENT_ANSWER = answerCContainer;
                    }
                }
            });

            answerDContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CURRENT_ANSWER != answerDContainer) {
                        onTouchAnimation(answerDContainer, true);
                        onTouchAnimation(CURRENT_ANSWER, false);
                        CURRENT_ANSWER = answerDContainer;
                    }
                }
            });

            question.setText(currentQuiz.getQuestion());
            answerA.setText(answers.get(0));
            answerB.setText(answers.get(1));
            answerC.setText(answers.get(2));
            answerD.setText(answers.get(3));

        } else if (currentQuiz.getType() == 2) {
            view = layoutInflater.inflate(R.layout.quiz_question_type2_layout, container, false);
            question = (TextView) view.findViewById(R.id.question);
            //answer = (EditText) view.findViewById(R.id.answer);

            question.setText(currentQuiz.getQuestion());
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView( (LinearLayout) object );
    }


    private void onTouchAnimation(final View view, boolean mark) {

        if (view != null) {

            ObjectAnimator scaleRed;
            ObjectAnimator scaleGold;
            ObjectAnimator alphaRed;
            ObjectAnimator alphaGold;

            ImageView red = (ImageView) ((ViewGroup) view).getChildAt(0);
            ImageView gold = (ImageView) ((ViewGroup) view).getChildAt(1);

            if (mark) {
                scaleRed = ObjectAnimator.ofFloat(red, "scaleX", 1f, 1.3f);
                scaleGold = ObjectAnimator.ofFloat(gold, "scaleX", 1f, 1.3f);
                alphaRed = ObjectAnimator.ofFloat(red, "alpha", 1f, 0f);
                alphaGold = ObjectAnimator.ofFloat(gold, "alpha", 0f, 1f);
            } else {
                scaleRed = ObjectAnimator.ofFloat(red, "scaleX", 1.3f, 1f);
                scaleGold = ObjectAnimator.ofFloat(gold, "scaleX", 1.3f, 1f);
                alphaRed = ObjectAnimator.ofFloat(red, "alpha", 0f, 1f);
                alphaGold = ObjectAnimator.ofFloat(gold, "alpha", 1f, 0f);
            }

            scaleRed.setDuration(200);
            scaleGold.setDuration(200);
            alphaRed.setDuration(200);
            alphaGold.setDuration(200);

            scaleRed.setInterpolator(new OvershootInterpolator());
            scaleGold.setInterpolator(new OvershootInterpolator());
            alphaRed.setInterpolator(new LinearInterpolator());
            alphaGold.setInterpolator(new LinearInterpolator());

            AnimatorSet set = new AnimatorSet();

            set.play(scaleRed).with(scaleGold);
            set.play(scaleGold).with(alphaRed);
            set.play(alphaRed).with(alphaGold);

            set.start();

        }

    }

}
