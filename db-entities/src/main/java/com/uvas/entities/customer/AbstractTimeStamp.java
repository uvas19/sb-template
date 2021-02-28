package com.uvas.entities.customer;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import static com.uvas.entities.customer.DateTimeUtil.currentLocalDateTime;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractTimeStamp {

  @Column(name = "crt_ts", nullable = false)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createdTimeStamp;

  @Column(name = "chg_ts", nullable = false)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime changedTimeStamp;

  @PrePersist
  protected void onCreate() {
    changedTimeStamp = currentLocalDateTime();
    createdTimeStamp = changedTimeStamp;
  }

  @PreUpdate
  protected void onUpdate() {
    changedTimeStamp = currentLocalDateTime();
  }
}
