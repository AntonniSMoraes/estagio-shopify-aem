package com.adobe.aem.guides.wknd.core.models;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    resourceType = UltimasMagazineModel.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = "jackson", extensions = "json")
public class UltimasMagazineModel {

    public static final String RESOURCE_TYPE = "wknd/components/ultimas-do-magazine";

    @ValueMapValue
    @Default(intValues = 4)
    private int limite;

    @OSGiService
    private QueryBuilder queryBuilder;

    @SlingObject
    private ResourceResolver resourceResolver;

    private List<ArtigoItem> artigos;

    @PostConstruct
    protected void init() {
        artigos = new ArrayList<>();

        if (resourceResolver == null) {
            return;
        }

        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Session session = resourceResolver.adaptTo(Session.class);

        if (session != null && queryBuilder != null && pageManager != null) {
            Map<String, String> map = new HashMap<>();
            map.put("path", "/content/wknd/us/en/magazine");
            map.put("type", "cq:Page");
            map.put("orderby", "@jcr:content/jcr:created");
            map.put("orderby.sort", "desc");
            map.put("p.limit", String.valueOf(limite > 0 ? limite : 4));

            Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
            SearchResult result = query.getResult();

            for (Hit hit : result.getHits()) {
                try {
                    Page page = pageManager.getPage(hit.getPath());
                    if (page != null) {
                        artigos.add(new ArtigoItem(page));
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public List<ArtigoItem> getArtigos() {
        return artigos;
    }

    public int getLimite() {
        return limite;
    }
}