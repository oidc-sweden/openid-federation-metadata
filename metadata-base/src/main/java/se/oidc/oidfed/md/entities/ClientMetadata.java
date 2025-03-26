/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import se.oidc.oidfed.md.lang.OidcLangJsonSerializer;

import java.util.List;
import java.util.Map;

/**
 * Oauth client metadata
 */
public class ClientMetadata extends BasicClientMetadata {

  @JsonIgnore
  @Getter
  private static final OidcLangJsonSerializer<ClientMetadata> jsonSerializer =
      new OidcLangJsonSerializer<>(ClientMetadata.class);

  /**
   * String containing a space-separated list of scope values (as described in Section 3.3 of OAuth 2.0 [RFC6749]) that
   * the client can use when requesting access tokens.  The semantics of values in this list are service specific.  If
   * omitted, an authorization server MAY register a client with a default set of scopes.
   */
  @JsonProperty("scope")
  @Getter
  @Setter
  protected String scope;

  /**
   * A unique identifier string (e.g., a Universally Unique Identifier (UUID)) assigned by the client developer or
   * software publisher used by registration endpoints to identify the client software to be dynamically registered.
   */
  @JsonProperty("software_id")
  @Getter
  @Setter
  protected String softwareId;

  /**
   * A version identifier string for the client software identified by "software_id".
   */
  @JsonProperty("software_version")
  @Getter
  @Setter
  protected String softwareVersion;

  /**
   * Constructor
   */
  public ClientMetadata() {
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
   * Create builder for this metadata object
   *
   * @return builder
   */
  public static ClientMetadataBuilder<ClientMetadata, ClientMetadataBuilder<?,?>> oauthClientMetadataBuilder() {
    return new ClientMetadataBuilder<>(new ClientMetadata());
  }

  /**
   * Client metadata builder class
   */
  public static class ClientMetadataBuilder<T extends ClientMetadata, B extends ClientMetadataBuilder<?, ?>> extends BasicClientMetadataBuilder<T, B> {

    /**
     * Constructor
     */
    public ClientMetadataBuilder(T metadata) {
      super(metadata);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected B getReturnedBuilderInstance() {
      if (this.getClass() == ClientMetadataBuilder.class) {
        return (B) this;
      }
      return getSubClassBuilderInstance();
    }

    /**
     * This method handles returning of a subclass builder instance. Any subclass of this builder class MUST @Override this method
     * and return its builder instance.
     *
     * @return builder subclass instance
     */
    public B getSubClassBuilderInstance() {
      throw new RuntimeException("This method must be overridden by subclass, returning the correct builder");
    }

    public B scope(final String scope) {
      this.metadata.scope = scope;
      return this.getReturnedBuilderInstance();
    }

    public B softwareId(final String softwareId) {
      this.metadata.softwareId = softwareId;
      return this.getReturnedBuilderInstance();
    }

    public B softwareVersion(final String softwareVersion) {
      this.metadata.softwareVersion = softwareVersion;
      return this.getReturnedBuilderInstance();
    }

    @Override
    public T build() {
      return this.metadata;
    }
  }

}
