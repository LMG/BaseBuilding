package fr.givel.basebuilding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.givel.basebuilding.controller.GameLoopThread;
import fr.givel.basebuilding.model.GameItem;
import fr.givel.basebuilding.model.World;
import fr.givel.basebuilding.utils.Coordinate;
import fr.givel.basebuilding.view.Camera;
import fr.givel.basebuilding.view.GameItemView;
import fr.givel.basebuilding.view.View3D;

public class ViewTestActivity extends AppCompatActivity {
    @BindView(R.id.mainView)
    View3D mainView;
    @BindView(R.id.altView1)
    View3D altView1;
    @BindView(R.id.altView2)
    View3D altView2;
    private GameLoopThread gameLoopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);
        ButterKnife.bind(this);

        gameLoopThread = new GameLoopThread(mainView);

        BitmapFactory.Options bitmapOpts = new BitmapFactory.Options();
        bitmapOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmapOpts.inScaled = false;
        bitmapOpts.inDither = false;
        Bitmap boatBMP = BitmapFactory.decodeResource(getResources(), R.drawable.boat, bitmapOpts);
        Bitmap islandBMP = BitmapFactory.decodeResource(getResources(), R.drawable.island, bitmapOpts);

        GameItemView islandView = new GameItemView(islandBMP, 14);
        GameItem island = new GameItem(new Coordinate(10, 20, 0, 0), islandView);
        GameItemView boatView = new GameItemView(boatBMP, 17);
        GameItemView boatView2 = new GameItemView(boatBMP, 17);
        GameItem boat = new GameItem(new Coordinate(15, 15, 0, 0), boatView);
        GameItem boat2 = new GameItem(new Coordinate(45, 55, 0, 0), boatView2);

        List<GameItem> itemList2 = new ArrayList<GameItem>();
        itemList2.add(island);
        itemList2.add(boat);
        itemList2.add(boat2);

        World w2 = new World(itemList2, new Camera(15));
        mainView.setWorld(w2);
        mainView.initView();

        List<GameItem> itemList = new ArrayList<GameItem>();
        itemList.add(boat2);

        World w = new World(itemList, new Camera(10));
        altView1.setWorld(w);
        altView1.initView();
        altView2.setWorld(w);
        altView2.initView();
    }
}
