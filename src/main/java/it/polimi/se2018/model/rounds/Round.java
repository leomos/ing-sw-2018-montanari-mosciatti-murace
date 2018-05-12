package it.polimi.se2018.model.rounds;

/* TODO: tests and docs */
public class Round {

    private int id;

    private int idPlayerPlaying = -1;

    private int[] rolledDiceLeft;

    private int[] players;

    public Round(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdPlayerPlaying() {
        return idPlayerPlaying;
    }

    public int[] getRolledDiceLeft() {
        return rolledDiceLeft;
    }

    public void setPlayers(int[] players) {
        this.players = players;
    }


    public void setRolledDiceLeft(int[] rolledDiceLeft) {
        this.rolledDiceLeft = rolledDiceLeft;
    }

    public void setFirstPlayer(int idFirstPlayer) throws RoundFirstPlayerAlreadySet {
        if(idPlayerPlaying != -1) {
            throw new RoundFirstPlayerAlreadySet();
        }
        idPlayerPlaying = idFirstPlayer;
    }

    /* TODO: implement next player logic. */
    public boolean setNextPlayer() {
        return true;
    }
}
