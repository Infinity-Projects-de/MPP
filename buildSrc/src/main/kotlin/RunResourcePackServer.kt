/*
 * This file is part of MPP.
 * Copyright (c) 2023 by it's authors. All rights reserved.
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

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import spark.Spark
import java.io.File
import java.net.InetAddress

open class RunResourcePackServer : DefaultTask() {

    @Input
    var port: Int = 8080

    @InputFile
    var resourcePackZip: File = File(project.projectDir, "ResourcePack.zip")

    @TaskAction
    fun startServer() {

        val ipAddress = InetAddress.getLocalHost().hostAddress

        Spark.port(port)
        Spark.get("/${resourcePackZip.name}") { _, res ->
            res.header("Content-Disposition", "attachment; filename=${resourcePackZip.name}")
            res.header("Content-Type", "application/zip")
            res.header("Content-Length", resourcePackZip.length().toString())
            res.raw().outputStream.use { outputStream ->
                resourcePackZip.inputStream().use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }

        println("Local Resource Pack Hosting Server started at http://$ipAddress:$port")
        println("Resource Pack ZIP file available at http://$ipAddress:$port/${resourcePackZip.name}")

        // keep server running
        while (true) {
            Thread.sleep(1000)
        }
    }
}