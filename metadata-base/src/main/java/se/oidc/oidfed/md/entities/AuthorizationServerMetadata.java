/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import se.oidc.oidfed.md.lang.OidcLangJsonSerializer;

import java.util.List;
import java.util.Map;

/**
 * Authorization server metadata
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizationServerMetadata extends BasicASMetadata {

  @JsonIgnore
  @Getter
  private static final OidcLangJsonSerializer<AuthorizationServerMetadata> jsonSerializer =
      new OidcLangJsonSerializer<>(AuthorizationServerMetadata.class);

  /*
   * Metadata parameters defined in this extension to BasicASMetadata
   */
  @JsonProperty("revocation_endpoint")
  @Getter
  @Setter
  private String revocationEndpoint;

  @JsonProperty("revocation_endpoint_auth_methods_supported")
  @Getter
  @Setter
  private List<String> revocationEndpointAuthMethodsSupported;

  @JsonProperty("revocation_endpoint_auth_signing_alg_values_supported")
  @Getter
  @Setter
  private List<String> revocationEndpointAuthSigningAlgValuesSupported;

  /**
   * Constructor
   */
  public AuthorizationServerMetadata() {
    this.addLanguageParametersTags(List.of());
  }

  /** {@inheritDoc} */
  @Override
  public String toJson(final boolean prettyPrinting) throws JsonProcessingException {
    return jsonSerializer.setPrettyPrinting(prettyPrinting).toJson(this);
  }

  /** {@inheritDoc} */
  @Override
  public Map<String, Object> toJsonObject() throws JsonProcessingException {
    return jsonSerializer.toJsonObject(this);
  }

  /**
   * Creates builder class
   *
   * @return builder
   */
  public static AuthorizationServerMetadataBuilder builder() {
    return new AuthorizationServerMetadataBuilder();
  }

  /**
   * Builder class
   */
  public static class AuthorizationServerMetadataBuilder
      extends BasicASMetadataBuilder<AuthorizationServerMetadata, AuthorizationServerMetadataBuilder> {

    /**
     * Private constructor
     */
    private AuthorizationServerMetadataBuilder() {
      super(new AuthorizationServerMetadata());
    }

    /** {@inheritDoc} */
    @Override
    protected AuthorizationServerMetadataBuilder getReturnedBuilderInstance() {
      return this;
    }

    public AuthorizationServerMetadataBuilder revocationEndpoint(final String revocationEndpoint) {
      this.metadata.revocationEndpoint = revocationEndpoint;
      return this;
    }

    public AuthorizationServerMetadataBuilder revocationEndpointAuthMethodsSupported(
        final List<String> revocationEndpointAuthMethodsSupported) {
      this.metadata.revocationEndpointAuthMethodsSupported = revocationEndpointAuthMethodsSupported;
      return this;
    }

    public AuthorizationServerMetadataBuilder revocationEndpointAuthSigningAlgValuesSupported(
        final List<String> revocationEndpointAuthSigningAlgValuesSupported) {
      this.metadata.revocationEndpointAuthSigningAlgValuesSupported = revocationEndpointAuthSigningAlgValuesSupported;
      return this;
    }

    /** {@inheritDoc} */
    @Override
    public AuthorizationServerMetadata build() {
      return this.metadata;
    }
  }

}
