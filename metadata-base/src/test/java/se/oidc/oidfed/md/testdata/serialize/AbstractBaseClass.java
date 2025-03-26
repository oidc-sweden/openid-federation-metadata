/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.testdata.serialize;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import se.oidc.oidfed.md.lang.LanguageObject;
import se.oidc.oidfed.md.lang.LanguageTaggedJson;

import java.util.List;
import java.util.Map;

/**
 * Test JSON serialization of abstract data
 */
public class AbstractBaseClass implements LanguageTaggedJson {

  public AbstractBaseClass() {
    this.organizationName = LanguageObject.builder(String.class)
        .defaultValue("Default value")
        .langValue("sv", "Svenska")
        .langValue("en", "English")
        .build();
    this.signedJwksUri = "https://example.com/jwks";
  }

  @Override
  public List<String> getLanguageTaggedParameters() {
    return List.of("organization_name");
  }

  @JsonProperty("organization_name")
  @Getter
  protected LanguageObject<String> organizationName;

  @JsonProperty("signed_jwks_uri")
  @Getter
  protected String signedJwksUri;

  @JsonProperty("jwks")
  protected Map<String, Object> jwkSet;

}
