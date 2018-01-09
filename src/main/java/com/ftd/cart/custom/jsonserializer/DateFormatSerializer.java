package com.ftd.cart.custom.jsonserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import static com.ftd.cart.constants.AddToCartConstants.DATE_FORMAT;

public class DateFormatSerializer extends JsonSerializer<String>{

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(DateFormatSerializer.class);
	
	@Override
	public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		SimpleDateFormat sdfSource = new SimpleDateFormat(DATE_FORMAT);

		//parse the string into Date object
		try {
			Date date = sdfSource.parse(value);
			jsonGenerator.writeString(date.toInstant().toString());
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
		}
		
	}
	
}
