package com.app.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteRequest {
    private Enum<VoteValue> value;

    private Long associateId;

    private Long electionId;

    public boolean getBooleanValue() {
        return value.equals(VoteValue.YES);
    }
}
