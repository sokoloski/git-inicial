package com.example.root.gobuy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PinFragment extends AppCompatActivity {

    public static TextView textView_nome;
    public static TextView textView_valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_fragment);

        textView_nome = (TextView) findViewById(R.id.textView2);
        textView_valor = (TextView) findViewById(R.id.textView3);

    }

    public void set_text(String nome, String valor){
        textView_valor.setText(valor);
        textView_nome.setText(nome);
    }

}
