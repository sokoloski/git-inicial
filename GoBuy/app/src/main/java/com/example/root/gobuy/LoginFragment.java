package com.example.root.gobuy;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    private EditText email;
    private EditText pass;
    private Button login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_login_fragment, container, false);


        login = (Button) rootView.findViewById(R.id.email_sign_in_button);
        email = (EditText) rootView.findViewById(R.id.email);
        pass = (EditText) rootView.findViewById(R.id.password);

        //botão para logar
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //verificando email e senha
                if(email.getText().toString().equals("teste") && pass.getText().toString().equals("teste") ){

                    InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(pass.getWindowToken(), 0);
                    AdmFragment admFragment = new AdmFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, admFragment);
                    fragmentTransaction.commit();

                } else{
                    CharSequence text = "Login incorreto";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                }
            }
        });

        return rootView;
    }

}

