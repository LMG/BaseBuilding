package fr.givel.basebuilding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.givel.basebuilding.controller.GameLoopThread;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.view3DTestButton)
    AppCompatButton view3DTestButton;
    @BindView(R.id.gameTestButton)
    AppCompatButton gameTestButton;
    private GameLoopThread gameLoopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        view3DTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewTestActivity.class);
                startActivity(intent);
            }
        });

        gameTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }
}
