import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import domain.Shop;

import static org.assertj.core.api.Assertions.assertThat;

public class ShopJsonTest {

    ClassLoader classLoader = ShopJsonTest.class.getClassLoader();

    @Test
    void checkJsonTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("shop.json");
        ObjectMapper mapper = new ObjectMapper ();
        Shop shop = mapper.readValue(is, Shop.class);
        assertThat(shop.getName()).isEqualTo("Авокадо");
        assertThat(shop.getProducts()).contains("молоко","сметана","йогурт");
        assertThat(shop.getAddress()).isEqualTo("Москва, ул.Ленина, 15А");
        assertThat(shop.getRevenue()).isEqualTo(10000);
        assertThat(shop.getOwner().getOwnerName()).isEqualTo("Игорев Игорь Игоревич");
    }
}
