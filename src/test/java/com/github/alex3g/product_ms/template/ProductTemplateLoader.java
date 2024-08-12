package com.github.alex3g.product_ms.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.github.alex3g.product_ms.dto.ProductDTO;

public class ProductTemplateLoader implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(ProductDTO.class).addTemplate("valid", new Rule(){{
            add("name", random("Monitor", "iPhone 15", "Mac Book Pro"));
            add("description", "Monitor da marca DELL de 30 polegadas. Possui uma tela touch screen, que permite usar tocando na tela.");
            add("price", random(Double.class, range(0.01, 199087.89)));
        }});
    }
}
