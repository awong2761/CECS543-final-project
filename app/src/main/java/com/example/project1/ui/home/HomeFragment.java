package com.example.project1.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.project1.R;
import com.example.project1.UserInfo;
import com.example.project1.UserProfile;
import com.example.project1.databinding.FragmentHomeBinding;
import com.example.project1.ui.food.FoodFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Observable;

public class HomeFragment extends Fragment {
    public static TextView calorieDisplay;
    public static TextView caloriesLeftMessage;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase userData;
    private String caloriesSub = "";
    private String brand;
    private String food;
    private int caloriesSubInt;
    private int totalCals;
    private int totalCalsFinal;
    public UserProfile userProfile;
    public static String caloriesSub1 = "";


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        caloriesLeftMessage= root.findViewById(R.id.caloriesLeft_msg);
        calorieDisplay = root.findViewById(R.id.calorie_display);
        String totalCalories;
        firebaseAuth = FirebaseAuth.getInstance();
        userData = FirebaseDatabase.getInstance();





        DatabaseReference databaseReference = userData.getReference(firebaseAuth.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                userProfile = snapshot.getValue(UserProfile.class);
                calorieDisplay.setText(userProfile.getCaloriesLeft());
                caloriesSub = userProfile.getCurrentFoodCalories();
                firebaseAuth = FirebaseAuth.getInstance();
                if(Integer.parseInt(userProfile.getCaloriesLeft()) < 0) {
                    TextPaint paint = calorieDisplay.getPaint();
                    float width = paint.measureText(calorieDisplay.getText().toString());
                    Shader textShader = new LinearGradient(0, 0, width, calorieDisplay.getTextSize(),
                            new int[]{
                                    Color.parseColor("#FF0000"),
                                    Color.parseColor("#FFD79C"),
                            }, null, Shader.TileMode.CLAMP);
                    calorieDisplay.getPaint().setShader(textShader);
                    calorieDisplay.setTextColor(Color.parseColor("#FF0000"));
                    caloriesLeftMessage.setText("calories over today");
                }
                else{
                    TextPaint paint = calorieDisplay.getPaint();
                    float width = paint.measureText(calorieDisplay.getText().toString());
                    Shader textShader = new LinearGradient(0, 0, width, calorieDisplay.getTextSize(),
                            new int[]{
                                    Color.parseColor("#3DBDB0"),
                                    Color.parseColor("#04B5CE"),
                            }, null, Shader.TileMode.CLAMP);
                    calorieDisplay.getPaint().setShader(textShader);
                    calorieDisplay.setTextColor(Color.parseColor("#3DBDB0"));
                    caloriesLeftMessage.setText("calories left today");
                }

                FirebaseUser user = firebaseAuth.getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                if(!userProfile.getCurrentFoodCalories().equals("0")){
                    totalCals = Integer.parseInt(userProfile.getCaloriesLeft());
                    caloriesSubInt = Integer.parseInt(caloriesSub);
                    totalCals = totalCals - caloriesSubInt;
                    databaseReference.child(user.getUid()).child("caloriesLeft").setValue(String.valueOf(totalCals));
                    databaseReference.child(user.getUid()).child("currentFoodCalories").setValue("0");
                }
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