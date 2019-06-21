package dev.hwiveloper.app.woomin.common;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;

import dev.hwiveloper.app.woomin.domain.common.Edu;
import dev.hwiveloper.app.woomin.domain.common.EduPK;

public class CompositeKeyConverter implements BackendIdConverter {
	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) {
			return null;
		}
		
		if (entityType.isAssignableFrom(Edu.class)) {	
			String[] idParts = id.split("-");
			String eduIdPart = getEduIdFromIdParts(idParts);
			return new Edu(Integer.parseInt(idParts[0]), eduIdPart);
		} else {
			return id;
		}
		
	}
	
	private String getEduIdFromIdParts(String[] idParts) {
        int idx = 0;
        String eduIdPart = "";
        for(String idPart : idParts){
            if( idx == 1){
                eduIdPart+=idPart;
            }else if (idx > 1){
                eduIdPart+="-"+idPart;
            }
            idx++;
        }
        return eduIdPart;
    }
	
	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		if (id instanceof EduPK) {
			EduPK eduPK = (EduPK) id;
			return String.format("%s-%s", eduPK.getSgId(), eduPK.getEduId());
		} else {
			if (id instanceof Integer) {
				return String.valueOf(id);
			} else {
				return (String) id;
			}
		}
	}
	
	@Override
	public boolean supports(Class<?> delimiter) {
		return true;
	}

}
