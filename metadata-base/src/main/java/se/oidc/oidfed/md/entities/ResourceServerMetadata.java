/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import se.oidc.oidfed.md.lang.OidcLangJsonSerializer;

import java.util.List;
import java.util.Map;

/**
 * Resource server metadata
 */
public class ResourceServerMetadata extends AbstractOidcFedMetadata {

  @JsonIgnore
  @Getter
  private static final OidcLangJsonSerializer<ResourceServerMetadata> jsonSerializer =
      new OidcLangJsonSerializer<>(ResourceServerMetadata.class);

  /**
   * Constructor
   */
  public ResourceServerMetadata() {
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
   * Create builder for Resource Server metadata
   *
   * @return the builder
   */
  public static ResourceServerMetadataBuilder builder() {
    return new ResourceServerMetadataBuilder();
  }

  /**
   * Builder class for resource server metadata
   */
  public static class ResourceServerMetadataBuilder
      extends AbstractOidcFedMetadataBuilder<ResourceServerMetadata, ResourceServerMetadataBuilder> {

    /**
     * Private constructor
     */
    private ResourceServerMetadataBuilder() {
      super(new ResourceServerMetadata());
    }

    /** {@inheritDoc} */
    @Override
    protected ResourceServerMetadataBuilder getReturnedBuilderInstance() {
      return this;
    }

    /** {@inheritDoc} */
    @Override
    public ResourceServerMetadata build() {
      return this.metadata;
    }
  }
}
