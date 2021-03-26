/*
 * Copyright 2021 溯洄w4123
 *
 *  此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 *  Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 *  https://www.gnu.org/licenses/agpl-3.0.en.html
 */

package tech.kokona.mirai.slidercaptcha

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import net.mamoe.mirai.Bot
import net.mamoe.mirai.network.WrongPasswordException
import net.mamoe.mirai.utils.LoginSolver
import net.mamoe.mirai.utils.SwingSolver
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AutomaticLoginSolverImpl : LoginSolver() {
    override val isSliderCaptchaSupported: Boolean get() = true

    init {

    }

    override suspend fun onSolvePicCaptcha(bot: Bot, data: ByteArray): String? {
        return Default?.onSolvePicCaptcha(bot, data)
    }


    override suspend fun onSolveUnsafeDeviceLoginVerify(bot: Bot, url: String): String? {
        return Default?.onSolveUnsafeDeviceLoginVerify(bot, url)
    }

    override suspend fun onSolveSliderCaptcha(bot: Bot, url: String): String? {
        System.out.println("[AutomaticLoginSolver] Captcha URL: $url")
        System.out.println("正在自动进行滑块验证，此步骤最多需要大概1分钟")
        System.out.println("请注意: 如果显示滑块验证成功但登录时却显示网络异常，可能是由于服务器进行了过多滑块验证而被限制。这种情况可以尝试稍后再试或采用其他滑块验证方式。")

        val client = HttpClient(OkHttp) {
            install(HttpTimeout) {
                // timeout config
                connectTimeoutMillis = 120000
                socketTimeoutMillis = 120000
                requestTimeoutMillis = 120000
            }
        }

        val response = withContext(Dispatchers.IO) {
            client.post<String?>("https://slider.kokona.tech/") {
                body = FormDataContent(Parameters.build {
                    append("url", url)
                })
            }
        }

        if (response != null) {
            System.out.println("[AutomaticLoginSolver] Captcha Response: $response")
            val respObj = Json.decodeFromString(JsonObject.serializer(), response)
            val ticket = respObj["ticket"] as? JsonPrimitive
            if (ticket != null) {
                System.out.println("自动滑块验证成功，正在提交 Ticket: ${ticket.content}")
                return ticket.content
            } else {
                throw (WrongPasswordException(response))
            }
        }
        return null
    }
}

