package br.com.crdd.common.pagination;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

@Data
public class SearchParams implements Serializable {

    private static final long serialVersionUID = -5321944326382035247L;


    private int page;
    private int size;
    private HashMap<String, String> sorting;

}