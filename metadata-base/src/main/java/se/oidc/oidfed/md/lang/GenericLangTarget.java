/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.lang;

import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A generic target class for language tagged data. This can be used to instantiate a {@link OidcLangJsonSerializer} for
 * generic conversion of arbitrary JSON objects with language tagged parameters.
 */
@NoArgsConstructor
public class GenericLangTarget implements LanguageTaggedJson {

  /** {@inheritDoc} */
  @Override
  public List<String> getLanguageTaggedParameters() {
    return List.of();
  }
}
