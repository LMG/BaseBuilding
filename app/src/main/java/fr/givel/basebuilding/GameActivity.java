package fr.givel.basebuilding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.givel.basebuilding.controller.GameLoopThread;
import fr.givel.basebuilding.model.Boat;
import fr.givel.basebuilding.model.GameItem;
import fr.givel.basebuilding.model.MovingGameItem;
import fr.givel.basebuilding.model.World;
import fr.givel.basebuilding.model.behaviour.BoatBehaviour;
import fr.givel.basebuilding.utils.Coordinate;
import fr.givel.basebuilding.view.Camera;
import fr.givel.basebuilding.view.GameItemView;
import fr.givel.basebuilding.view.View3D;

public class GameActivity extends AppCompatActivity {
    @BindView(R.id.gameView)
    View3D gameView;
    @BindView(R.id.addBoatButton)
    AppCompatButton addBoatButton;
    @BindView(R.id.changeDestButton)
    AppCompatButton changeDestButton;

    GameLoopThread gameLoopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        BitmapFactory.Options bitmapOpts = new BitmapFactory.Options();
        bitmapOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmapOpts.inScaled = false;
        bitmapOpts.inDither = false;
        Bitmap boatBMP = BitmapFactory.decodeResource(getResources(), R.drawable.boat, bitmapOpts);
        final GameItemView boatView = new GameItemView(boatBMP, 17);
        Bitmap islandBMP = BitmapFactory.decodeResource(getResources(), R.drawable.island, bitmapOpts);

        GameItemView islandView = new GameItemView(islandBMP, 14);
        GameItem island = new GameItem(new Coordinate(10, 20, 0, 0), islandView);

        final List<GameItem> itemList = new ArrayList<GameItem>();
        itemList.add(island);

        final World world = new World(itemList, new Camera(3));
        gameView.setWorld(world);
        gameView.initView();

        gameLoopThread = new GameLoopThread(gameView);
        gameLoopThread.start();

        addBoatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int x = rand.nextInt(200);
                int y = rand.nextInt(500);
                int rot = rand.nextInt(360);
                Coordinate boatCoordinate = new Coordinate(x, y, 0, rot);
                Boat boat = new Boat(boatCoordinate, boatView);
                boat.setBehaviour(new BoatBehaviour(boat));
                itemList.add(boat);
            }
        });

        changeDestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int x = rand.nextInt(200);
                int y = rand.nextInt(500);
                Coordinate destCoordinate = new Coordinate(x, y, 0, 0);
                GameItem boat2 = new GameItem(new Coordinate(destCoordinate), boatView);
                itemList.add(boat2);
                for (MovingGameItem m : world.getMovingItems()) {
                    ((BoatBehaviour) (m.getBehaviour())).setDestination(destCoordinate);
                }
            }
        });

    }
}
