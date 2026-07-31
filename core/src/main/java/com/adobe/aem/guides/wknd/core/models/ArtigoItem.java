package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import com.day.cq.wcm.api.Page;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArtigoItem {

    private String titulo;
    private String path;
    private String imagem;

    public ArtigoItem(Page page) {
        if (page != null) {
            this.titulo = page.getTitle() != null ? page.getTitle() : page.getName();
            this.path = page.getPath() + ".html";
            Resource imageResource = page.getContentResource("image");
            if (imageResource != null) {
                this.imagem = imageResource.getValueMap().get("fileReference", String.class);
            }
        }
    }

    public String getTitulo() { return titulo; }
    public String getPath() { return path; }
    public String getImagem() { return imagem; }
}