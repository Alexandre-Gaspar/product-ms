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
            add("name", random("Smart TV 4K", "PlayStation 5", "Laptop Asus"));
            add("description", "Smart TV 4K com painel LED de 55 polegadas e suporte a HDR10. Ideal para uma experiência de entretenimento imersiva.");
            add("price", random(Double.class, range(100.00, 2999.99)));
        }});

        Fixture.of(ProductDTO.class).addTemplate("valid-update", new Rule(){{
            add("name", random("Teclado Mecânico", "Monitor Dell UltraSharp", "Fone de Ouvido Bluetooth"));
            add("description", "Teclado mecânico com switches RGB, design ergonômico e teclas anti-ghosting. Perfeito para gamers e digitadores.");
            add("price", random(Double.class, range(50.00, 1499.99)));
        }});

        Fixture.of(Product.class).addTemplate("valid", new Rule(){{
            add("id", random(Long.class, range(6L, 15L)));
            add("name", random("Cadeira Gamer", "Tablet Samsung", "Smartwatch Apple", "Impressora Canon", "Drone DJI"));
            add("description", "Cadeira gamer ergonômica com ajuste de altura e apoio para os pés. Ideal para longas sessões de jogos.");
            add("price", random(Double.class, range(199.99, 4999.99)));
            add("isAvailable", true);
        }});

    }
}
