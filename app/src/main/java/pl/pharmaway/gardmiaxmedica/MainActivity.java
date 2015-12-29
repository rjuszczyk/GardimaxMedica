package pl.pharmaway.gardmiaxmedica;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.gardmiaxmedica.fragment.ChooseAgentTypeFragment;
import pl.pharmaway.gardmiaxmedica.fragment.ChooseAgentTypeFragmentBuilder;
import pl.pharmaway.gardmiaxmedica.model.Agent;
import pl.pharmaway.gardmiaxmedica.model.AgentsResponse;
import pl.pharmaway.gardmiaxmedica.model.Question;
import pl.pharmaway.gardmiaxmedica.model.QuestionsResponse;
import pl.pharmaway.gardmiaxmedica.retrofit.DataService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final String url = "http://pharmawayjn.nazwa.pl/MedycynaRodzinna/dane.json";

    DataService mService;

    @Bind(R.id.choose_agent)
    View mChooseAgent;

    @Bind(R.id.network_problems)
    View mNetworkProblems;

    @Bind(R.id.progress_bar)
    View mProgressBar;

    @Bind(R.id.agent_pharmaceutical)
    Button mAgentPharmaceutical;

    @Bind(R.id.agent_medical)
    Button mAgentMedical;

    @OnClick(R.id.try_again)
    public void tryAgain(View view) {
        loadQuestionsAndAgents();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_main);



        ButterKnife.bind(this);

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://pharmawayjn.nazwa.pl/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mService = retrofit.create(DataService.class);

        loadQuestionsAndAgents();

    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()==1)
        {
            mNetworkProblems.setVisibility(View.GONE);
            mChooseAgent.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
        super.onBackPressed();
    }
    List<Question> mQuestions;
    public List<Question> getQuestions() {
        return mQuestions;
    }

    void loadQuestionsAndAgents() {
        mNetworkProblems.setVisibility(View.GONE);
        mChooseAgent.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        Call<QuestionsResponse> questionsResponse = mService.questionsIndex();

        questionsResponse.enqueue(new Callback<QuestionsResponse>() {

            @Override
            public void onResponse(Response<QuestionsResponse> response) {
                final QuestionsResponse questionsResponse = response.body();
                mQuestions = questionsResponse.getQuestions();
                loadAgents();
            }

            @Override
            public void onFailure(Throwable t) {
                mNetworkProblems.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    void loadAgents() {


        Call<AgentsResponse> agentsResponse = mService.agentsIndex();

        agentsResponse.enqueue(new Callback<AgentsResponse>() {
            @Override
            public void onResponse(final Response<AgentsResponse> response) {
                final AgentsResponse agentsResponse = response.body();

                mChooseAgent.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Agent> agents;
                        String title;
                        if(v.getId() == R.id.agent_medical) {
                            agents = agentsResponse.getMedicalAgents();
                            title = "Wybierz przedstawiciela medycznego:";
                        } else {
                            agents = agentsResponse.getPharmaceuticalAgents();
                            title = "Wybierz przedstawiciela farmaceutycznego:";
                        }

                        ChooseAgentTypeFragment fragment = ChooseAgentTypeFragmentBuilder.newChooseAgentTypeFragment(agents, title);
                        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, ChooseAgentTypeFragment.TAG).addToBackStack(ChooseAgentTypeFragment.TAG).commit();
                        mChooseAgent.setVisibility(View.GONE);
                    }
                };

                mAgentPharmaceutical.setOnClickListener(onClickListener);
                mAgentMedical.setOnClickListener(onClickListener);
            }

            @Override
            public void onFailure(Throwable t) {
                mNetworkProblems.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
