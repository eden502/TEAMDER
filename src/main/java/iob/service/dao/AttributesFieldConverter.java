package iob.service.dao;

import java.util.Map;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AttributesFieldConverter implements AttributeConverter<Map<String, Object>, String> {

	private ObjectMapper jackson;

	public AttributesFieldConverter() {
		this.jackson = new ObjectMapper();
	}

	@Override
	public String convertToDatabaseColumn(Map<String, Object> entityValue) {
		try {
			return this.jackson.writeValueAsString(entityValue);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Map<String, Object> convertToEntityAttribute(String json) {

		try {
			return this.jackson.readValue(json, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
