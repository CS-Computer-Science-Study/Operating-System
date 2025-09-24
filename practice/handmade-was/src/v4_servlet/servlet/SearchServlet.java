package v4_servlet.servlet;

import common.HttpRequest;
import common.HttpResponse;
import common.HttpServlet;
import common.repository.ModelInformation;
import common.repository.ModelRepository;
import common.util.HTMLCreatorUtil;

import java.util.List;

public class SearchServlet implements HttpServlet {

    private final ModelRepository modelRepository;

    public SearchServlet(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>검색 결과</h1>");

        String query = request.getQueryParameter("model");
        List<ModelInformation> models = modelRepository.findByKeyword(query);
        if (models.isEmpty()) {
            response.writeBody("== 검색 결과가 없습니다. ==<br>");
        }
        else {
            String table = HTMLCreatorUtil.createTable(models);
            response.writeBody(table);
        }

        response.writeBody("<a href='/'>Home</a>");
    }
}
