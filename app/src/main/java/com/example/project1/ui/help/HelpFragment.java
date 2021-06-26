package com.example.project1.ui.help;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.project1.R;
import com.example.project1.databinding.FragmentHelpBinding;
import com.example.project1.ui.food.FoodFragment;

public class HelpFragment extends Fragment {

    private HelpViewModel helpViewModel;
    private FragmentHelpBinding binding;
    private Context context1;
    private Button popchangepass;
    private Dialog diag1;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        diag1 = new Dialog(context1);
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
    public void ShowPopup(View v){
        TextView closepop;
        Button changepass;
        diag1.setContentView(R.layout.pass_change_pop);
        diag1.setCanceledOnTouchOutside(false);
        closepop = (TextView) diag1.findViewById(R.id.pass_close);
        changepass = (Button) diag1.findViewById(R.id.confirm_pass_change);
        closepop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                diag1.dismiss();
            }
        });
        diag1.show();
    }

}