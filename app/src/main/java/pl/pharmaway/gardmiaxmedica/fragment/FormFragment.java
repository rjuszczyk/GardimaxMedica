package pl.pharmaway.gardmiaxmedica.fragment;

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
import android.widget.CheckBox;
import android.widget.EditText;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.gardmiaxmedica.R;
import pl.pharmaway.gardmiaxmedica.watcher.OnTextChangeListener;

/**
 * Created by Radek on 12/23/2015.
 */
public class FormFragment extends Fragment {
    public static final String TAG = "FormFragment";

    @Bind(R.id.form_imie)
    EditText mFormImie;
    
    @Bind(R.id.form_nazwisko)
    EditText mFormNazwisko;
    
    @Bind(R.id.form_nazwa_apteki)
    EditText mFormNazwaApteki;
    
    @Bind(R.id.form_id_apteki)
    EditText mFormIdApteki;

    @Bind(R.id.form_uilca)
    EditText mFormUlica;
    
    @Bind(R.id.form_nr)
    EditText mFormNr;
    
    @Bind(R.id.form_miasto)
    EditText mFormMiasto;

    @Bind(R.id.form_kod)
    EditText mFormKod;

    @Bind(R.id.form_telefon)
    EditText mFormTelefon;

    @Bind(R.id.check1)
    CheckBox mCheck1;

    @Bind(R.id.check2)
    CheckBox mCheck2;

    @Bind(R.id.check3)
    CheckBox mCheck3;

    @Bind(R.id.next_button)
    Button mButton;

    @OnClick(R.id.next_button)
    void onNextButtonClick(Button nextButton) {
        if (getActivity() instanceof OnConfirmListener) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mButton.getWindowToken(), 0);

            ((OnConfirmListener)getActivity()).onConfirm(mFormImie.getText().toString(),
                    mFormNazwisko.getText().toString(),
                    mFormNazwaApteki.getText().toString(),
                    mFormIdApteki.getText().toString(),
                    mFormUlica.getText().toString(),
                    mFormNr.getText().toString(),
                    mFormMiasto.getText().toString(),
                    mFormKod.getText().toString(),
                    mFormTelefon.getText().toString(),
                    mCheck1.isChecked(),
                    mCheck2.isChecked(),
                    mCheck3.isChecked()
            );
        } else {
            throw new RuntimeException(""+getActivity().getClass().getSimpleName() + " must implmenet OnConfirmListener!");
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
        View view = inflater.inflate(R.layout.form_fragment, null);
        ButterKnife.bind(this, view);

        mFormImie.addTextChangedListener(mOnTextChangeListener);    
        mFormNazwisko.addTextChangedListener(mOnTextChangeListener);
        mFormNazwaApteki.addTextChangedListener(mOnTextChangeListener);
        mFormIdApteki.addTextChangedListener(mOnTextChangeListener);
        mFormUlica.addTextChangedListener(mOnTextChangeListener);
        mFormNr.addTextChangedListener(mOnTextChangeListener);
        mFormMiasto.addTextChangedListener(mOnTextChangeListener);
        mFormKod.addTextChangedListener(mOnTextChangeListener);
        mFormTelefon.addTextChangedListener(mOnTextChangeListener);
        
        return view;
    }

    OnTextChangeListener mOnTextChangeListener = new OnTextChangeListener () {
        
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean notAllTextFilled =
                    mFormImie.getText().length() == 0 ||
                    mFormNazwisko.getText().length() == 0 ||
                    mFormNazwaApteki.getText().length() == 0 ||
                   // mFormIdApteki.getText().length() == 0 ||
                    mFormUlica.getText().length() == 0 ||
                    mFormNr.getText().length() == 0 ||
                    mFormMiasto.getText().length() == 0 ||
                    mFormKod.getText().length() == 0 ||
                    mFormTelefon.getText().length() == 0;

            if(notAllTextFilled)
            {
                mButton.setEnabled(false);
            }
            else
            {
                mButton.setEnabled(true);
            }
        }
    };

    public interface OnConfirmListener {
        public void onConfirm(String imie, String nazwisko, String nazwaApteki, String  idApteki, String ulica,
                              String nr, String miasto, String kod, String telefon, boolean checkbox1, boolean checkbox2, boolean checkbox3);
    }
}
