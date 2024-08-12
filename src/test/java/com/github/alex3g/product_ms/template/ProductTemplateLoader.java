package com.github.alex3g.product_ms.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.github.alex3g.product_ms.dto.ProductDTO;
import com.github.alex3g.product_ms.model.Product;

public class ProductTemplateLoader implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(ProductDTO.class).addTemplate("valid", new Rule(){{
            add("name", random("Monitor", "iPhone 15", "Mac Book Pro"));
            add("description", "Monitor da marca DELL de 30 polegadas. Possui uma tela touch screen, que permite usar tocando na tela.");
            add("price", random(Double.class, range(0.01, 199087.89)));
        }});
        Fixture.of(Product.class).addTemplate("valid", new Rule(){{
            add("id", random(Long.class, range(1L, 5L)));
            add("name", random("Monitor", "iPhone 15", "Mac Book Pro", "Computador HP", "AirPods 11s"));
            add("description", "Monitor da marca DELL de 30 polegadas. Possui uma tela touch screen, que permite usar tocando na tela.");
            add("price", random(Double.class, range(0.01, 199087.89)));
            add("isAvailable", true);
        }});
    }
}
