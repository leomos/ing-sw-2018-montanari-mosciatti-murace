package it.polimi.se2018.network;

import java.io.Serializable;

public enum ControllerActionEnum implements Serializable {
    GET_DIE_FROM_PATTERNCARD,
    GET_DIE_FROM_ROUNDTRACK,
    GET_INCREMENTED_VALUE,
    GET_POSITION_IN_PATTERNCARD,
    GET_VALUE_FOR_DIE
}
