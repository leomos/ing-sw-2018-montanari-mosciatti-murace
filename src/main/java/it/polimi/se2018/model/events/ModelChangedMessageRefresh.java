package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

public class ModelChangedMessageRefresh extends ModelChangedMessage implements Message {

    private static final long serialVersionUID = 2000L;

    private Integer idPlayerPlaying;

    private String playerName;

    /**
     * When the view receive this message, they refresh the view of the game
     * @param idPlayerPlaying player id that is gonna play this turn
     */
    public ModelChangedMessageRefresh(Integer idPlayerPlaying, String playerName){
        this.idPlayerPlaying = idPlayerPlaying;
        this.playerName = playerName;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitModelChangedMessageRefresh(this);
    }
    public Integer getIdPlayerPlaying() {
        return idPlayerPlaying;
    }

    public String getPlayerName() {
        return playerName;
    }
}
