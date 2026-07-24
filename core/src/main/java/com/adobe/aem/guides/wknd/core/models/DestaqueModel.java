package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class DestaqueModel {

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String titulo;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String texto;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String textoBotao;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String urlBotao;

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }

    public String getTextoBotao() {
        return textoBotao;
    }

    public String getUrlBotao() {
        return urlBotao;
    }

    public boolean isMostrarBotao() {
        return urlBotao != null && !urlBotao.trim().isEmpty();
    }
}