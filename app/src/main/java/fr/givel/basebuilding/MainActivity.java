package fr.givel.basebuilding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.givel.basebuilding.controller.GameLoopThread;
import fr.givel.basebuilding.model.GameItem;
import fr.givel.basebuilding.model.World;
import fr.givel.basebuilding.utils.Coordinate;
import fr.givel.basebuilding.view.Camera;
import fr.givel.basebuilding.view.GameItemView;
import fr.givel.basebuilding.view.View3D;

public class MainActivity extends AppCompatActivity {
    private GameLoopThread gameLoopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View3D mainView = (View3D) findViewById(R.id.mainView);
        gameLoopThread = new GameLoopThread(mainView);

        BitmapFactory.Options bitmapOpts = new BitmapFactory.Options();
        bitmapOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmapOpts.inScaled = false;
        bitmapOpts.inDither = false;
        Bitmap boatBMP = BitmapFactory.decodeResource(getResources(), R.drawable.boat, bitmapOpts);

        GameItemView boatView = new GameItemView(boatBMP);
        GameItem boat = new GameItem(new Coordinate(10, 20, 0, 0), boatView);
        List<GameItem> itemList = new ArrayList<GameItem>();
        itemList.add(boat);

        World w = new World(itemList, new Camera(30));
        mainView.setWorld(w);
        mainView.initView();
    }
}
