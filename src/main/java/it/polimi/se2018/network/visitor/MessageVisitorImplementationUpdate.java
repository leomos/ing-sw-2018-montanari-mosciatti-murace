package it.polimi.se2018.network.visitor;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.ConnectedClient;
import it.polimi.se2018.network.server.Room;

import java.rmi.RemoteException;

public class MessageVisitorImplementationUpdate implements MessageVisitorInterface {

    private Room room;

    private ConnectedClient player;

    private int currentPlayerPlayingId;


    public MessageVisitorImplementationUpdate(Room room) {
        this.room = room;
    }

    public void setPlayer(ConnectedClient player) {
        this.player = player;
    }

    public void setCurrentPlayerPlayingId(int currentPlayerPlayingId) {
        this.currentPlayerPlayingId = currentPlayerPlayingId;
    }

    private void update(ModelChangedMessage modelChangedMessage) {
        try {
            if(!player.isInactive())
                player.getClientInterface().update(modelChangedMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visitModelChangedMessageChangeGamePhase(ModelChangedMessageChangeGamePhase modelChangedMessageChangeGamePhase) {
    /*    if(this.player.isInactive()
                && (this.player.getId() == this.currentPlayerPlayingId)
                && modelChangedMessageChangeGamePhase.getGamePhase() == GamePhase.GAMEPHASE) {
//            Integer id = Integer.parseInt(modelChangedMessageRefresh.getIdPlayerPlaying());
            ;

//            Message fakeMessage = new PlayerMessageEndTurn(id);
  //          this.room.notifyView(fakeMessage);
        } else { */
            update(modelChangedMessageChangeGamePhase);
     //   }
    }


    @Override
    public void visitModelChangedMessagePlayerAFK(ModelChangedMessagePlayerAFK modelChangedMessagePlayerAFK) {
        update(modelChangedMessagePlayerAFK);
        if(modelChangedMessagePlayerAFK.getPlayer() == player.getId())
            player.setInactive(true);
    }

    @Override
    public void visitModelChangedMessageOnlyOnePlayerLeft(ModelChangedMessageOnlyOnePlayerLeft modelChangedMessageOnlyOnePlayerLeft) {
        update(modelChangedMessageOnlyOnePlayerLeft);
    }

    @Override
    public void visitMethodCallMessage(MethodCallMessage methodCallMessage) {

    }

    @Override
    public void visitModelChangedMessageConnected(ModelChangedMessageConnected modelChangedMessageConnected) {
        update(modelChangedMessageConnected);
    }

    @Override
    public void visitModelChangedMessageDiceArena(ModelChangedMessageDiceArena modelChangedMessageDiceArena) {
        update(modelChangedMessageDiceArena);
    }

    @Override
    public void visitModelChangedMessageRefresh(ModelChangedMessageRefresh modelChangedMessageRefresh) {
        update(modelChangedMessageRefresh);
    }

    @Override
    public void visitModelChangedMessageDiceOnPatternCard(ModelChangedMessageDiceOnPatternCard modelChangedMessageDiceOnPatternCard) {
        update(modelChangedMessageDiceOnPatternCard);
    }

    @Override
    public void visitModelChangedMessageEndGame(ModelChangedMessageEndGame modelChangedMessageEndGame) {
        update(modelChangedMessageEndGame);
        room.dispose();
    }

    @Override
    public void visitModelChangedMessageMoveFailed(ModelChangedMessageMoveFailed modelChangedMessageMoveFailed) {
        update(modelChangedMessageMoveFailed);
    }

    @Override
    public void visitModelChangedMessagePatternCard(ModelChangedMessagePatternCard modelChangedMessagePatternCard) {
        update(modelChangedMessagePatternCard);
    }

    @Override
    public void visitModelChangedMessagePrivateObjective(ModelChangedMessagePrivateObjective modelChangedMessagePrivateObjective) {
        update(modelChangedMessagePrivateObjective);
    }

    @Override
    public void visitModelChangedMessagePublicObjective(ModelChangedMessagePublicObjective modelChangedMessagePublicObjective) {
        update(modelChangedMessagePublicObjective);
    }

    @Override
    public void visitModelChangedMessageRound(ModelChangedMessageRound modelChangedMessageRound) {
        update(modelChangedMessageRound);
    }

    @Override
    public void visitModelChangedMessageNewEvent(ModelChangedMessageNewEvent modelChangedMessageNewEvent) {
        update(modelChangedMessageNewEvent);
    }

    @Override
    public void visitModelChangedMessageToolCard(ModelChangedMessageToolCard modelChangedMessageToolCard) {
        update(modelChangedMessageToolCard);
    }

    @Override
    public void visitModelChangedMessageTokensLeft(ModelChangedMessageTokensLeft modelChangedMessageTokensLeft) {
        update(modelChangedMessageTokensLeft);
    }

    @Override
    public void visitPlayerMessageDie(PlayerMessageDie playerMessageDie) {

    }

    @Override
    public void visitPlayerMessageEndTurn(PlayerMessageEndTurn playerMessageEndTurn) {

    }

    @Override
    public void visitPlayerMessageSetup(PlayerMessageSetup playerMessageSetup) {

    }

    @Override
    public void visitPlayerMessageToolCard(PlayerMessageToolCard playerMessageToolCard) {

    }

    @Override
    public void visitPlayerMessageNotAFK(PlayerMessageNotAFK playerMessageNotAFK) {

    }

    @Override
    public void visitHeartbeatMessage(HeartbeatMessage heartbeatMessage) {

    }
}
