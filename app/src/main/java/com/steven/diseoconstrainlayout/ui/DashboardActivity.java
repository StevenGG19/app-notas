package com.steven.diseoconstrainlayout.ui;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.steven.diseoconstrainlayout.common.Constante;
import com.steven.diseoconstrainlayout.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class DashboardActivity extends AppCompatActivity {
    private FloatingActionButton boton;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            item -> {
                Fragment f = null;

                switch (item.getItemId()) {
                    case R.id.navigation_notas:
                        f = NotaListaFragment.newInstance(Constante.NOTA_ALL);
                        break;
                    case R.id.navigation_favoritas:
                        f = NotaListaFragment.newInstance(Constante.NOTA_FAV);
                        break;
                }

                if (f != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, f)
                            .commit();
                    return true;
                }

                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        boton = findViewById(R.id.fab);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, NotaListaFragment.newInstance(Constante.NOTA_ALL))
                .commit();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        boton.setOnClickListener(item -> mostrarDialogoNuevaNota());
    }

    private void mostrarDialogoNuevaNota() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        NuevaNotaDialogFragment dialogFragment = new NuevaNotaDialogFragment();
        dialogFragment.show(fragmentManager, "NuevaNotaDialogFragment");
    }
}