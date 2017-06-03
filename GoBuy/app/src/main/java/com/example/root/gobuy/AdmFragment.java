package com.example.root.gobuy;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class AdmFragment extends Fragment {


    private EditText endereco;
    private EditText valor;
    private EditText nome;
    private Button adicionar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_adm_fragment, container, false);


        endereco = (EditText) rootView.findViewById(R.id.editText_coordenadas);
        valor = (EditText) rootView.findViewById(R.id.editText_valor);
        nome = (EditText) rootView.findViewById(R.id.editText_nome);
        adicionar = (Button) rootView.findViewById(R.id.button_adicionar);


        //adicionar novo produto em uma localidade
        adicionar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    Produto produto = new Produto();
                    produto.nome = nome.getText().toString();
                    produto.valor = valor.getText().toString();
                    produto.endereço = endereco.getText().toString();
                    produto.loja = "GDG";
                    MainFragment.produtos.add(produto);

                //pegando localização que o usuario digitou
                    String location = endereco.getText().toString();
                    List<Address> addressList = null;
                    if(location != null || !location.equals(""))
                    {
                        Geocoder geocoder = new Geocoder(getActivity());
                        try {
                            addressList = geocoder.getFromLocationName(location , 1);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude() , address.getLongitude());
                        produto.latlng = latLng;
                    }

                    String text = "Produto adicionado com sucesso!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();

            }
        });



        return rootView;
    }


}
