package v5_final;

import common.HttpRequest;
import common.HttpResponse;
import common.HttpServlet;
import common.PageNotFoundException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationServlet implements HttpServlet {

    private final Map<String, ControllerAndMethod> pathMap = new HashMap<>();

    public AnnotationServlet(List<Object> controllers) {
        for (Object controller : controllers) {
            Arrays.stream(controller.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(GetMapping.class))
                    .forEach(method -> {
                        String path = method.getAnnotation(GetMapping.class).value();
                        validateDuplicationPath(method, path);
                        pathMap.put(path, new ControllerAndMethod(controller, method));
                    });
        }
    }

    private void validateDuplicationPath(Method method, String path) {
        if (pathMap.containsKey(path)) {
            ControllerAndMethod controllerAndMethod = pathMap.get(path);
            throw new IllegalStateException("경로 중복 등록, path = " + path +
                    ", method = " + method + ", 이미 등록된 메서드 = " + controllerAndMethod.method);
        }
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String path = request.getPath();

        ControllerAndMethod controllerAndMethod = pathMap.get(path);
        if (controllerAndMethod == null) {
            throw new PageNotFoundException("request = " + path);
        }
        controllerAndMethod.invoke(request, response);
    }

    private record ControllerAndMethod(Object controller, Method method) {

        public void invoke(HttpRequest request, HttpResponse response) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] args = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterTypes[i] == HttpRequest.class) {
                    args[i] = request;
                }
                else if (parameterTypes[i] == HttpResponse.class) {
                    args[i] = response;
                }
                else {
                    throw new IllegalArgumentException("Unsupported parameter type: " + parameterTypes[i]);
                }
            }

            try {
                method.invoke(controller, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
