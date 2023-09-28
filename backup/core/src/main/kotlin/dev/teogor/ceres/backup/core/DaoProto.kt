/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.backup.core

import kotlinx.serialization.Serializable

@Serializable
open class DaoProto

// data class AccountProto
// @OptIn(ExperimentalSerializationApi::class)
// constructor(
//   @ProtoNumber(1) val id: Long,
//   @ProtoNumber(2) val email: String,
//   @ProtoNumber(3) val subscriptionId: Long,
//   @ProtoNumber(5) val subscriptionPurchase: Long,
//   @ProtoNumber(8) val subscriptionValid: Boolean = true,
//   @ProtoNumber(9) val password: String,
//   @ProtoNumber(10) val payment: String,
// ) : DaoProto() {
//   fun toAccount(
//     newSubscriptionId: Long,
//   ): Account {
//     return Account(
//       id = this.id,
//       email = this.email,
//       subscriptionId = newSubscriptionId,
//       subscriptionPurchase = this.subscriptionPurchase / 1000,
//       subscriptionValid = this.subscriptionValid,
//       password = this.password,
//       payment = this.payment,
//     )
//   }
//
//   companion object {
//     fun copyFrom(account: Account) = AccountProto(
//       id = account.id,
//       email = account.email,
//       subscriptionId = account.subscriptionId,
//       subscriptionPurchase = account.subscriptionPurchase,
//       subscriptionValid = account.subscriptionValid,
//       password = account.password,
//       payment = account.payment,
//     )
//   }
// }
