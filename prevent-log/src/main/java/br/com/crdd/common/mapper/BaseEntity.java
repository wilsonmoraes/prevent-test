package br.com.crdd.common.mapper;

import java.io.Serializable;


public interface BaseEntity<S extends Serializable>{
	
	S getId();
	
}
