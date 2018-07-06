package it.polimi.se2018.network.visitor;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.network.client.ServerImplementationSocket;

import java.util.ArrayList;

public class MessageVisitorImplementationServer implements MessageVisitorInterface {

    private ServerImplementationSocket serverImplementationSocket;

    public MessageVisitorImplementationServer(ServerImplementationSocket serverImplementationSocket) {
        this.serverImplementationSocket = serverImplementationSocket;
    }

    @Override
    public void visitMethodCallMessage(MethodCallMessage methodCallMessage) {
        Runnable task;
        Thread thread;
        switch (methodCallMessage.getMethodName()) {
            case "getDieFromPatternCard":
                task = () -> {
                    serverImplementationSocket.writeMessage(new MethodCallMessage(methodCallMessage.getMethodName(),
                            serverImplementationSocket.getViewClient().getDieFromPatternCard()));
                };
                thread = new Thread(task);
                thread.start();
                break;
            case "getDieFromRoundTrack":
                task = () -> {
                    serverImplementationSocket.writeMessage(new MethodCallMessage(methodCallMessage.getMethodName(),
                            serverImplementationSocket.getViewClient().getDieFromRoundTrack()));
                };
                thread = new Thread(task);
                thread.start();
                break;
            case "getIncrementedValue":
                task = () -> {
                    serverImplementationSocket.writeMessage(new MethodCallMessage(methodCallMessage.getMethodName(),
                            serverImplementationSocket.getViewClient().getIncrementedValue()));
                };
                thread = new Thread(task);
                thread.start();
                break;
            case "getPositionInPatternCard":
                task = () -> {
                    serverImplementationSocket.writeMessage(new MethodCallMessage(methodCallMessage.getMethodName(),
                            serverImplementationSocket.getViewClient().getPositionInPatternCard()));
                };
                thread = new Thread(task);
                thread.start();
                break;
            case "getValueForDie":
                task = () -> {
                    serverImplementationSocket.writeMessage(new MethodCallMessage(methodCallMessage.getMethodName(),
                            serverImplementationSocket.getViewClient().getValueForDie()));
                };
                thread = new Thread(task);
                thread.start();
                break;
            case "askForPatternCard":
                task = () -> {
                    serverImplementationSocket.writeMessage(new MethodCallMessage(methodCallMessage.getMethodName(),
                            serverImplementationSocket.getViewClient().askForPatternCard()));
                };
                thread = new Thread(task);
                thread.start();
                break;
            case "getDieFromDiceArena":
                task = () -> {
                    serverImplementationSocket.writeMessage(new MethodCallMessage(methodCallMessage.getMethodName(),
                            serverImplementationSocket.getViewClient().getDieFromDiceArena()));
                };
                thread = new Thread(task);
                thread.start();
                break;
            case "getDoublePositionInPatternCard":
                task = () -> {
                    serverImplementationSocket.writeMessage(new MethodCallMessage(methodCallMessage.getMethodName(),
                            serverImplementationSocket.getViewClient().getDoublePositionInPatternCard()));
                };
                thread = new Thread(task);
                thread.start();
                break;
            case "getSinglePositionInPatternCard":
                task = () -> {
                    serverImplementationSocket.writeMessage(new MethodCallMessage(methodCallMessage.getMethodName(),
                            serverImplementationSocket.getViewClient().getSinglePositionInPatternCard(
                                    (ArrayList<Integer>) methodCallMessage.getArgument("listOfAvailablePositions"))));
                };
                thread = new Thread(task);
                thread.start();
                break;
            case "block":
                task = () -> {
                    serverImplementationSocket.writeMessage(new MethodCallMessage(methodCallMessage.getMethodName(),
                            serverImplementationSocket.getViewClient().block()));
                };
                thread = new Thread(task);
                thread.start();
                break;
            case "free":
                task = () -> {
                    serverImplementationSocket.writeMessage(new MethodCallMessage(methodCallMessage.getMethodName(),
                            serverImplementationSocket.getViewClient().free()));
                };
                thread = new Thread(task);
                thread.start();
                break;
            default:
                break;
        }

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
    public void visitModelChangedMessageChangeGamePhase(ModelChangedMessageChangeGamePhase modelChangedMessageChangeGamePhase) {
        serverImplementationSocket.updateView(modelChangedMessageChangeGamePhase);
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
    public void visitModelChangedMessageNewEvent(ModelChangedMessageNewEvent modelChangedMessageNewEvent) {
        serverImplementationSocket.updateView(modelChangedMessageNewEvent);
    }

    @Override
    public void visitModelChangedMessagePlayerAFK(ModelChangedMessagePlayerAFK modelChangedMessagePlayerAFK) {
        serverImplementationSocket.updateView(modelChangedMessagePlayerAFK);
    }

    @Override
    public void visitModelChangedMessageOnlyOnePlayerLeft(ModelChangedMessageOnlyOnePlayerLeft modelChangedMessageOnlyOnePlayerLeft) {
        serverImplementationSocket.updateView(modelChangedMessageOnlyOnePlayerLeft);
    }

    @Override
    public void visitModelChangedMessageRoomUpdate(ModelChangedMessageRoomUpdate modelChangedMessageRoomUpdate) {
        serverImplementationSocket.updateView(modelChangedMessageRoomUpdate);
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
    public void visitPlayerMessageNotAFK(PlayerMessageNotAFK playerMessageNotAFK) {

    }

    @Override
    public void visitPlayerMessageToolCard(PlayerMessageToolCard playerMessageToolCard) {

    }

    @Override
    public void visitHeartbeatMessage(HeartbeatMessage heartbeatMessage) {

    }
}
