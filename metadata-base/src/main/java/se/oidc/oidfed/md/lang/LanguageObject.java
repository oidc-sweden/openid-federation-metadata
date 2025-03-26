/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.lang;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Data class holding the values associated with zero or more language tags
 *
 * @param <T> Class of the language tagged objects
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageObject<T> {

  /** The value of a present value without language tag (There can only be one) */
  @JsonProperty("def")
  private T defaultValue;
  /** Language tagged values with the language identifier as key */
  @JsonProperty("lang_values")
  private Map<String, T> valueMap;

  @JsonIgnore
  public T getLanguageValue(final String preferredLanguage) {
    if (this.defaultValue == null && MapUtils.isEmpty(this.valueMap)) {
      return null;
    }
    final T firstAvailable = this.defaultValue == null
        ? this.valueMap.values().stream().findFirst().get()
        : this.defaultValue;

    return !MapUtils.isEmpty(this.valueMap) && this.valueMap.containsKey(preferredLanguage)
        ? this.valueMap.get(preferredLanguage)
        : firstAvailable;
  }

  public static <V> LanguageObjectBuilder<V> builder(final Class<V> valueClass) {
    return new LanguageObjectBuilder<>();
  }

  public static class LanguageObjectBuilder<T> {

    LanguageObject<T> languageObject;

    private LanguageObjectBuilder() {
      this.languageObject = new LanguageObject<>();
    }

    public LanguageObjectBuilder<T> defaultValue(final T defaultValue) {
      this.languageObject.defaultValue = defaultValue;
      return this;
    }

    public LanguageObjectBuilder<T> valueMap(final Map<String, T> valueMap) {
      this.languageObject.valueMap = valueMap;
      return this;
    }

    public LanguageObjectBuilder<T> langValue(final String language, final T value) {
      final Map<String, T> valueMap = Optional.ofNullable(this.languageObject.getValueMap())
          .orElse(new HashMap<>());
      valueMap.put(language, value);
      this.languageObject.valueMap = valueMap;
      return this;
    }

    public LanguageObject<T> build() {
      return this.languageObject;
    }

  }

}
