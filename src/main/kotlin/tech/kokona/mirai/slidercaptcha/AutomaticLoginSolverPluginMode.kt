/*
 * Copyright 2021 溯洄w4123
 *
 *  此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 *  Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 *  https://www.gnu.org/licenses/agpl-3.0.en.html
 */

package tech.kokona.mirai.slidercaptcha

import net.mamoe.mirai.console.extension.PluginComponentStorage
import net.mamoe.mirai.console.extensions.BotConfigurationAlterer
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.version
import net.mamoe.mirai.utils.BotConfiguration
import java.util.*

internal class AutomaticLoginSolverPluginMode : KotlinPlugin(
    JvmPluginDescriptionBuilder(
        id = "tech.kokona.mirai-automatic-slider-captcha-solver",
        version = "0.2"
    ).build()
), BotConfigurationAlterer {
    override fun PluginComponentStorage.onLoad() {
        logger.info("正在加载 Mirai Automatic Slider Captcha Solver v$version By 溯洄w4123")
        try {
            AutomaticLoginSolver
        } catch (err: Throwable) {
            logger.warning("加载 Mirai Automatic Slider Captcha Solver 时遇到错误", err)
            return
        }
        contributeBotConfigurationAlterer(this@AutomaticLoginSolverPluginMode)
    }

    override fun alterConfiguration(botId: Long, configuration: BotConfiguration): BotConfiguration {
        configuration.loginSolver = AutomaticLoginSolver
        return configuration
    }
}
