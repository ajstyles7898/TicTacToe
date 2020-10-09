package com.example.apptictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements EntryFragment.OnEntryListener, GameScreenFragment.OnScreeenListener, ExampleBottomSheetDialog.OnSheetDialogListener {

    private GameScreenFragment gameScreenFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFragment(new EntryFragment());
    }

    private void setFragment(Fragment fragment){
        if(fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onEntry(String player1, String player2) {
        gameScreenFragment = GameScreenFragment.newInstance(player1,player2,"0","0");
        setFragment(gameScreenFragment);
    }

    @Override
    public void onSheetDialogStartGame() {
        setFragment(gameScreenFragment);
    }

    @Override
    public void onNewScreenCreate(GameScreenFragment gameScreenFragment) {
        this.gameScreenFragment = gameScreenFragment;
    }
}
