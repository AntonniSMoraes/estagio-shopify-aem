package com.adobe.aem.guides.wknd.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
    name = "WKND - Configuração de Exibição da Equipe",
    description = "Define regras globais para exibição de membros da equipe"
)
public @interface MostrarEquipeConfig {

    @AttributeDefinition(
        name = "Máximo de Membros",
        description = "Quantidade máxima de membros exibidos no componente Equipe"
    )
    int maxMembros() default 3;
}