package it.polimi.se2018.network.visitor;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.server.Room;

public class MessageVisitorDisconnectionImplementation implements MessageVisitorInterface {

    private Room room;

    public MessageVisitorDisconnectionImplementation(Room room) {
        this.room = room;
    }

    @Override
    public void visitModelChangedMessageRefresh(ModelChangedMessageRefresh modelChangedMessageRefresh) {
        //System.out.println("visitor");
        Integer id = Integer.parseInt(modelChangedMessageRefresh.getIdPlayerPlaying());
        Message fakeMessage = new PlayerMessageEndTurn(id);
        room.notifyView(fakeMessage);
    }

    @Override
    public void visitMethodCallMessage(MethodCallMessage methodCallMessage) {

    }

    @Override
    public void visitModelChangedMessageConnected(ModelChangedMessageConnected modelChangedMessageConnected) {

    }

    @Override
    public void visitModelChangedMessageDiceArena(ModelChangedMessageDiceArena modelChangedMessageDiceArena) {

    }

    @Override
    public void visitModelChangedMessageDiceOnPatternCard(ModelChangedMessageDiceOnPatternCard modelChangedMessageDiceOnPatternCard) {

    }

    @Override
    public void visitModelChangedMessageEndGame(ModelChangedMessageEndGame modelChangedMessageEndGame) {

    }

    @Override
    public void visitModelChangedMessageMoveFailed(ModelChangedMessageMoveFailed modelChangedMessageMoveFailed) {

    }

    @Override
    public void visitModelChangedMessagePatternCard(ModelChangedMessagePatternCard modelChangedMessagePatternCard) {

    }

    @Override
    public void visitModelChangedMessagePrivateObjective(ModelChangedMessagePrivateObjective modelChangedMessagePrivateObjective) {

    }

    @Override
    public void visitModelChangedMessagePublicObjective(ModelChangedMessagePublicObjective modelChangedMessagePublicObjective) {

    }

    @Override
    public void visitModelChangedMessageRound(ModelChangedMessageRound modelChangedMessageRound) {

    }

    @Override
    public void visitModelChangedMessageNewEvent(ModelChangedMessageNewEvent modelChangedMessageNewEvent) {

    }

    @Override
    public void visitModelChangedMessageToolCard(ModelChangedMessageToolCard modelChangedMessageToolCard) {

    }

    @Override
    public void visitModelChangedMessageTokensLeft(ModelChangedMessageTokensLeft modelChangedMessageTokensLeft) {

    }

    @Override
    public void visitModelChangedMessagePlayerAFK(ModelChangedMessagePlayerAFK modelChangedMessagePlayerAFK) {

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
