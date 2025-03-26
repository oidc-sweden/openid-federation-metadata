/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.lang;

import java.util.List;

/**
 * Interface for all Json POJO classes holding language tagged parameters
 */
public interface LanguageTaggedJson {

  /**
   * Get a list of parameter names that serializes to a {@link LanguageObject} object.
   *
   * @return list of language tagged parameters
   */
  List<String> getLanguageTaggedParameters();

}
