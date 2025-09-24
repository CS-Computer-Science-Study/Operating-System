package v5_final.controller;

import common.HttpRequest;
import common.HttpResponse;
import common.repository.ModelInformation;
import common.repository.ModelRepository;
import common.repository.Store;
import common.util.HTMLCreatorUtil;
import v5_final.GetMapping;

import java.util.List;

public class StoreController {

    private final ModelRepository modelRepository;

    public StoreController(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @GetMapping("/")
    public void home(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>Welcome Store!</h1>");
        response.writeBody("<form method='GET' action='/search'>");
        response.writeBody("<input type='text' placeholder = '제품명 입력' name='model'>");
        response.writeBody("<input type='submit' value='검색하기'>");
        response.writeBody("</form>");
        response.writeBody("<h3><a href='/apple'>Apple</a></h3>");
        response.writeBody("<h3><a href='/samsung'>Samsung</a></h3>");
    }

    @GetMapping("/apple")
    public void apple(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>Apple Store</h1>");

        List<ModelInformation> models = modelRepository.findByStore(Store.APPLE);
        String table = HTMLCreatorUtil.createTable(models);
        response.writeBody(table);
        response.writeBody("<a href='/'>Home</a>");
    }

    @GetMapping("/samsung")
    public void samsung(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>Samsung Store</h1>");

        List<ModelInformation> models = modelRepository.findByStore(Store.SAMSUNG);
        String table = HTMLCreatorUtil.createTable(models);
        response.writeBody(table);
        response.writeBody("<a href='/'>Home</a>");
    }

}
