/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.wallet.credentialissuer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * Credential Configuration for IETF SD-JWT verifiable credentials
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SdJwtCredentialConfiguration extends AbstractCredentialConfiguration {

  /**
   * REQUIRED. String designating the type of a Credential, as defined in [I-D.ietf-oauth-sd-jwt-vc].
   */
  @JsonProperty("vct")
  private String vct;

  /**
   * OPTIONAL. A list of claims offered in the Credential.
   */
  @JsonProperty("claims")
  private List<Claim> claims;

  /**
   * OPTIONAL. An array of the claim name values that lists them in the order they should be displayed by the Wallet.
   */
  @JsonProperty("order")
  private List<String> order;

  public static SdJwtCredentialConfigurationBuilder builder() {
    return new SdJwtCredentialConfigurationBuilder();
  }

  public static class SdJwtCredentialConfigurationBuilder
    extends AbstractCredentialConfigurationBuilder<SdJwtCredentialConfiguration, SdJwtCredentialConfigurationBuilder>{

    public SdJwtCredentialConfigurationBuilder() {
      super(new SdJwtCredentialConfiguration());
    }

    @Override protected SdJwtCredentialConfigurationBuilder getBuilder() {
      return this;
    }

    /**
     * Sets the vct value in the SdJwtCredentialConfiguration and returns the builder instance.
     *
     * @param vct the verifiable credential type value to set
     * @return the SdJwtCredentialConfigurationBuilder instance
     */
    public SdJwtCredentialConfigurationBuilder vct (String vct) {
      this.credentialConfiguration.setVct(vct);
      return this;
    }

    /**
     * Sets the claims for the SdJwtCredentialConfiguration and returns the SdJwtCredentialConfigurationBuilder instance.
     *
     * @param claims a list of claims
     * @return the SdJwtCredentialConfigurationBuilder instance
     */
    public SdJwtCredentialConfigurationBuilder claims(List<Claim> claims) {
      this.credentialConfiguration.setClaims(claims);
      return this;
    }

    /**
     * Adds a claim to the credential configuration.
     *
     * @param claim the Claim object to be added
     * @return the SdJwtCredentialConfigurationBuilder instance
     */
    public SdJwtCredentialConfigurationBuilder claim(Claim claim) {
      List<Claim> claimList = Optional.ofNullable(this.credentialConfiguration.getClaims()).orElse(new ArrayList<>());
      claimList.add(claim);
      this.credentialConfiguration.setClaims(claimList);
      return this;
    }

    /**
     * Sets the order of claims in display.
     *
     * @param order the list of claim names in the order they should be displayed
     * @return the SdJwtCredentialConfigurationBuilder instance
     */
    public SdJwtCredentialConfigurationBuilder order(List<String> order) {
      this.credentialConfiguration.setOrder(order);
      return this;
    }

  }
}
