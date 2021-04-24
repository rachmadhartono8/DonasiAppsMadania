package com.hartonostudio.donasiappsmadania.Menu.Profil;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hartonostudio.donasiappsmadania.R;


public class ProfilFragment extends Fragment {

        private TextView GetEmail, GetNama, GetPassword;
        private FirebaseAuth auth;
        private FirebaseDatabase getDatabase;
        private DatabaseReference getRefenence;
        private String GetUserID;

        private Context mContext;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            return inflater.inflate(R.layout.fragment_profil, container, false);
        }



        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

        }

        @Override
        public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
            menu.clear();
            inflater.inflate(R.menu.menu_edit_profile, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.nav_menu_edit) {
                Toast.makeText(mContext, "Menu Edit Profile", Toast.LENGTH_SHORT).show();
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            mContext = context;
        }
    }
