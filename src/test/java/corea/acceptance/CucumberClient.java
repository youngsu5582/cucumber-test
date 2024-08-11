package corea.acceptance;

import io.cucumber.spring.ScenarioScope;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.test.context.TestComponent;

import java.util.HashMap;
import java.util.Map;

@TestComponent
@ScenarioScope
@NoArgsConstructor
@Getter
@Setter
public class CucumberClient {

    private Map<String, Object> dataStorage = new HashMap<>();

    private Response response;

    private String token;

    public void addData(final String key, final Object value) {
        dataStorage.put(key, value);
    }

    public <T> T getData(final String key, final Class<T> clazz) {
        final Object data = dataStorage.get(key);

        if (data == null) {
            throw new NullPointerException(String.format("%s 에 대한 값이 없습니다.", key));
        }

        if (!clazz.isInstance(data)) {
            throw new ClassCastException(String.format("%s 와 %s는 타입이 다릅니다.", clazz, data));
        }

        return clazz.cast(data);
    }
}
