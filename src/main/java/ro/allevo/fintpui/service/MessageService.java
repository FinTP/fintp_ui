package ro.allevo.fintpui.service;

import org.springframework.beans.factory.annotation.Autowired;

import ro.allevo.fintpui.controllers.MessageController;
import ro.allevo.fintpui.dao.MessagesDao;



public class MessageService {

	@Autowired
	MessagesDao messagesDao;
	
	public String getPayload(String correlid){
		String payload = messagesDao.getPayload(correlid);
		String path = getClass().getClassLoader()
				.getResource(MessageController.NESTED_TABLES_XSLT).getPath();
		String friendlyPayload = MessageController.applyXSLT(payload, path);
		return friendlyPayload;
	}

	public String getImage(String correlid) {
		String base64TiffImage = messagesDao.getImage(correlid);
		
		return base64TiffImage;
	}
}
