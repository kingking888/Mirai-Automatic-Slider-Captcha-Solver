/*
 * Copyright 2021 溯洄w4123
 *
 *  此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 *  Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 *  https://www.gnu.org/licenses/agpl-3.0.en.html
 */

@file:JvmName("AutomaticLoginSolver")

package tech.kokona.mirai.slidercaptcha

import net.mamoe.mirai.utils.LoginSolver

@get:JvmName("getInstance")
public val AutomaticLoginSolver: LoginSolver by lazy<LoginSolver> {
    AutomaticLoginSolverImpl()
}
