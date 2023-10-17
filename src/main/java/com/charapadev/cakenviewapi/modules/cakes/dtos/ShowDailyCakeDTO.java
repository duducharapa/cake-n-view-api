package com.charapadev.cakenviewapi.modules.cakes.dtos;

import java.sql.Timestamp;

public record ShowDailyCakeDTO(
    Long id,
    ShowCakeDTO cake,
    Timestamp expiresAt
) {}
