package com.example.ventas.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ventas.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<PagadosTO> lVentas= new ArrayList<>();
    public GridView gridview;

    private HomeViewModel homeViewModel;

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private ArrayAdapter<PagadosTO> arrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        gridview = root.findViewById(R.id.grid_pagados);

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        homeContenido();
        return root;
    }

    private void listaVentas(){
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        final View view;
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.helper_listado_pagados, null);

        ref.child("pagados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lVentas.clear();


                for (DataSnapshot objDataSnapshot: dataSnapshot.getChildren()) {
                    final PagadosTO p1 = objDataSnapshot.getValue(PagadosTO.class);

                    final TextView numeroPagado = view.findViewById(R.id.numeropagado);
                    numeroPagado.setText(String.valueOf(p1.getId()));

                    if(p1.getStatus()==0){
                        numeroPagado.setBackgroundColor(Color.parseColor("#008577"));
                    }else if(p1.getStatus()==1){
                        numeroPagado.setBackgroundColor(Color.RED);
                    }

                    numeroPagado.(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int estado = 0;
                            if(p1.getStatus() == 0 ){
                                estado = 1;
                                //numeroPagado.setBackgroundColor(Color.parseColor("#008577"));
                            }else if(p1.getStatus() == 1){
                                estado = 0;
                                //numeroPagado.setBackgroundColor(Color.RED);
                            }

                            PagadosTO p= new PagadosTO();
                            p.setId(p1.getId());
                            p.setStatus(estado);
                            p.setNumero(p1.getNumero());
                            p.setDesc(p1.getDesc());

                            ref.child("pagados").child(p.getId()).setValue(p);
                        }
                    });
                    lVentas.add(p1);
                    arrayAdapter = new ArrayAdapter<PagadosTO>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,lVentas);
                    gridview.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void homeContenido(){
        listaVentas();
//gridview.setEmptyView(v);

        /*ListaPagados adapter = new ListaPagados(getActivity().getApplicationContext(), lVentas);
        gridview.setAdapter(adapter);*/
    }
}