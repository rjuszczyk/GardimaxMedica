package pl.pharmaway.gardmiaxmedica.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.pharmaway.gardmiaxmedica.MainActivity;
import pl.pharmaway.gardmiaxmedica.QuizActivity;
import pl.pharmaway.gardmiaxmedica.R;
import pl.pharmaway.gardmiaxmedica.model.Agent;
import pl.pharmaway.gardmiaxmedica.model.AgentsBundler;
import pl.pharmaway.gardmiaxmedica.model.Question;

/**
 * Created by Radek on 12/21/2015.
 */
public class ChooseAgentTypeFragment extends Fragment {
    public static final String TAG = "ChooseAgentTypeFragment";

    @Arg
    String mTitle;

    @Arg(bundler = AgentsBundler.class)
    List<Agent> mAgents;

    @Bind(R.id.agents_list)
    ListView mAgentsList;

    @Bind(R.id.list_title)
    TextView mTitleTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_agent_fragment, null);
        ButterKnife.bind(this, view);



        Collections.sort(mAgents, new Comparator<Agent>() {
            @Override
            public int compare(Agent lhs, Agent rhs) {
                return lhs.toString().compareToIgnoreCase(rhs.toString());
            }
        });

        ArrayAdapter<Agent> adapter = new ArrayAdapter<Agent>(getContext(), android.R.layout.simple_list_item_1, mAgents);
        mAgentsList.setAdapter(adapter);
        mAgentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<Question> questions = ((MainActivity) getActivity()).getQuestions();
                QuizActivity.start(getActivity(), (Agent) mAgentsList.getAdapter().getItem(position), questions);
            }
        });
        mTitleTV.setText(mTitle);

        return view;
    }


}
