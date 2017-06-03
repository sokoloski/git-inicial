package com.example.root.gobuy;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    private EditText edt;
    private FragmentManager fragmentManager;
    private MapsFragment frag;


    static List<Produto> produtos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);


        //criando lista de produtos.

        Produto mouse = new Produto();
        mouse.latlng = new LatLng(-25.4475657, -49.2486664);
        mouse.nome = "mouse";
        mouse.valor = "R$ 15,50";
        mouse.loja = "GDG_1";
        mouse.endereço = "Rua Nilo Peçanha, 300";


        Produto mouse_2 = new Produto();
        mouse_2.latlng = new LatLng(-25.4480254,-49.26004);
        mouse_2.nome = "mouse";
        mouse_2.valor = "R$ 20,50";
        mouse_2.loja = "GDG_@";
        mouse_2.endereço = "Rua Angelo zeni 430";
        produtos.add(mouse);
        produtos.add(mouse_2);


        Produto g1 = new Produto();
        g1.latlng = new LatLng(-25.4419466,-49.2624851);
        g1.nome = "gasolina";
        g1.valor = "R$ 2,50";
        g1.loja = "Posto 1";
        g1.endereço = "Rua do posto 1, 430";

        Produto g2 = new Produto();
        g2.latlng = new LatLng(-25.4277271,-49.257128);
        g2.nome = "gasolina";
        g2.valor = "R$ 3,50";
        g2.loja = "Posto 2";
        g2.endereço = "Rua do posto 2, 430";

        Produto g3 = new Produto();
        g3.latlng = new LatLng(-25.4239946,-49.27554);
        g3.nome = "gasolina";
        g3.valor = "R$ 4,50";
        g3.loja = "Posto 3";
        g3.endereço = "Rua do posto 3, 430";


        produtos.add(g1);
        produtos.add(g2);
        produtos.add(g3);
        ///////////////////////////////////

        //chamando o fragment do mapa
        fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        frag = new MapsFragment();
        transaction.add(R.id.container_map, frag, "Maps");
        transaction.commitAllowingStateLoss();


        edt = (EditText) rootView.findViewById(R.id.editText);

        //botão de pesquisa
        edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Search();
                    return true;
                }
                return false;
            }
        });
        return rootView;
    }


    private void Search() {

        //fechar teclado
        edt.clearFocus();
        InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(edt.getWindowToken(), 0);

        //Pesquisar
        String item_to_search = edt.getText().toString();
        if(item_to_search != null || !item_to_search.toString().equals(""))
        {
            frag.mMap.clear();

            int counter = 0;
            for (Produto p:
                    produtos) {

                if (p.nome.equals(item_to_search)) {
                    counter++;
                    LatLng latLng = new LatLng(p.latlng.latitude, p.latlng.longitude);

                    IconGenerator generator = new IconGenerator(getActivity());
                    generator.setBackground(getActivity().getDrawable(R.drawable.bubble_shadow));
                    LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService
                            (Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.activity_pin_fragment,null);

                    ((TextView) view.findViewById(R.id.tvi_nome)).setText(p.nome);
                    ((TextView) view.findViewById(R.id.tvi_valor)).setText(p.valor);


                    generator.setContentView(view);
                    Bitmap icon = generator.makeIcon();
                    MarkerOptions tp = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(icon));
                    tp.snippet(p.endereço);
                    tp.title(p.loja);
                    frag.mMap.addMarker(tp);


                }

            }
            if(counter>0){
                frag.mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            }
            else{
                toast("Produto não encontrado!");
            }
        }


    }

    private void toast(String Message) {
        String text = Message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getActivity(), text, duration);
        toast.show();
    }


}