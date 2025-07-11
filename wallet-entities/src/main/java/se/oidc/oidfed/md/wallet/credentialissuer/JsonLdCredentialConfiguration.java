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
 * Credential Configuration for JSON LD verifiable credentials
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonLdCredentialConfiguration extends AbstractCredentialConfiguration {

  @JsonProperty("credential_definition")
  private JsonLdCredentialDefinition credentialDefinition;

  /**
   * OPTIONAL. Array of the claim name values that lists them in the order they should be displayed by the Wallet.
   */
  @JsonProperty("order")
  private List<String> order;

  public static JsonLdCredentialConfigurationBuilder builder() {
    return new JsonLdCredentialConfigurationBuilder();
  }

  public static class JsonLdCredentialConfigurationBuilder extends AbstractCredentialConfigurationBuilder<JsonLdCredentialConfiguration, JsonLdCredentialConfigurationBuilder>{

    public JsonLdCredentialConfigurationBuilder() {
      super(new JsonLdCredentialConfiguration());
    }

    /**
     * Sets the credential definition for the JsonLdCredentialConfigurationBuilder.
     *
     * @param credentialDefinition the JsonLdCredentialDefinition to be set
     * @return the JsonLdCredentialConfigurationBuilder instance with the updated credential definition
     */
    public JsonLdCredentialConfigurationBuilder credentialDefinition(JsonLdCredentialDefinition credentialDefinition) {
      this.credentialConfiguration.credentialDefinition = credentialDefinition;
      return this;
    }

    /**
     * Sets the order of claim name values that lists them in the order they should be displayed by the Wallet.
     *
     * @param order the list of claim name values to set the order
     * @return the JsonLdCredentialConfigurationBuilder instance
     */
    public JsonLdCredentialConfigurationBuilder order(List<String> order) {
      this.credentialConfiguration.order = order;
      return this;
    }

    @Override protected JsonLdCredentialConfigurationBuilder getBuilder() {
      return this;
    }
  }

  @Data
  @NoArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class JsonLdCredentialDefinition {

    /**
     * REQUIRED. Array as defined in [VC_DATA], Section 4.1.
     */
    @JsonProperty("@context")
    public List<String> context;

    /**
     * REQUIRED. Array designating the types a certain credential type supports, according to [VC_DATA], Section 4.3.
     */
    @JsonProperty("type")
    public List<String> type;

    /**
     * OPTIONAL. A list of claims offered in the Credential.
     */
    @JsonProperty("claims")
    public List<Claim> claims;

    /**
     * Creates a new instance of JsonLdCredentialDefinitionBuilder.
     *
     * @return a new JsonLdCredentialDefinitionBuilder instance
     */
    public static JsonLdCredentialDefinitionBuilder builder() {
      return new JsonLdCredentialDefinitionBuilder();
    }

    /**
     * A builder class for creating JsonLdCredentialDefinition instances.
     */
    public static class JsonLdCredentialDefinitionBuilder {

      private final JsonLdCredentialDefinition credentialDefinition;
      private JsonLdCredentialDefinitionBuilder() {
        this.credentialDefinition = new JsonLdCredentialDefinition();
      }

      /**
       * Sets the context for the JsonLdCredentialDefinition.
       *
       * @param context a list of context strings to set
       * @return the JsonLdCredentialDefinitionBuilder instance with the updated context
       */
      public JsonLdCredentialDefinitionBuilder context(List<String> context) {
        this.credentialDefinition.context = context;
        return this;
      }

      /**
       * Sets the types of the JsonLdCredentialDefinition.
       *
       * @param type a list of strings representing the types to set
       * @return the JsonLdCredentialDefinitionBuilder instance with the updated types
       */
      public JsonLdCredentialDefinitionBuilder type(List<String> type) {
        this.credentialDefinition.type = type;
        return this;
      }

      /**
       * Sets the claims field of the JsonLdCredentialDefinitionBuilder instance.
       * If this function is used after setting values with "claim()", then these values will be lost.
       *
       * @param claims a list representing the credential subject information
       * @return the JsonLdCredentialDefinitionBuilder instance with the updated claims field
       */
      public JsonLdCredentialDefinitionBuilder claims(List<Claim> claims) {
        this.credentialDefinition.claims = claims;
        return this;
      }

      /**
       * Sets a claim for the credential subject.
       *
       * @param claim the claim object containing information about the claim
       * @return the JsonLdCredentialDefinitionBuilder instance with the updated credentialSubject field
       */
      public JsonLdCredentialDefinitionBuilder claim(Claim claim) {
        List<Claim> claimList = Optional.ofNullable(this.credentialDefinition.getClaims()).orElse(new ArrayList<>());
        claimList.add(claim);
        this.credentialDefinition.setClaims(claimList);
        return this;
      }

      /**
       * Retrieves the built JsonLdCredentialDefinition instance.
       *
       * @return the built JsonLdCredentialDefinition instance
       */
      public JsonLdCredentialDefinition build() {
        return this.credentialDefinition;
      }

    }


  }

}
