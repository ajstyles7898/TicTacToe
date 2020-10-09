package com.example.apptictactoe;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class GameScreenFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PLAYER1 = "argPlayer1";
    private static final String ARG_PLAYER2 = "argPlayer2";
    private static final String ARG_PLAYER1_SCORE = "argPlayer1Score";
    private static final String ARG_PLAYER2_SCORE = "argPlayer2Score";

    private String strPlayer1;
    private String strPlayer2;
    private String strPlayer1Score;
    private String strPlayer2Score;

    private String[] marks={"O","X"};
    private String currentPlayer;
    private int[] block;

    private TextView tvPlayer1,tvPlayer2;
    private TextView tvPlayer1Score, tvPlayer2Score;
    private TextView[] tvBlock;

    private OnScreeenListener mListener;

    public GameScreenFragment() {
        // Required empty public constructor
    }

    public static GameScreenFragment newInstance(String strPlayer1, String strPlayer2, String strPlayer1Score, String strPlayer2Score) {
        GameScreenFragment fragment = new GameScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLAYER1,strPlayer1);
        args.putString(ARG_PLAYER2,strPlayer2);
        args.putString(ARG_PLAYER1_SCORE,strPlayer1Score);
        args.putString(ARG_PLAYER2_SCORE,strPlayer2Score);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            strPlayer1 = getArguments().getString(ARG_PLAYER1);
            strPlayer2 = getArguments().getString(ARG_PLAYER2);
            strPlayer1Score = getArguments().getString(ARG_PLAYER1_SCORE);
            strPlayer2Score = getArguments().getString(ARG_PLAYER2_SCORE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_screen, container, false);
        initialise(view);
        setTextViews();
        setClickLitener();

        return view;
    }

    private void initialise(View view){
        currentPlayer = marks[0];

        tvPlayer1 = view.findViewById(R.id.tv_player1);
        tvPlayer2 = view.findViewById(R.id.tv_player2);

        tvPlayer1Score = view.findViewById(R.id.tv_score_player1);
        tvPlayer2Score = view.findViewById(R.id.tv_score_player2);

        block = new int[9];
        tvBlock = new TextView[9];
        String id = "tv_block";
        int temp;
        for(int i=0;i<9;i++){
            temp = this.getResources().getIdentifier(id+(i+1),"id",getActivity().getPackageName());
            tvBlock[i] = view.findViewById(temp);
            block[i] = 0;
        }
    }

    private void setTextViews(){
        tvPlayer1.setText(strPlayer1);
        tvPlayer2.setText(strPlayer2);
        tvPlayer1Score.setText(strPlayer1Score);
        tvPlayer2Score.setText(strPlayer2Score);

        for(int i=0;i<9;i++){
                tvBlock[i].setText("");
        }
    }

    private void setClickLitener(){
        for(int i=0;i<9;i++){
            tvBlock[i].setOnClickListener(GameScreenFragment.this);
        }
    }

    @Override
    public void onClick(View v) {
        String tv= v.getResources().getResourceEntryName(v.getId());
        int btnNumber = tv.charAt(tv.length()-1)-'0' ;

        if(block[btnNumber-1]!=0){
            Toast.makeText(getActivity(),"Pressed Again",Toast.LENGTH_SHORT).show();
            return;
        }

        int currentIndex = Arrays.asList(marks).indexOf(currentPlayer);
        tvBlock[btnNumber-1].setText(currentPlayer);
        block[btnNumber-1] = currentIndex+1;

        if(!checkWinner(currentIndex)) {
            checkAllPressed();
        }
        currentPlayer = marks[1-currentIndex ];
    }

    private boolean checkWinner(int currentPlayer){
        boolean winner = false;
        if(block[0]!=0 && block[0]==block[1] && block[2]==block[1]){
            winner = true;
        }
        else if(block[3]!=0 && block[3]==block[4] && block[5]==block[4]){
            winner = true;
        }
        else if(block[6]!=0 && block[6]==block[7] && block[8]==block[7]){
            winner = true;
        }
        else if(block[0]!=0 && block[0]==block[3] && block[6]==block[3]){
            winner = true;
        }
        else if(block[1]!=0 && block[1]==block[4] && block[7]==block[4]){
            winner = true;
        }
        else if(block[2]!=0 && block[2]==block[5] && block[8]==block[5]){
            winner = true;
        }
        else if(block[4]!=0 && block[0]==block[4] && block[8]==block[4]){
            winner = true;
        }
        else if(block[4]!=0 && block[2]==block[4] && block[6]==block[4]){
            winner = true;
        }
        if(winner){
            if(currentPlayer==0){
                int score = Integer.parseInt(tvPlayer1Score.getText().toString());
                tvPlayer1Score.setText(""+(score+1));
            }else{
                int score = Integer.parseInt(tvPlayer2Score.getText().toString());
                tvPlayer2Score.setText(""+(score+1));
            }
            bottomSheetResult(currentPlayer);
            return true;
        }
        return false;
    }

    private void checkAllPressed(){
        int i;
        for(i=0;i<9;i++){
            if(block[i]==0){
                break;
            }
        }
        if(i==9){
            bottomSheetResult(-1);
        }
    }

    private void bottomSheetResult(int result){
        ExampleBottomSheetDialog bottomSheet = ExampleBottomSheetDialog.newInstance(result);
        bottomSheet.setCancelable(false);
        bottomSheet.show(getActivity().getSupportFragmentManager(),"examplebottomsheet");

        String player1 = tvPlayer1.getText().toString();
        String player2 = tvPlayer2.getText().toString();
        String score1 = tvPlayer1Score.getText().toString();
        String score2 = tvPlayer2Score.getText().toString();

        GameScreenFragment gameScreenFragment = GameScreenFragment.newInstance(player1,player2,score1,score2);
        mListener.onNewScreenCreate(gameScreenFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnScreeenListener) {
            mListener = (OnScreeenListener) context;
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

    public interface OnScreeenListener {
        void onNewScreenCreate(GameScreenFragment gameScreenFragment);
    }
}
