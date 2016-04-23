package org.feather4j.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Jackie on 2015/11/25.
 * Email : chenxinhua@ishehui.com
 */
public final class PropsUtil {
    private static Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    public static String getString(Properties properties, String name) {
        Object o = properties.get(name);
        if (o != null) return StringUtils.trim(o.toString());
        return null;
    }

    public static String getString(Properties properties, String name, String value) {
        String string = getString(properties, name);
        return StringUtils.isNotBlank(string) ? string : value;
    }

    public static Integer getInteger(Properties properties, String name) {
        return Integer.parseInt(getString(properties, name));
    }

    public static Boolean getBoolean(Properties properties, String name) {
        return Boolean.parseBoolean(getString(properties, name));
    }

    public static Properties loadProps(String fileName) {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            Properties p = new Properties();
            p.load(in);
            return p;
        } catch (IOException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }
}
