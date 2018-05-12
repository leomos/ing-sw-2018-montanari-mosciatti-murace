package it.polimi.se2018.model;

import it.polimi.se2018.utils.Observable;

public class Model extends Observable<Object> {

    private Table table;

    public Model(){

    }

    public Table getTable() {
        return table;
    }

    public void calculateScore() {
        return;
    }
}
