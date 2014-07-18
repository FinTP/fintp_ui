package ro.allevo.fintpui.dao;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONObject;

import ro.allevo.fintpui.model.MessageType;


public interface MessageTypesDao {

public ArrayList<MessageType> getMessageTypesInQueue(String queue);
}
