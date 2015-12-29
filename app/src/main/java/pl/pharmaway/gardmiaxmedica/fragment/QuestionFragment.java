package pl.pharmaway.gardmiaxmedica.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.gardmiaxmedica.QuizActivity;
import pl.pharmaway.gardmiaxmedica.R;
import pl.pharmaway.gardmiaxmedica.model.Agent;

/**
 * Created by Radek on 12/23/2015.
 */
public class QuestionFragment  extends Fragment {
    public static final String TAG = "QuestionFragment";

    private int mCurrentSelectedAnswer = -1;

    @Arg
    int mNumber;

    @Arg
    String mQuestion;

    @Arg
    String mAnswerA;

    @Arg
    String mAnswerB;

    @Arg
    String mAnswerC;

    @Arg
    int mCorrectAnswer;

    @Bind(R.id.question_number)
    TextView mQuestionNumber;

    @Bind(R.id.question_text)
    TextView mQuestionText;

    @Bind(R.id.question_ans_a)
    ImageView mAnsImgA;

    @Bind(R.id.question_ans_b)
    ImageView mAnsImgB;

    @Bind(R.id.question_ans_c)
    ImageView mAnsImgC;

    @Bind(R.id.answer_a)
    TextView mAnswerTvA;

    @Bind(R.id.answer_b)
    TextView mAnswerTvB;

    @Bind(R.id.answer_c)
    TextView mAnswerTvC;

    @Bind(R.id.next_button)
    Button mNextButton;

    @OnClick(R.id.answerA)
    public void onAnswerA(View v) {
        setCurrentSelectedAnswer(0);
    }

    @OnClick(R.id.answerB)
    public void onAnswerB(View v) {
        setCurrentSelectedAnswer(1);
    }

    @OnClick(R.id.answerC)
    public void onAnswerC(View v) {
        setCurrentSelectedAnswer(2);
    }

    @OnClick(R.id.next_button)
    public void onNext(Button next) {
        if (getActivity() instanceof OnAnswerListener) {
            ((OnAnswerListener)getActivity()).selectedAnswer(mCurrentSelectedAnswer, mCorrectAnswer);
        } else {
            throw new RuntimeException(""+getActivity().getClass().getSimpleName() + " must implmenet OnAnswerListener!");
        }
    }

    private void setCurrentSelectedAnswer(int answer) {
        switch (mCurrentSelectedAnswer)
        {
            case 0:
                mAnsImgA.setImageResource(R.drawable.ans_a);
                break;
            case 1:
                mAnsImgB.setImageResource(R.drawable.ans_b);
                break;
            case 2:
                mAnsImgC.setImageResource(R.drawable.ans_c);
                break;
        }
        switch (answer) {
            case 0:
                mAnsImgA.setImageResource(R.drawable.ans_a_sel);
                break;
            case 1:
                mAnsImgB.setImageResource(R.drawable.ans_b_sel);
                break;
            case 2:
                mAnsImgC.setImageResource(R.drawable.ans_c_sel);
                break;
        }
        mCurrentSelectedAnswer = answer;
        mNextButton.setEnabled(true);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment, null);
        ButterKnife.bind(this, view);

        mQuestionNumber.setText(""+mNumber+".");
        mQuestionText.setText(Html.fromHtml(mQuestion));

        mAnswerTvA.setText(Html.fromHtml(mAnswerA));
        mAnswerTvB.setText(Html.fromHtml(mAnswerB));
        mAnswerTvC.setText(Html.fromHtml(mAnswerC));

        return view;
    }

    public interface OnAnswerListener {
        public void selectedAnswer(int selectedAnswer, int correctAnswer);
    }
}
