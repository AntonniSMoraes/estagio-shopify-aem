package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.models.UltimasMagazineModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
    resourceTypes = UltimasMagazineModel.RESOURCE_TYPE,
    selectors = "ultimas",
    extensions = "json"
)
public class UltimasMagazineServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        UltimasMagazineModel model = request.adaptTo(UltimasMagazineModel.class);
        if (model == null) {
            model = request.getResource().adaptTo(UltimasMagazineModel.class);
        }

        ObjectMapper mapper = new ObjectMapper();
        if (model != null && model.getArtigos() != null) {
            mapper.writeValue(response.getWriter(), model.getArtigos());
        } else {
            response.getWriter().write("[]");
        }
    }
}