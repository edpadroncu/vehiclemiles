package com.prodigio.vehiclemiles.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Data
@Service
public class Helper {

    public boolean isString(Object object){
        return object instanceof String;
    }

    public boolean isInteger(Object object){
        return object instanceof Integer;
    }

    public Object mapToObject(Map<String, Object> map, Class pClass){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(map, pClass);
    }

    public ResponseEntity<Object> httpResponse(boolean success, Object data, HttpStatus httpStatus){
        return new ResponseEntity<>(new HashMap<Object, Object>(){{
            put("success", success);
            put("response", new HashMap<Object, Object>(){{
                put("data", data);
            }});
        }}, httpStatus);
    }
}
