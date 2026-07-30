package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class EquipeModel {

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String nome;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String cargo;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String urlImagem;


    public String getNome() {
        return nome;
    }
    
    public String getCargo() {
        return cargo;
    }

    public String getUrlImagem() {
        return urlImagem;
    }
}
