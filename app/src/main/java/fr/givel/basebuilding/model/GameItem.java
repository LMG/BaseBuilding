package fr.givel.basebuilding.model;

import fr.givel.basebuilding.utils.Coordinate;
import fr.givel.basebuilding.view.GameItemView;

/**
 * Created by lmg on 25/05/17.
 */

public class GameItem {
    Coordinate coordinate;
    GameItemView view;

    public GameItem(Coordinate coordinate, GameItemView view) {
        this.coordinate = coordinate;
        this.view = view;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public GameItemView getView() {
        return view;
    }

    public void setView(GameItemView view) {
        this.view = view;
    }
}
