package it.polimi.se2018.network.visitor;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.client.ServerImplementationSocket;

public class MessageVisitorImplementationServer implements MessageVisitorInterface {

    private ServerImplementationSocket serverImplementationSocket;

    public MessageVisitorImplementationServer(ServerImplementationSocket serverImplementationSocket) {
        this.serverImplementationSocket = serverImplementationSocket;
    }

    @Override
    public void visitMethodCallMessage(MethodCallMessage methodCallMessage) {
        Message answer;
        switch (methodCallMessage.getMethodName()) {
            case "getDieFromPatternCard":
                answer = new MethodCallMessage(methodCallMessage.getMethodName(),
                        serverImplementationSocket.getViewClient().getDieFromPatternCard());
                break;
            case "getDieFromRoundTrack":
                answer = new MethodCallMessage(methodCallMessage.getMethodName(),
                        serverImplementationSocket.getViewClient().getDieFromRoundTrack());
                break;
            case "getIncrementedValue":
                answer = new MethodCallMessage(methodCallMessage.getMethodName(),
                        serverImplementationSocket.getViewClient().getIncrementedValue());
                break;
            case "getPositionInPatternCard":
                answer = new MethodCallMessage(methodCallMessage.getMethodName(),
                        serverImplementationSocket.getViewClient().getPositionInPatternCard());
                break;
            case "getValueForDie":
                answer = new MethodCallMessage(methodCallMessage.getMethodName(),
                        serverImplementationSocket.getViewClient().getValueForDie());
                break;
            case "askForPatternCard":
                answer = new MethodCallMessage(methodCallMessage.getMethodName(),
                        serverImplementationSocket.getViewClient().askForPatternCard());
                break;
            default:
                /* TODO: send a WrongMethodException? */
                answer = null;
                break;
        }
        serverImplementationSocket.writeMessage(answer);
    }

    @Override
    public void visitModelChangedMessageConnected(ModelChangedMessageConnected modelChangedMessageConnected) {
        serverImplementationSocket.updateView(modelChangedMessageConnected);
    }

    @Override
    public void visitModelChangedMessageDiceArena(ModelChangedMessageDiceArena modelChangedMessageDiceArena) {
        serverImplementationSocket.updateView(modelChangedMessageDiceArena);
    }

    @Override
    public void visitModelChangedMessageDiceOnPatternCard(ModelChangedMessageDiceOnPatternCard modelChangedMessageDiceOnPatternCard) {
        serverImplementationSocket.updateView(modelChangedMessageDiceOnPatternCard);
    }

    @Override
    public void visitModelChangedMessageEndGame(ModelChangedMessageEndGame modelChangedMessageEndGame) {
        serverImplementationSocket.updateView(modelChangedMessageEndGame);
    }

    @Override
    public void visitModelChangedMessageMoveFailed(ModelChangedMessageMoveFailed modelChangedMessageMoveFailed) {
        serverImplementationSocket.updateView(modelChangedMessageMoveFailed);
    }

    @Override
    public void visitModelChangedMessagePatternCard(ModelChangedMessagePatternCard modelChangedMessagePatternCard) {
        serverImplementationSocket.updateView(modelChangedMessagePatternCard);
    }

    @Override
    public void visitModelChangedMessagePrivateObjective(ModelChangedMessagePrivateObjective modelChangedMessagePrivateObjective) {
        serverImplementationSocket.updateView(modelChangedMessagePrivateObjective);
    }

    @Override
    public void visitModelChangedMessagePublicObjective(ModelChangedMessagePublicObjective modelChangedMessagePublicObjective) {
        serverImplementationSocket.updateView(modelChangedMessagePublicObjective);
    }

    @Override
    public void visitModelChangedMessageRefresh(ModelChangedMessageRefresh modelChangedMessageRefresh) {
        serverImplementationSocket.updateView(modelChangedMessageRefresh);
    }

    @Override
    public void visitModelChangedMessageRound(ModelChangedMessageRound modelChangedMessageRound) {
        serverImplementationSocket.updateView(modelChangedMessageRound);
    }

    @Override
    public void visitModelChangedMessageToolCard(ModelChangedMessageToolCard modelChangedMessageToolCard) {
        serverImplementationSocket.updateView(modelChangedMessageToolCard);
    }

    @Override
    public void visitModelChangedMessageTokensLeft(ModelChangedMessageTokensLeft modelChangedMessageTokensLeft) {
        serverImplementationSocket.updateView(modelChangedMessageTokensLeft);
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
    public void visitHeartbeatMessage(HeartbeatMessage heartbeatMessage) {

    }
}
