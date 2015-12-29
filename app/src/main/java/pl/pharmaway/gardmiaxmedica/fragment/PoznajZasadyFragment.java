package pl.pharmaway.gardmiaxmedica.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.gardmiaxmedica.R;

/**
 * Created by Radek on 12/23/2015.
 */
public class PoznajZasadyFragment extends Fragment {
    public static final String TAG = "PoznajZasadyFragment";

    @OnClick(R.id.next_button)
    public void onNext(Button next) {
        if (getActivity() instanceof OnStartButtonListner) {
            ((OnStartButtonListner)getActivity()).startQuiz();
        } else {
            throw new RuntimeException(""+getActivity().getClass().getSimpleName() + " must implmenet OnStartButtonListner!");
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
        View view = inflater.inflate(R.layout.poznaj_zasady_fragment, null);
        ButterKnife.bind(this, view);

        return view;
    }

    public interface OnStartButtonListner {
        public void startQuiz();
    }
}

