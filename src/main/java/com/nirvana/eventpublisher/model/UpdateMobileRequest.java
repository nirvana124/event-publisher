package com.nirvana.eventpublisher.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateMobileRequest {
    private String mobileNumber;
    private String id;
}
