package com.example.root.gobuy;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.Icon;
import android.graphics.drawable.PictureDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.location.LocationServices;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.maps.android.ui.IconGenerator;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    private EditText edt;
    private AlertDialog alerta;


    private FragmentManager fragmentManager;
    MapsFragment frag;

    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    List<Produto> produtos = new ArrayList<Produto>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);


        //criando lista de produtos.

        Produto mouse = new Produto();
        mouse.latlng = new LatLng(-25.4475657, -49.2486664);
        mouse.nome = "mouse";
        mouse.valor = "R$ 15,50";
        
        Produto mouse_2 = new Produto();
        mouse_2.latlng = new LatLng(-25.4480254,-49.26004);
        mouse_2.nome = "mouse";
        mouse_2.valor = "R$ 20,50";
                
        produtos.add(mouse);
        produtos.add(mouse_2);

        ///////////////////////////////////

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
        // Inflate the layout for this fragment
        return rootView;
    }


    private void Search() {

        //fechar teclado
        edt.clearFocus();
        InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(edt.getWindowToken(), 0);

        //Pesquisar
        String item_to_search = edt.getText().toString();
       // List<Address> addressList = null;
        if(item_to_search != null || !item_to_search.equals(""))
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
                    frag.mMap.addMarker(tp);
                }

            }
            if(counter>0){
                frag.mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            }
            else{
                dialog("Não encontrado", "Produto não encontrado!" );
            }
        }



    }

    private void dialog(String Title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Title);
        builder.setMessage(Message).setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {}
        });
        alerta = builder.create();
        alerta.show();
    }


}
