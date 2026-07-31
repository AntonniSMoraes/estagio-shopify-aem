package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
    adaptables = Resource.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class MembroModel {

    @ValueMapValue
    private String nome;

    @ValueMapValue
    private String cargo;

    @ValueMapValue
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
