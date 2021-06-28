package com.example.project1.ui.help;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project1.Navigation;
import com.example.project1.R;
import com.example.project1.databinding.FragmentHelpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class HelpFragment extends Fragment {

    private HelpViewModel helpViewModel;
    private FragmentHelpBinding binding;

    // Popup
    private Dialog diag1;
    private Dialog diag2;

    private Button popchangepass;
    private TextView closepop;
    private Button changepass;
    private EditText currentpass;
    private EditText newpass;

    private ImageView pic1;
    private ImageView pic2;
    private ImageView pic3;
    private Button changepic;
    private TextView closepop2;

    private Context context1;
    private Context context2;
    private FirebaseUser user;
    Context context;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        diag1 = new Dialog(context1);
        diag2 = new Dialog(context2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        context = getActivity().getApplicationContext();

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        helpViewModel =
                new ViewModelProvider(this).get(HelpViewModel.class);
        binding = FragmentHelpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        popchangepass = root.findViewById(R.id.changepass_help);
        popchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup(view);
            }
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        context1 = context;
    }
    // Method for creating password change popup
    public void ShowPopup(View v){
        diag1.show();
        diag1.setContentView(R.layout.pass_change_pop);
        diag1.setCanceledOnTouchOutside(false);
        closepop = (TextView) diag1.findViewById(R.id.pass_close);
        changepass = (Button) diag1.findViewById(R.id.confirm_pass_change);
        currentpass = (EditText) diag1.findViewById(R.id.edt_current_pass);
        newpass = (EditText) diag1.findViewById(R.id.edt_new_pass);
        closepop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                diag1.dismiss();
            }
        });

        changepass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String currentpassenter = currentpass.getText().toString();
                String newpassenter = newpass.getText().toString();
                user.updatePassword(newpassenter).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(context, "Password Changed", Toast.LENGTH_SHORT).show();
                            Intent nav = new Intent(context, Navigation.class);
                            startActivity(nav);
                        }

                    }
                });
                diag1.dismiss();
            }
        });

    }
    // Method for creating profile picture change popup
    public void ShowPopup2(View v){
        diag2.show();
        diag2.setContentView(R.layout.profile_pic_change_pop);
        diag2.setCanceledOnTouchOutside(false);
        closepop2 = (TextView) diag2.findViewById(R.id.pic_close);
        pic1 = (ImageView) diag2.findViewById(R.id.first_pic);
        pic2 = (ImageView) diag2.findViewById(R.id.second_pic);
        pic3 = (ImageView) diag2.findViewById(R.id.third_pic);
        changepic = (Button) diag2.findViewById(R.id.confirm_pic_change);
        closepop2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ diag2.dismiss(); }
        });



    }

}