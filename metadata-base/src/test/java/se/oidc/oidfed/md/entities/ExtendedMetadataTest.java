/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.entities;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import se.oidc.oidfed.md.MetadataUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for extended metadata
 */
@Slf4j
class ExtendedMetadataTest {

  @Test
  void testExtendedMetadata() throws Exception {

    final ExtendedMetadata<OpMetadata> metadata = ExtendedMetadata.builder(OpMetadata.class)
        .baseMetadata(OpMetadata.builder()
            .issuer("issuer")
            .build())
        .addParameter("ext_param1", "value1")
        .addParameter("ext_param2", "value2")
        .build();

    final Map<String, Object> metadataJsonObject = metadata.toJsonObject();
    log.info("Extended metadata:\n{}",
        MetadataUtils.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(metadataJsonObject));

    final ExtendedMetadata<OpMetadata> parsedMetadata =
        new ExtendedMetadata<>(metadataJsonObject, OpMetadata.getJsonSerializer());

    final Map<String, Object> extendedParameters = parsedMetadata.getExtendedParameters();
    assertEquals(2, extendedParameters.size());
    assertEquals("value1", parsedMetadata.getExtendedParameter("ext_param1"));
    assertEquals("value2", parsedMetadata.getExtendedParameter("ext_param2"));
  }

}
