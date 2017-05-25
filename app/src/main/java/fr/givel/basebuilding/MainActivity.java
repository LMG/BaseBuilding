package fr.givel.basebuilding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.givel.basebuilding.controller.GameLoopThread;
import fr.givel.basebuilding.view.View3D;

public class MainActivity extends AppCompatActivity {

    private GameLoopThread gameLoopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameLoopThread = new GameLoopThread((View3D) findViewById(R.id.mainView));
    }
}
