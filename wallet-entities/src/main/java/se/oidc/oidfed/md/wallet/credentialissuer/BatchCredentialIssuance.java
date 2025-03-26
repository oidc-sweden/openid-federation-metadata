/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.wallet.credentialissuer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Description
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchCredentialIssuance {

  @JsonProperty("batch_size")
  private int batchSize;

  public BatchCredentialIssuance(int batchSize) {
    this.batchSize = batchSize;
  }
}
