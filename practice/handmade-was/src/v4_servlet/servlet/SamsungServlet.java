package v4_servlet.servlet;

import common.HttpRequest;
import common.HttpResponse;
import common.HttpServlet;
import common.repository.ModelInformation;
import common.repository.ModelRepository;
import common.repository.Store;
import common.util.HTMLCreatorUtil;

import java.util.List;

public class SamsungServlet implements HttpServlet {

    private final ModelRepository modelRepository;

    public SamsungServlet(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>Samsung Store</h1>");

        List<ModelInformation> models = modelRepository.findByStore(Store.SAMSUNG);
        String table = HTMLCreatorUtil.createTable(models);
        response.writeBody(table);
        response.writeBody("<a href='/'>Home</a>");
    }
}
