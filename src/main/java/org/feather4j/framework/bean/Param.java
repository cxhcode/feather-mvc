package org.feather4j.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jackie on 2015/11/26.
 * Email : chenxinhua@ishehui.com
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Param() {
        this.paramMap = new HashMap<>();
    }

    public Param put(String key, Object value) {
        if (paramMap == null)
            paramMap = new HashMap<>();
        paramMap.put(key, value);
        return this;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
