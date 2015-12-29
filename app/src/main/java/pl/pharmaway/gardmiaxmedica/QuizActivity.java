package pl.pharmaway.gardmiaxmedica;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import pl.pharmaway.gardmiaxmedica.fragment.ChooseAgentTypeFragment;
import pl.pharmaway.gardmiaxmedica.fragment.ChooseAgentTypeFragmentBuilder;
import pl.pharmaway.gardmiaxmedica.fragment.CongratulationsFragment;
import pl.pharmaway.gardmiaxmedica.fragment.FormFragment;
import pl.pharmaway.gardmiaxmedica.fragment.PoznajZasadyFragment;
import pl.pharmaway.gardmiaxmedica.fragment.Question4Fragment;
import pl.pharmaway.gardmiaxmedica.fragment.QuestionFragment;
import pl.pharmaway.gardmiaxmedica.fragment.QuestionFragmentBuilder;
import pl.pharmaway.gardmiaxmedica.fragment.SendingFragment;
import pl.pharmaway.gardmiaxmedica.fragment.SendingFragment.OnRetryListener;
import pl.pharmaway.gardmiaxmedica.model.Agent;
import pl.pharmaway.gardmiaxmedica.model.Question;
import pl.pharmaway.gardmiaxmedica.retrofit.DataService;
import pl.pharmaway.gardmiaxmedica.workaround.AndroidBug5497Workaround;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Radek on 12/21/2015.
 */
public class QuizActivity extends AppCompatActivity implements QuestionFragment.OnAnswerListener, Question4Fragment.OnAnswerListener, FormFragment.OnConfirmListener, OnRetryListener, PoznajZasadyFragment.OnStartButtonListner {

    public static final String ARG_AGENT = "ARG_AGENT";
    public static final String ARG_QUESTIONS = "ARG_QUESTIONS";

    private Agent mAgent;
    private ArrayList<Question> mQuestions;

