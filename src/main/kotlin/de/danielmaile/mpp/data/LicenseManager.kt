/*
 * This file is part of MPP.
 * Copyright (c) 2022 by it's authors. All rights reserved.
 * MPP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MPP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MPP.  If not, see <http://www.gnu.org/licenses/>.
 */

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