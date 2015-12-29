package pl.pharmaway.gardmiaxmedica.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.gardmiaxmedica.R;

/**
 * Created by Radek on 12/23/2015.
 */
public class SendingFragment extends Fragment {
    public static String TAG = "SendingFragment";

    @Bind(R.id.progress_bar)
    View mProgressBar;

    @Bind(R.id.retry)
    View mRetry;

    @OnClick(R.id.retry)
    public void onRetry(View view) {
        if(getActivity() instanceof OnRetryListener) {
            ((OnRetryListener) getActivity()).onRetry();
        } else {
            throw new RuntimeException(""+getActivity().getClass().getSimpleName() + " must implement OnRetryListener!");
        }
        mRetry.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void onFailed() {
        mRetry.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sending_fragment, null);
        ButterKnife.bind(this, view);

        return view;
    }

    public interface OnRetryListener {
        public void onRetry();
    }
}
