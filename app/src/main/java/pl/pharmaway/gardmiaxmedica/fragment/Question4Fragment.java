package pl.pharmaway.gardmiaxmedica.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.gardmiaxmedica.R;

/**
 * Created by Radek on 12/23/2015.
 */
public class Question4Fragment  extends Fragment {
    public static final String TAG = "Question4Fragment";

    @Bind(R.id.answer_text)
    EditText mQuestionAnswer;

    @Bind(R.id.next_button)
    Button mNextButton;

    @OnClick(R.id.next_button)
    public void onNext(Button next) {
        if (getActivity() instanceof OnAnswerListener) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mQuestionAnswer.getWindowToken(), 0);
            ((OnAnswerListener)getActivity()).selectedAnswer(mQuestionAnswer.getText().toString());
        } else {
            throw new RuntimeException(""+getActivity().getClass().getSimpleName() + " must implmenet OnAnswerListener!");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_4_layout, null);
        ButterKnife.bind(this, view);

        mQuestionAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0) {
                    mNextButton.setEnabled(true);
                } else {
                    mNextButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public interface OnAnswerListener {
        public void selectedAnswer(String answerText);
    }
}
