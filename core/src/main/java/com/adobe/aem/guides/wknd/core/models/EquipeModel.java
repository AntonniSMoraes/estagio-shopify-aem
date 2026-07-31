package com.adobe.aem.guides.wknd.core.models;

import com.adobe.aem.guides.wknd.core.services.MostrarEquipeService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class EquipeModel {

    @ValueMapValue
    @Default(values = "Nossa Equipe")
    private String titulo;

    @ChildResource
    private List<Resource> membros;

    @OSGiService
    private MostrarEquipeService mostrarEquipeService;

    private List<Resource> membrosExibidos;

    @PostConstruct
    protected void init() {
        if (membros != null && !membros.isEmpty()) {
            int limite = (mostrarEquipeService != null) ? mostrarEquipeService.getMaxMembros() : membros.size();
            this.membrosExibidos = membros.stream()
                    .limit(limite)
                    .collect(Collectors.toList());
        } else {
            this.membrosExibidos = Collections.emptyList();
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Resource> getMembros() {
        return membrosExibidos;
    }

    public boolean isHasMembros() {
        return !membrosExibidos.isEmpty();
    }
}