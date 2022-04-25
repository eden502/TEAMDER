package iob.logic;

import org.springframework.stereotype.Component;

@Component
public class IdConverter {

	public IdConverter(){
		
	}
	
	public String getUserDomainFromUserEntityId(String id) {	
		return id.split("@@",2)[0] ;
	}
	
	public String getUserEmailFromUserEntityId(String id) {
		return id.split("@@",2)[1] ;
		
	}
	
	public String getUserEntityIdFromDomainAndEmail(String domain, String email) {
		return domain+"@@"+email;
	}
	
	
	
	
	public String getEntityGeneralIdFromDomainAndId(String domain, String id) {
		
		return id+"@"+domain;
	}
	
	public String getIdFromEntityGeneralId(String generalId) {
		
		return generalId.split("@")[0];
	}

	public String getDomainFromEntityGeneralId(String generalId) {
		
		return generalId.split("@")[1];
	}
}
