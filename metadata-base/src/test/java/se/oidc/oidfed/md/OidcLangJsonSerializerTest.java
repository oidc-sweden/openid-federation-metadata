/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import se.oidc.oidfed.md.lang.GenericLangTarget;
import se.oidc.oidfed.md.lang.OidcLangJsonSerializer;
import se.oidc.oidfed.md.testdata.LangTestTarget;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Json language converter
 */
@Slf4j
class OidcLangJsonSerializerTest {

  static String langTestJson1;
  static final ObjectMapper OBJECT_MAPPER = MetadataUtils.getOidcObjectMapper();

  @BeforeAll
  static void init() throws Exception {
    langTestJson1 = FileUtils.readFileToString(
        new File(OidcLangJsonSerializerTest.class.getResource("/testdata/langTestJson1.json").getFile()),
        StandardCharsets.UTF_8);
  }

  @Test
  void consolidateTest() throws Exception {

    log.info("Testing language tag consolidation");
    log.info("Using test json:\n{}", langTestJson1);
    final OidcLangJsonSerializer<LangTestTarget> preparedConverter = new OidcLangJsonSerializer<>(LangTestTarget.class);
    final LangTestTarget defConsolidatedTarget = preparedConverter.parse(langTestJson1);
    assertEquals("Default value", defConsolidatedTarget.getLangDefault().getDefaultValue());
    assertEquals("Svenska", defConsolidatedTarget.getLangNoDefault().getValueMap().get("sv"));
    log.info("Default consolidation of JSON to Data object test success");

    log.info("Testing conversion using generic converter");
    final OidcLangJsonSerializer<GenericLangTarget> genericConverter =
        new OidcLangJsonSerializer<>(GenericLangTarget.class);
    final String genericConvertedJson = this.genericConvertToJsonObject(genericConverter, List.of());
    log.info("Generic converted JSON without explicit lang parameter list\n{}", genericConvertedJson);
    final MismatchedInputException mismatchedInputException = assertThrows(MismatchedInputException.class, () -> OBJECT_MAPPER.readValue(genericConvertedJson, LangTestTarget.class));
    log.info("Expected exception from deserialization of illegal JSON: {}", mismatchedInputException.getMessage());

    final String adaptedConvertedJson = this.genericConvertToJsonObject(genericConverter, List.of("lang_onlydef"));
    log.info("Generic converted JSON with explicit lang parameter list\n{}", adaptedConvertedJson);
    final LangTestTarget adaptedTarget = OBJECT_MAPPER.readValue(adaptedConvertedJson, LangTestTarget.class);
    assertEquals("Default value", adaptedTarget.getLangOnlyDefault().getDefaultValue());
    log.info("Deserialization success");
  }

  private String genericConvertToJsonObject(final OidcLangJsonSerializer<GenericLangTarget> genericConverter,
      final List<String> langParams) throws Exception {
    final Map<String, Object> convertPresent = genericConverter.consolidateLanguageTags(
        OBJECT_MAPPER.readValue(langTestJson1, new TypeReference<>() {
        }), langParams);
    return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(convertPresent);
  }

}
