package de.danielmaile.mpp.data

import de.danielmaile.mpp.inst
import org.apache.http.NameValuePair
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.bukkit.Bukkit
import java.nio.charset.StandardCharsets
import java.util.ArrayList

object LicenseManager {

    fun validateLicense(): Boolean {
        return try {
            val config = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000).build()
            val client = HttpClientBuilder.create().setDefaultRequestConfig(config).build()
            val httpPost = HttpPost("http://sugmadezz.online:8000/validation")
            val urlParameters = getRequestInformation()
            httpPost.entity = UrlEncodedFormEntity(urlParameters, StandardCharsets.UTF_8)
            val response = client.execute(httpPost)
            val responseString = BasicResponseHandler().handleResponse(response)
            client.close()
            responseString.toBoolean()
        } catch (e: Exception) {
            false
        }
    }

    private fun getRequestInformation(): List<NameValuePair> {
        val urlParameters: MutableList<NameValuePair> = ArrayList()
        urlParameters.add(BasicNameValuePair("productName", "MPP"))
        urlParameters.add(BasicNameValuePair("licenseKey", inst().config.getString("license_key") ?: ""))
        urlParameters.add(BasicNameValuePair("serverVersion", Bukkit.getVersion()))
        urlParameters.add(BasicNameValuePair("onlinePlayers", Bukkit.getOnlinePlayers().size.toString()))
        return urlParameters
    }
}