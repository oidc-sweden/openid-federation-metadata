/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */

package se.oidc.oidfed.md;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;

import java.util.*;

/**
 * Description
 */
public class MetadataUtils {

  public static final JsonMapper JSON_MAPPER = getOidcObjectMapper();

  public static final List<String> standardJwtClaims = List.of(
    "iss", "sub", "iat", "exp", "jti", "aud", "nbf"
  );

  public static JsonMapper getOidcObjectMapper() {
    return JsonMapper.builder()
        .changeDefaultPropertyInclusion(inclusion -> inclusion.withValueInclusion(JsonInclude.Include.NON_NULL))
        .enable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .build();
  }

  /**
   * Extract all claims that are not listed as standard JWT claims and are not present in the exclude data object
   *
   * @param dataObject Data object containing parameters that are not part of the extension set
   * @param payload payload of the JWT from which the extension claims are collected
   * @return JSON object map containing the extension claims
   */
  public static Map<String, Object> getExtensionProperties(final Map<String, Object> payload, Object dataObject) {
    Objects.requireNonNull(payload, "Null payload is not allowed");
    dataObject = Optional.ofNullable(dataObject).orElse(new HashMap<>());

    final Map<String, Object> extensionObjectMap = new HashMap<>();
    final List<String> payloadClaims = new ArrayList<>(payload.keySet());
    final Map<String, Object> dataObjectMap = JSON_MAPPER.readValue(JSON_MAPPER.writeValueAsString(dataObject),
      new TypeReference<>() {
      });
    for (final String claimName : payloadClaims) {
      if (standardJwtClaims.contains(claimName)) {
        continue;
      }
      if (dataObjectMap.containsKey(claimName)) {
        continue;
      }
      // This parameter in the payload is not a standard JWT claim and not part of the defined data object members. Add to extensions
      extensionObjectMap.put(claimName, payload.get(claimName));
    }
    return extensionObjectMap;
  }

}
