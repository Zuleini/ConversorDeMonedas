import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaDeCambio {
    @SerializedName("base_code")
    private String baseCode;
    @SerializedName("target_code")
    private String targetCode;
    private double amount;
    private double result;


    public ConsultaDeCambio(String baseCode, String targetCode, double amount) {
        this.baseCode = baseCode.toUpperCase();
        this.targetCode = targetCode.toUpperCase();
        this.amount = amount;
    }

    public JsonObject convert() throws IOException, InterruptedException {
        String apiKey = "47ef3f20114d3ddc93d4962b";
        String urlStr = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + baseCode + "/" + targetCode + "/" + amount;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlStr))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return new Gson().fromJson(response.body(), JsonObject.class);
        } else {
            throw new IOException("Error al realizar la solicitud sel Servidor: " + response.statusCode());
        }
    }
}
