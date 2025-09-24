package v5_final.controller;

import common.HttpRequest;
import common.HttpResponse;
import v5_final.GetMapping;

public class RedirectController {

    @GetMapping("/redirect")
    public void search(HttpRequest request, HttpResponse response) {
        response.setStatusCode(302);
        response.addHeader("Location", "/apple");
    }
}
