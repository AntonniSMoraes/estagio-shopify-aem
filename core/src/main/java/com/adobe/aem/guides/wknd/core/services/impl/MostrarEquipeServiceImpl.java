package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.config.MostrarEquipeConfig;
import com.adobe.aem.guides.wknd.core.services.MostrarEquipeService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = MostrarEquipeService.class, immediate = true)
@Designate(ocd = MostrarEquipeConfig.class)
public class MostrarEquipeServiceImpl implements MostrarEquipeService {

    private int maxMembros;

    @Activate
    @Modified
    protected void activate(MostrarEquipeConfig config) {
        this.maxMembros = config.maxMembros();
    }

    @Override
    public int getMaxMembros() {
        return this.maxMembros;
    }
}