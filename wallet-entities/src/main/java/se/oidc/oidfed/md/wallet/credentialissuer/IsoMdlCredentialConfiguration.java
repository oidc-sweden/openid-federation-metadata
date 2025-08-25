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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Credential Configuration for ISO Mdl verifiable credentials
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IsoMdlCredentialConfiguration extends AbstractCredentialConfiguration {

  /**
   *  REQUIRED. String identifying the Credential type, as defined in [ISO.18013-5].
   */
  @JsonProperty("doctype")
  private String doctype;

  /**
   * OPTIONAL. A list of claims offered in the Credential.
   */
  @JsonProperty("claims")
  private List<Claim> claims;

  /**
   * OPTIONAL. Array of namespaced claim name values that lists them in the order they should be displayed by the Wallet. The values MUST be
   * two strings separated by a tilde ('~') character, where the first string is a namespace value and a second is a claim name value.
   * For example, "org.iso.18013.5.1~given_name"
   */
  @JsonProperty("order")
  private List<String> order;

  public static IsoMdlCredentialConfigurationBuilder builder() {
    return new IsoMdlCredentialConfigurationBuilder();
  }

  public static class IsoMdlCredentialConfigurationBuilder
    extends AbstractCredentialConfigurationBuilder<IsoMdlCredentialConfiguration, IsoMdlCredentialConfigurationBuilder>{

    public IsoMdlCredentialConfigurationBuilder() {
      super(new IsoMdlCredentialConfiguration());
    }

    @Override protected IsoMdlCredentialConfigurationBuilder getBuilder() {
      return this;
    }

    /**
     * Sets the docType value in the IsoMdlCredentialConfiguration and returns the builder instance.
     *
     * @param docType the verifiable credential type value to set
     * @return the IsoMdlCredentialConfigurationBuilder instance
     */
    public IsoMdlCredentialConfigurationBuilder doctype (String docType) {
      this.credentialConfiguration.setDoctype(docType);
      return this;
    }

    /**
     * Adds a claim under a certain name space to the credential configuration.
     *
     * @param claim the Claim object to be added
     * @return the IsoMdlCredentialConfigurationBuilder instance
     */
    public IsoMdlCredentialConfigurationBuilder claim(Claim claim) {
      List<Claim> claimList = Optional.ofNullable(this.credentialConfiguration.getClaims()).orElse(new ArrayList<>());
      claimList.add(claim);
      this.credentialConfiguration.setClaims(claimList);
      return this;
    }

    /**
     * Sets the order of claims in display.
     *
     * @param order the list of claim names in the order they should be displayed
     * @return the IsoMdlCredentialConfigurationBuilder instance
     */
    public IsoMdlCredentialConfigurationBuilder order(List<String> order) {
      this.credentialConfiguration.setOrder(order);
      return this;
    }

  }
}
