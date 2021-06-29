package com.example.project1.ui.home;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.project1.R;
import com.example.project1.UserInfo;
import com.example.project1.UserProfile;
import com.example.project1.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {
    private TextView calorieDisplay;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase userData;


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calorieDisplay = root.findViewById(R.id.calorie_display);
        String totalCalories;
        firebaseAuth = FirebaseAuth.getInstance();
        userData = FirebaseDatabase.getInstance();

        TextPaint paint = calorieDisplay.getPaint();
        float width = paint.measureText(calorieDisplay.getText().toString());
        Shader textShader = new LinearGradient(0, 0, width, calorieDisplay.getTextSize(),
                new int[]{
                        Color.parseColor("#3DBDB0"),
                        Color.parseColor("#04B5CE"),
                }, null, Shader.TileMode.CLAMP);
        calorieDisplay.getPaint().setShader(textShader);
        calorieDisplay.setTextColor(Color.parseColor("#3DBDB0"));

        DatabaseReference databaseReference = userData.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                calorieDisplay.setText(userProfile.getCaloriesLeft());

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}