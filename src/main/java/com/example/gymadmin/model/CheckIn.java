package com.example.gymadmin.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class CheckIn {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String memberId;
    private OffsetDateTime timestamp;

    public String getId(){return id;}
    public void setId(String id){this.id=id;}
    public String getMemberId(){return memberId;}
    public void setMemberId(String v){this.memberId=v;}
    public OffsetDateTime getTimestamp(){return timestamp;}
    public void setTimestamp(OffsetDateTime v){this.timestamp=v;}
}
