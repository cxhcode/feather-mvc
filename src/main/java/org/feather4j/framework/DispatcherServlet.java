package org.feather4j.framework;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.feather4j.framework.bean.Data;
import org.feather4j.framework.bean.Handler;
import org.feather4j.framework.bean.Param;
import org.feather4j.framework.bean.View;
import org.feather4j.framework.helper.BeanHelper;
import org.feather4j.framework.helper.ConfigHelper;
import org.feather4j.framework.helper.ControllerHelper;
import org.feather4j.framework.helper.HelperLoader;
import org.feather4j.framework.util.CodeUtil;
import org.feather4j.framework.util.JsonUtil;
import org.feather4j.framework.util.ReflectionUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by Jackie on 2015/11/26.
 * Email : chenxinhua@ishehui.com
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        HelperLoader.initHelper();
        ServletContext servletContext = config.getServletContext();
        ServletRegistration jsp = servletContext.getServletRegistration("jsp");
        jsp.addMapping(ConfigHelper.getAppJspPath() + "*");
        ServletRegistration aDefault = servletContext.getServletRegistration("default");
        aDefault.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod().toLowerCase();
        String pathInfo = req.getPathInfo();
        Handler handler = ControllerHelper.getHandler(method, pathInfo);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass();
            Method actionMethod = handler.getActionMethod();
            Object bean = BeanHelper.getBean(controllerClass);

            Enumeration<String> parameterNames = req.getParameterNames();
            Param param = new Param();
            if (parameterNames != null) {
                while (parameterNames.hasMoreElements()) {
                    String key = parameterNames.nextElement();
                    String value = req.getParameter(key);
                    param.put(key, value);
                }
            }
            ServletInputStream inputStream = req.getInputStream();
            String body = CodeUtil.decodeURL(IOUtils.toString(inputStream));
            if (StringUtils.isNotBlank(body)) {
                body = body.trim();
                String[] params = StringUtils.split(body, "&");
                if (ArrayUtils.isNotEmpty(params)) {
                    for (String s : params) {
                        String[] split = StringUtils.split(s, "=");
                        if (ArrayUtils.isNotEmpty(split) && split.length == 2) {
                            param.put(split[0], split[1]);
                        }
                    }
                }
            }

            Object result = ReflectionUtil.invokeMethod(bean, actionMethod, param);
            if (result instanceof View) {
                View view = (View) result;
                if (view.getPath().startsWith("/")) {
                    resp.sendRedirect(req.getContextPath() + view.getPath());
                } else {
                    Map<String, Object> paramMap = view.getModel();
                    if (MapUtils.isNotEmpty(paramMap)) {
                        paramMap.forEach((s, o) -> {
                            req.setAttribute(s, o);
                        });
                    }
                    req.getRequestDispatcher(ConfigHelper.getAppJspPath() + view.getPath() + ".jsp").forward(req, resp);
                }
            } else if (result instanceof Data) {
                Data data = (Data) result;
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                String s = JsonUtil.toJson(data);
                ServletOutputStream outputStream = resp.getOutputStream();
                outputStream.print(s);
                outputStream.flush();
                outputStream.close();
            }
        }
    }
}
