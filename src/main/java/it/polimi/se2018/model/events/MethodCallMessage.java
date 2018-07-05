package it.polimi.se2018.model.events;

import it.polimi.se2018.network.visitor.MessageVisitorInterface;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MethodCallMessage implements Serializable, Message {

    private static final long serialVersionUID = 3000L;

    private String methodName;

    private Map<String, Object> arguments;

    private Object returnValue;

    public MethodCallMessage(String methodName) {
        this.methodName = methodName;
        this.arguments = new HashMap<>();
    }

    public MethodCallMessage(String methodName, Object returnValue) {
        this.methodName = methodName;
        this.returnValue = returnValue;
    }

    public void addArgument(String argumentName, Object argument) {
        arguments.put(argumentName, argument);
    }

    public Object getArgument(String argumentName) {
        return arguments.get(argumentName);
    }

    public String getMethodName() {
        return methodName;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    @Override
    public void accept(MessageVisitorInterface messageVisitorInterface) {
        messageVisitorInterface.visitMethodCallMessage(this);
    }
}
