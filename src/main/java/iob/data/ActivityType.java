package iob.data;

public enum ActivityType {
	JOIN_GROUP, EXIT_GROUP, ACCEPT_NEW_MEMBER, DELETE_GROUP, DELETE_MEMBER, GET_GROUPS_OF_USER, GET_USERS_IN_GROUP, OTHER;


	public static ActivityType getType(String type) {
		
		if(type.equalsIgnoreCase(ActivityType.JOIN_GROUP.name()))
			return ActivityType.JOIN_GROUP;
		
		if(type.equalsIgnoreCase(ActivityType.EXIT_GROUP.name()))
			return ActivityType.EXIT_GROUP;
		
		if(type.equalsIgnoreCase(ActivityType.ACCEPT_NEW_MEMBER.name()))
			return ActivityType.ACCEPT_NEW_MEMBER;
		
		if(type.equalsIgnoreCase(ActivityType.DELETE_GROUP.name()))
			return ActivityType.DELETE_GROUP;
		
		if(type.equalsIgnoreCase(ActivityType.DELETE_MEMBER.name()))
			return ActivityType.DELETE_MEMBER;
		
		if(type.equalsIgnoreCase(ActivityType.GET_GROUPS_OF_USER.name()))
			return ActivityType.GET_GROUPS_OF_USER;
		
		if(type.equalsIgnoreCase(ActivityType.GET_USERS_IN_GROUP.name()))
			return ActivityType.GET_USERS_IN_GROUP;
		
		return OTHER;
	}
	
}