    public static void start(Context context, Agent agent, List<Question> questions) {
        Intent startIntent = new Intent(context, QuizActivity.class);
        startIntent.putExtra(ARG_AGENT, agent);
        ArrayList<Serializable> questionsSerializable = new ArrayList<>();
        questionsSerializable.addAll(questions);
        startIntent.putExtra(ARG_QUESTIONS, questionsSerializable);
        context.startActivity(startIntent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_quiz);

    //    boolean bigTablet = getResources().getBoolean(R.bool.big_tablet);
     //   if(bigTablet) {
            AndroidBug5497Workaround.assistActivity(this, findViewById(R.id.container));
      //  }

        if(getIntent() != null) {
            mAgent = (Agent) getIntent().getSerializableExtra(ARG_AGENT);
            mQuestions = (ArrayList<Question>) getIntent().getSerializableExtra(ARG_QUESTIONS);
        }

        ButterKnife.bind(this);

        PoznajZasadyFragment fragment = new PoznajZasadyFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, PoznajZasadyFragment.TAG).commit();
    }


    public void startQuiz() {
        Question question = mQuestions.get(currentAnswer);

        QuestionFragment fragment = QuestionFragmentBuilder.newQuestionFragment(question.getAnswer1(), question.getAnswer2(), question.getAnswer3(), question.getCorrectAnswer(), 1, question.getQuestion());
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, QuestionFragment.TAG).commit();
    }

    int currentAnswer = 0;



    @Override
    public void selectedAnswer(int selectedAnswer, int correctAnswer) {
        currentAnswer++;

        if(currentAnswer ==1) {
            odp1 = answerIntToString(selectedAnswer);
        }
        if(currentAnswer ==2) {
            odp2 = answerIntToString(selectedAnswer);
        }
        if(currentAnswer ==3) {
            odp3 = answerIntToString(selectedAnswer);
        }

        if(currentAnswer==3)
        {
            Question4Fragment fragment = new Question4Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, Question4Fragment.TAG).commit(); 
        } else {


            Question question = mQuestions.get(currentAnswer);

            QuestionFragment fragment = QuestionFragmentBuilder.newQuestionFragment(question.getAnswer1(), question.getAnswer2(), question.getAnswer3(), question.getCorrectAnswer(), 1+currentAnswer, question.getQuestion());

            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, QuestionFragment.TAG).commit();
        }



    }

    private String answerIntToString(int answer) {
        switch (answer) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
        }
        return "?";
    }

    public void SendData(
            String przedstawiciel_id,
            String przedstawiciel,

            String odp1,
            String odp2,
            String odp3,
            String odp4,

            String imie,
            String nazwisko,
            String nazwa_apteki,
            String id_apterki,

            String ulica,
            String nr,
            String miasto,
            String kod,

            String telefon,
            String zgoda,
            String brac_pod_uwage,
            Runnable onSuccess,
            Runnable onFailure
                           ) throws UnsupportedEncodingException {


      //  String bracPodUwage = (checkBox1.isChecked() && checkBox2.isChecked()) ? "1" : "0";
      //  String zgoda = checkBox3.isChecked() ? "1" : "0";


        String data = URLEncoder.encode("przedstawiciel_id", "UTF-8")
                + "=" + URLEncoder.encode("" + przedstawiciel_id, "UTF-8");

        data += "&" + URLEncoder.encode("przedstawiciel", "UTF-8") + "="
                + URLEncoder.encode(przedstawiciel, "UTF-8");

        data += "&" + URLEncoder.encode("odp1", "UTF-8") + "="
                + URLEncoder.encode(odp1, "UTF-8");

        data += "&" + URLEncoder.encode("odp2", "UTF-8") + "="
                + URLEncoder.encode(odp2, "UTF-8");

        data += "&" + URLEncoder.encode("odp3", "UTF-8") + "="
                + URLEncoder.encode(odp3, "UTF-8");

        data += "&" + URLEncoder.encode("odp4", "UTF-8") + "="
                + URLEncoder.encode(odp4, "UTF-8");


        data += "&" + URLEncoder.encode("imie", "UTF-8") + "="
                + URLEncoder.encode(imie, "UTF-8");

        data += "&" + URLEncoder.encode("nazwisko", "UTF-8") + "="
                + URLEncoder.encode(nazwisko, "UTF-8");
        data += "&" + URLEncoder.encode("nazwa_apteki", "UTF-8") + "="
                + URLEncoder.encode(nazwa_apteki, "UTF-8");
        data += "&" + URLEncoder.encode("id_apterki", "UTF-8") + "="
                + URLEncoder.encode(id_apterki, "UTF-8");


        data += "&" + URLEncoder.encode("ulica", "UTF-8") + "="
                + URLEncoder.encode(ulica, "UTF-8");

        data += "&" + URLEncoder.encode("nr", "UTF-8") + "="
                + URLEncoder.encode(nr, "UTF-8");

        data += "&" + URLEncoder.encode("miasto", "UTF-8") + "="
                + URLEncoder.encode(miasto, "UTF-8");

        data += "&" + URLEncoder.encode("kod", "UTF-8") + "="
                + URLEncoder.encode(kod, "UTF-8");

        data += "&" + URLEncoder.encode("telefon", "UTF-8") + "="
                + URLEncoder.encode(telefon, "UTF-8");

        data += "&" + URLEncoder.encode("zgoda", "UTF-8") + "="
                + URLEncoder.encode(zgoda, "UTF-8");

        data += "&" + URLEncoder.encode("brac_pod_uwage", "UTF-8") + "="
                + URLEncoder.encode(brac_pod_uwage, "UTF-8");


        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("http://pharmawayjn.nazwa.pl/MedycynaRodzinna/gardimax/test.php");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        } catch (Exception ex) {
            runOnUiThread(onFailure);
            return;
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        runOnUiThread(onSuccess);

        return;
    }

    @Override
    public void selectedAnswer(String answerText) {
        FormFragment fragment = new FormFragment();
        odp4 = answerText;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, FormFragment.TAG).commit();
    }

    public String odp1;
    public String odp2;
    public String odp3;
    public String odp4;

    String mImie;
    String mNazwisko;
    String mNazwaApteki;
    String mIdApteki;
    String mUlica;
    String mNr;
    String mMiasto;
    String mKod;
    String mTelefon;
    boolean mCheckbox1;
    boolean mCheckbox2;
    boolean mCheckbox3;
    
    
    @Override
    public void onConfirm(final String imie, final String nazwisko, final String nazwaApteki, final String idApteki, final String ulica, final String nr, final String miasto, final String kod, final String telefon, boolean checkbox1, boolean checkbox2, boolean checkbox3) {

        mImie = imie;
        mNazwisko = nazwisko;
        mNazwaApteki = nazwaApteki;
        mIdApteki = idApteki;
        mUlica = ulica;
        mNr = nr;
        mMiasto = miasto;
        mKod = kod;
        mTelefon = telefon;
        mCheckbox1 = checkbox1;
        mCheckbox2 = checkbox2;
        mCheckbox3 = checkbox3;

        final String bracPodUwage = (checkbox1 && checkbox2) ? "1" : "0";
        final String zgoda = checkbox3 ? "1" : "0";

        final Runnable onSuccess = new Runnable() {
            @Override
            public void run() {
                CongratulationsFragment fragment = new CongratulationsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, CongratulationsFragment.TAG).commit();
            }
        };

        final Runnable onFailure = new Runnable() {
            @Override
            public void run() {
                SendingFragment sendingFragment = (SendingFragment) getSupportFragmentManager().findFragmentByTag(SendingFragment.TAG);
                sendingFragment.onFailed();
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    SendingFragment fragment = new SendingFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, SendingFragment.TAG).commit();
                    SendData("" + mAgent.getId(),  mAgent.getName(), odp1, odp2, odp3, odp4, imie, nazwisko, nazwaApteki, idApteki, ulica, nr, miasto, kod, telefon, zgoda, bracPodUwage, onSuccess, onFailure);
                } catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(onFailure);
                }
            }
        }).start();
    }

    @Override
    public void onRetry() {
        onConfirm(
            mImie,
            mNazwisko,
            mNazwaApteki,
            mIdApteki,
            mUlica,
            mNr,
            mMiasto,
            mKod,
            mTelefon,
            mCheckbox1,
            mCheckbox2,
            mCheckbox3
        );
    }
}
