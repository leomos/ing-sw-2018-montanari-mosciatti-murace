package it.polimi.se2018.network.visitor;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.server.ClientImplementationSocket;

public class MessageVisitorImplementationClient implements MessageVisitorInterface {

    private ClientImplementationSocket clientImplementationSocket;

    public MessageVisitorImplementationClient(ClientImplementationSocket clientImplementationSocket) {
        this.clientImplementationSocket = clientImplementationSocket;
    }

    @Override
    public void visitMethodCallMessage(MethodCallMessage methodCallMessage) {
        System.out.println("MethodCallMessage");
        clientImplementationSocket.unlockAndSetReady();
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
    public void visitModelChangedMessageRefresh(ModelChangedMessageRefresh modelChangedMessageRefresh) {

    }

    @Override
    public void visitModelChangedMessageRound(ModelChangedMessageRound modelChangedMessageRound) {

    }

    @Override
    public void visitModelChangedMessageToolCard(ModelChangedMessageToolCard modelChangedMessageToolCard) {

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
}
