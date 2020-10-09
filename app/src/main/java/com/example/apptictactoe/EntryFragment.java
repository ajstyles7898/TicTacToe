package com.example.apptictactoe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class EntryFragment extends Fragment {
    private EditText etPlayer1, etPlayer2;
    private Button btnMoveToGame;

    private OnEntryListener mListener;

    public EntryFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry, container, false);

        initialise(view);
        setButtonListener();

        return view;
    }

    private void initialise(View view){
        etPlayer1 = view.findViewById(R.id.et_player1);
        etPlayer2 = view.findViewById(R.id.et_player2);
        btnMoveToGame = view.findViewById(R.id.btn_move_to_game);
    }

    private void setButtonListener(){
        btnMoveToGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player1 = etPlayer1.getText().toString();
                String player2 = etPlayer2.getText().toString();

                onButtonPressed(player1,player2);
            }
        });
    }

    public void onButtonPressed(String player1, String player2) {
        if (mListener != null) {
            mListener.onEntry(player1,player2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEntryListener) {
            mListener = (OnEntryListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEntryListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnEntryListener {
        void onEntry(String player1, String player2);
    }
}
