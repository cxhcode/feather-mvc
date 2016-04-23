package org.feather4j.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jackie on 2015/11/26.
 * Email : chenxinhua@ishehui.com
 */
public class View {

    private String path;
    private Map<String, Object> model;


    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public View(String path) {
        this.path = path;
    }

    public View addModel(String key, Object value) {
        if (model == null) {
            model = new HashMap<>();
        }
        model.put(key, value);
        return this;
    }
}
