package com.kotlin.study.bean;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created on 2018/1/6.
 * Description:
 *
 */
public class PreviewData implements Serializable {

    public ArrayList<String> templates;
    public JsonObject data;
}
