package common.util;

import common.repository.ModelInformation;

import java.util.List;

public class HTMLCreatorUtil {

    private HTMLCreatorUtil() {
    }

    public static String createTable(List<ModelInformation> models) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'");
        sb.append("<tr>");
        sb.append("<th>제조사</th>");
        sb.append("<th>제품명</th>");
        sb.append("<th>가격</th>");
        sb.append("</tr>");

        for (ModelInformation model : models) {
            sb.append("<tr>");
            sb.append("<td style='padding: 10px'>");
            sb.append(model.store().name());
            sb.append("</td>");
            sb.append("<td style='padding: 10px'>");
            sb.append(model.name());
            sb.append("</td>");
            sb.append("<td style='padding: 10px'>");
            sb.append(model.price());
            sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }
}
