package com.example.ventas;

import android.os.Bundle;
import android.widget.GridView;

import com.example.ventas.ui.home.ListaPagados;
import com.example.ventas.ui.home.PagadosTO;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        /*database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        for (int i = 0; i < 150; i++) {
            PagadosTO p= new PagadosTO();
            int cont= i +1;
            p.setId(String.valueOf(cont));
            p.setStatus(0);
            p.setNumero(cont);
            p.setDesc("00"+cont);

            ref.child("pagados").child(p.getId()).setValue(p);
        }*/


    }



}
