package de.danielmaile.lama.license;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;

public record LicenseManager(JavaPlugin instance, String pluginName) {

    public LicenseManager(JavaPlugin instance, String pluginName) {
        this.instance = instance;
        this.pluginName = pluginName;

        Bukkit.getScheduler().runTaskTimerAsynchronously(instance, () -> {
            if (!validate()) {
                instance.getLogger().severe("License key validation failed! Please check your key in the config and restart the server to try again. Plugin gets disabled...");
                Bukkit.getScheduler().runTask(instance, () -> instance.getServer().getPluginManager().disablePlugin(instance));
            }
        }, 0L, 18000L); //run every 15min
    }

    private boolean validate() {
        try {
            String license = instance.getConfig().getString("license_key");
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(30000)
                    .setConnectionRequestTimeout(30000)
                    .setSocketTimeout(30000).build();
            CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            HttpGet httpGet = new HttpGet("http://lamahost.de:8000/validation");
            URI uri = new URIBuilder(httpGet.getURI())
                    .addParameter("product", pluginName)
                    .addParameter("license", license)
                    .build();
            httpGet.setURI(uri);

            CloseableHttpResponse response = client.execute(httpGet);
            String responseString = new BasicResponseHandler().handleResponse(response);
            client.close();

            return Boolean.parseBoolean(responseString);
        } catch (Exception e) {
            return false;
        }
    }
}
