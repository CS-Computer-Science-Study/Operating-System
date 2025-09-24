package v5_final.controller;

import common.HttpRequest;
import common.HttpResponse;
import common.repository.ModelInformation;
import common.repository.ModelRepository;
import common.util.HTMLCreatorUtil;
import v5_final.GetMapping;

import java.util.List;

public class SearchController {

    private final ModelRepository modelRepository;

    public SearchController(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @GetMapping("/search")
    public void search(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>검색 결과</h1>");

        String query = request.getQueryParameter("model");
        List<ModelInformation> models = modelRepository.findByKeyword(query);
        if (models.isEmpty()) {
            response.writeBody("== 검색 결과가 없습니다. ==<br>");
            response.writeBody("<a href='/'>Home</a>");
            return;
        }

        String table = HTMLCreatorUtil.createTable(models);
        response.writeBody(table);
        response.writeBody("<a href='/'>Home</a>");
    }
}
