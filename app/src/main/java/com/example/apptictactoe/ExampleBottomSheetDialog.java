package com.example.apptictactoe;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ExampleBottomSheetDialog extends BottomSheetDialogFragment {
    public static final String ARG_RESULT="argResult";

    private TextView tvResult;
    private Button btnNewGame;
    private Button btnExit;

    private int result;

    private OnSheetDialogListener listener;

    public static ExampleBottomSheetDialog newInstance(int result) {

        Bundle args = new Bundle();
        args.putInt(ARG_RESULT,result);
        ExampleBottomSheetDialog fragment = new ExampleBottomSheetDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        result= getArguments().getInt(ARG_RESULT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_bottom_sheet, container, false);

        initialise(view);
        setTextViewResult();
        setButtons();

        return view;
    }

    private void initialise(View view){
        tvResult = view.findViewById(R.id.tv_result);
        btnNewGame = view.findViewById(R.id.btn_new_game);
        btnExit = view.findViewById(R.id.btn_exit);
    }

    private void setTextViewResult(){
        if(result==-1){
            tvResult.setText("Draw");
        }else if(result==0){
            tvResult.setText("Player 1 Wins");
        }else{
            tvResult.setText("Player 2 Wins");
        }
    }

    private void setButtons(){
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSheetDialogStartGame();
                dismiss();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExampleBottomSheetDialog.OnSheetDialogListener) {
            listener = (ExampleBottomSheetDialog.OnSheetDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEntryListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnSheetDialogListener{
        void onSheetDialogStartGame();
    }
}
